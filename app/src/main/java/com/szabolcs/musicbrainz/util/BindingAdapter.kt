package com.szabolcs.musicbrainz.util

import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

@BindingAdapter("isLoading")
fun setLoading(swipeRefreshLayout: SwipeRefreshLayout, isRefreshing: Boolean) {
    swipeRefreshLayout.isRefreshing = isRefreshing
}


@BindingAdapter("enabled")
fun setEnabled(swipeRefreshLayout: SwipeRefreshLayout, enabled: Boolean) {
    swipeRefreshLayout.isEnabled = enabled
}