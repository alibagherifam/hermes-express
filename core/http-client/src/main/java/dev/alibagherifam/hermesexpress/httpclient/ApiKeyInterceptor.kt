package dev.alibagherifam.hermesexpress.httpclient

import okhttp3.Interceptor
import okhttp3.Response

internal class ApiKeyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val updatedRequest = request.newBuilder()
            .addHeader(
                name = Constants.API_KEY_NAME,
                value = "key=" + Constants.API_KEY_VALUE
            )
            .build()

        return chain.proceed(updatedRequest)
    }
}
