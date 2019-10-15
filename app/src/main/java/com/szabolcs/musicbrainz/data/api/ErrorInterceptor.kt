package com.szabolcs.musicbrainz.data.api

import com.google.gson.Gson
import com.szabolcs.musicbrainz.R
import com.szabolcs.musicbrainz.data.model.remote.ServiceError
import com.szabolcs.musicbrainz.util.ResourceWrapper
import okhttp3.Interceptor
import okhttp3.Response
import java.net.UnknownHostException

class ErrorInterceptor(private val resourceWrapper: ResourceWrapper, private val gson: Gson) : Interceptor {

    @Throws(ServiceError::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response: Response

        try {
            response = chain.proceed(request)
            if (!response.isSuccessful) {
                throw ServiceError(resourceWrapper.getString(R.string.something_went_wrong), 1)
            }
        } catch (exception: UnknownHostException) {
            throw ServiceError(resourceWrapper.getString(R.string.no_network), errorCode = NETWORK_ERROR)
        } catch (illegal: IllegalStateException) {
            throw ServiceError(resourceWrapper.getString(R.string.something_went_wrong), errorCode = NETWORK_ERROR)
        }
        return response
    }

    companion object {
        const val NETWORK_ERROR = 1
    }
}