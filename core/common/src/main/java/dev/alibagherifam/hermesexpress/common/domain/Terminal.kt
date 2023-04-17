package dev.alibagherifam.hermesexpress.common.domain

import dev.alibagherifam.hermesexpress.common.R
import dev.alibagherifam.hermesexpress.common.ui.StringProvider
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

fun generateFakeTerminals(stringProvider: StringProvider) = listOf(
    Terminal(
        latitude = 35.958,
        longitude = 50.681,
        postalAddress = stringProvider.getString(R.string.fake_data_address_1)
    ),
    Terminal(
        latitude = 35.936,
        longitude = 50.769,
        postalAddress = stringProvider.getString(R.string.fake_data_address_2)
    ),
    Terminal(
        latitude = 35.941,
        longitude = 50.725,
        postalAddress = stringProvider.getString(R.string.fake_data_address_3)
    )
)
