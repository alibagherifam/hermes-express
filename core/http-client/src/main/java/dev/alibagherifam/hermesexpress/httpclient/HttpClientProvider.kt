package dev.alibagherifam.hermesexpress.httpclient

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit

internal fun provideHttpClient(): Retrofit {
    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(ApiKeyInterceptor())
        .build()

    val jsonConverter = Json.asConverterFactory("application/json".toMediaType())

    return Retrofit.Builder()
        .baseUrl(NetworkConstants.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(jsonConverter)
        .build()
}
