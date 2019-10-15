package com.szabolcs.musicbrainz

import kotlinx.coroutines.*
import java.util.concurrent.atomic.AtomicReference

/**
 * A controlled runner decides what to do when new tasks are run.
 *
 * By calling [cancelPreviousThenRun], the old task will *always* be cancelled and then the new task will
 * be run. This is useful in situations where a new event implies that the previous work is no
 * longer relevant such as sorting or filtering a list.
 *
 * Resources: https://gist.github.com/objcode/7ab4e7b1df8acd88696cb0ccecad16f7#file-concurrencyhelpers-kt-L19
 * https://medium.com/androiddevelopers/coroutines-on-android-part-iii-real-work-2ba8a2ec2f45
 */
class ControlledRunner<T> {
    /**
     * The currently active task.
     *
     * This uses an atomic reference to ensure that it's safe to update activeTask on both
     * Dispatchers.Default and Dispatchers.Main which will execute coroutines on multiple threads at
     * the same time.
     */
    private val activeTask = AtomicReference<Deferred<T>?>(null)

    /**
     * Cancel all previous tasks before calling block.
     *
     * When several coroutines call cancelPreviousThenRun at the same time, only one will run and
     * the others will be cancelled.
     *
     * @param block the code to run after previous work is cancelled.
     * @return the result of block, if this call was not cancelled prior to returning.
     */
    suspend fun cancelPreviousThenRun(block: suspend () -> T): T {
        // fast path: if we already know about an active task, just cancel it right away.
        activeTask.get()?.cancelAndJoin()

        return coroutineScope {
            // Create a new coroutine, but don't start it until it's decided that this block should
            // execute. In the code below, calling await() on newTask will cause this coroutine to
            // start.
            val newTask = async(start = CoroutineStart.LAZY) {
                block()
            }

            // When newTask completes, ensure that it resets activeTask to null (if it was the
            // current activeTask).
            newTask.invokeOnCompletion {
                activeTask.compareAndSet(newTask, null)
            }

            // Kotlin ensures that we only set result once since it's a val, even though it's set
            // inside the while(true) loop.
            val result: T

            // Loop until we are sure that newTask is ready to execute (all previous tasks are
            // cancelled)
            while (true) {
                if (!activeTask.compareAndSet(null, newTask)) {
                    // some other task started before newTask got set to activeTask, so see if it's
                    // still running when we call get() here. If so, we can cancel it.

                    // we will always start the loop again to see if we can set activeTask before
                    // starting newTask.
                    activeTask.get()?.cancelAndJoin()
                    // yield here to avoid a possible tight loop on a single threaded dispatcher
                    yield()
                } else {
                    // happy path - we set activeTask so we are ready to run newTask
                    result = newTask.await()
                    break
                }
            }

            // Kotlin ensures that the above loop always sets result exactly once, so we can return
            // it here!
            result
        }
    }
}