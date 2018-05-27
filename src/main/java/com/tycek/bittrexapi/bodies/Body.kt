package com.tycek.bittrexapi.bodies

data class TickerBody(
        val mainCoin: String,
        val secondCoin: String
) {
    override fun toString(): String = "$mainCoin-$secondCoin"
}

enum class OrderTypeBody(val jsonValue: String) {
    BUY("buy"),
    SELL("sell"),
    BOTH("both");

    override fun toString(): String = jsonValue
}

enum class ChartDataPeriodType(val jsonValue: String) {
    ONE_MIN("oneMin"),
    FIVE_MIN("fiveMin"),
    THIRTY_MIN("thirtyMin"),
    ONE_HOUR("hour"),
    ONE_DAY("day");

    override fun toString(): String = jsonValue
}


