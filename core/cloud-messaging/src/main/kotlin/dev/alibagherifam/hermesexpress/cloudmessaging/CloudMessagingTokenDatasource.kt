package dev.alibagherifam.hermesexpress.cloudmessaging

interface CloudMessagingTokenDatasource {
    suspend fun getToken(): String
    suspend fun saveToken(token: String)
}
