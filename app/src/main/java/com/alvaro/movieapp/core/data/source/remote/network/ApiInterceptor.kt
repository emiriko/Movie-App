package com.alvaro.movieapp.core.data.source.remote.network

import com.alvaro.movieapp.BuildConfig
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class ApiInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original: Request = chain.request()

        val isPublicEndpoint = original.headers["isPublic"] == "true"

        val requestBuilder = original.newBuilder()
            .removeHeader("isPublic")
            .method(original.method, original.body)

        if (!isPublicEndpoint) {
            requestBuilder.addHeader("Authorization", "Bearer ${BuildConfig.API_KEY}")
        }
        return chain.proceed(requestBuilder.build())
    }
}