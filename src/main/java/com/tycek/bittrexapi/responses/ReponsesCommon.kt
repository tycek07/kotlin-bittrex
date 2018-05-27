package com.tycek.bittrexapi.responses


data class BittrexResponseList<out T>(
        val success: Boolean,
        val message: String,
        val result: List<T>?
)

data class BittrexResponseObject<out T>(
        val success: Boolean,
        val message: String,
        val result: T?
)
