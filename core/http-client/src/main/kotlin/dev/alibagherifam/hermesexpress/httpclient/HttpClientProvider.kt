package dev.alibagherifam.hermesexpress.httpclient

import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

internal fun provideHttpClient(): Retrofit {
    val okHttpClient = OkHttpClient.Builder()
        .build()

    val jsonConverter = Json.asConverterFactory("application/json".toMediaType())

    return Retrofit.Builder()
        .baseUrl("http://localhost/")
        .client(okHttpClient)
        .addConverterFactory(jsonConverter)
        .build()
}
