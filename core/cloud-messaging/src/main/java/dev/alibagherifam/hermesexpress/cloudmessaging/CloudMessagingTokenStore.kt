package dev.alibagherifam.hermesexpress.cloudmessaging

interface CloudMessagingTokenStore {
    suspend fun getToken(): String
    suspend fun saveToken(token: String)
}
