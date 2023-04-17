package dev.alibagherifam.hermesexpress.common.domain

import kotlinx.serialization.Serializable

@Serializable
data class Terminal(
    val latitude: Double,
    val longitude: Double,
    val postalAddress: String
) {
    init {
        require(latitude > 0) { "Latitude ID is not allowed" }
        require(longitude > 0) { "Longitude ID is not allowed" }
        require(postalAddress.isNotEmpty()) { "Postal address can not be empty" }
    }
}

fun generateFakeTerminals() = listOf(
    Terminal(
        latitude = 35.958,
        longitude = 50.681,
        postalAddress = "Robert Robertson, 1234 NW Bobcat Lane, St. Robert, MO 65584"
    ),
    Terminal(
        latitude = 35.936,
        longitude = 50.769,
        postalAddress = "Robert Robertson, 1234 NW Bobcat Lane, St. Robert, MO 65584"
    ),
    Terminal(
        latitude = 35.941,
        longitude = 50.725,
        postalAddress = "Miranda McAnderson, 6543 N 9th Street, AA 33608-1234"
    )
)
