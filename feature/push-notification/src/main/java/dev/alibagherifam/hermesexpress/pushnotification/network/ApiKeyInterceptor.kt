package dev.alibagherifam.hermesexpress.pushnotification.network

import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val updatedRequest = request.newBuilder()
            .addHeader(NetworkConstants.API_KEY_NAME, NetworkConstants.API_KEY_VALUE)
            .build()

        return chain.proceed(updatedRequest)
    }
}
