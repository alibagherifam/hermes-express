package dev.alibagherifam.hermesexpress.common.domain

data class Terminal(
    val point: Point,
    val postalAddress: String
) {
    init {
        require(postalAddress.isNotEmpty()) { "Terminal need a postal address" }
    }
}

fun generateFakeTerminals() = listOf(
    Terminal(
        Point(60.239, 25.004),
        postalAddress = "Robert Robertson, 1234 NW Bobcat Lane, St. Robert, MO 65584"
    ),
    Terminal(
        Point(55.004, 39.239),
        postalAddress = "Miranda McAnderson, 6543 N 9th Street, AA 33608-1234"
    )
)
