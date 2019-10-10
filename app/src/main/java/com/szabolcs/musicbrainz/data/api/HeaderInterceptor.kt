package com.szabolcs.musicbrainz.data.api

import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()

        val requestBuilder = original.newBuilder()
            .addHeader("Accept", "application/json")
        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}