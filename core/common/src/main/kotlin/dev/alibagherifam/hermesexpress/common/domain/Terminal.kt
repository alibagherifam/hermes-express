package dev.alibagherifam.hermesexpress.common.domain

import dev.alibagherifam.hermesexpress.common.R
import dev.alibagherifam.hermesexpress.common.ui.StringProvider
import kotlinx.serialization.Serializable

@Serializable
data class Terminal(
    val location: Location,
    val postalAddress: String
) {
    init {
        require(postalAddress.isNotEmpty()) { "Postal address can not be empty" }
    }
}

fun generateFakeTerminals(
    stringProvider: StringProvider,
    userLocation: Location = Location(35.7194, 51.3709)
): List<Terminal> = listOf(
    Pair(-0.0167, -0.0061),
    Pair(-0.0037, +0.0520),
    Pair(+0.0227, +0.0069),
    Pair(+0.0191, -0.0715)
).map { (latitudeDiff, longitudeDiff) ->
    Location(
        userLocation.first + latitudeDiff,
        userLocation.second + longitudeDiff
    )
}.zip(
    listOf(
        R.string.fake_data_address_1,
        R.string.fake_data_address_2,
        R.string.fake_data_address_3,
        R.string.fake_data_address_4
    )
).map { (locations, addressStringRes) ->
    Terminal(
        locations,
        postalAddress = stringProvider.getString(addressStringRes)
    )
}
