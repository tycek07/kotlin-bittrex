package com.tycek.bittrexapi.adapters

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import com.tycek.bittrexapi.bodies.OrderTypeBody
import java.math.BigDecimal
import java.time.LocalDateTime


class LocalDateTimeAdapter {
    @FromJson
    fun localDateTimeFromJson(time: String): LocalDateTime = LocalDateTime.parse(time)

    @ToJson
    fun locationDateTimeToJson(time: LocalDateTime): String = time.toString()
}

class BigDecimalAdapter {

    @FromJson
    fun bigDecimalFromJson(bigDecimal: String): BigDecimal = BigDecimal(bigDecimal)

    @ToJson
    fun bigDecimalToJson(bigDecimal: BigDecimal): String = bigDecimal.toPlainString()

}

class OrderTypeAdapter {

    @FromJson
    fun orderTypeFromJson(orderType: String): OrderTypeBody = OrderTypeBody.values().first { orderType == it.jsonValue }

    @ToJson
    fun orderTypeToJson(orderTypeBody: OrderTypeBody): String = orderTypeBody.jsonValue

}