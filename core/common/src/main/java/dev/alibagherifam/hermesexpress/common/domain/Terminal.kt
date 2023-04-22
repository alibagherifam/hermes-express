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

fun generateFakeTerminals(
    stringProvider: StringProvider,
    fromOrigin: Pair<Double, Double> = Pair(35.7194, 51.3709)
): List<Terminal> = listOf(
    Pair(+0.0167, +0.0061),
    Pair(+0.0037, -0.0520),
    Pair(-0.0227, -0.0069),
    Pair(-0.0191, +0.0715)
).map { coordinatesDiff ->
    Pair(
        fromOrigin.first - coordinatesDiff.first,
        fromOrigin.second - coordinatesDiff.second
    )
}.zip(
    listOf(
        R.string.fake_data_address_1,
        R.string.fake_data_address_2,
        R.string.fake_data_address_3,
        R.string.fake_data_address_4
    )
).map {
    val (latitude, longitude) = it.first
    Terminal(
        latitude,
        longitude,
        postalAddress = stringProvider.getString(it.second)
    )
}
