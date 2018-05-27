package com.tycek.bittrexapi.responses

import com.squareup.moshi.Json
import java.math.BigDecimal
import java.time.LocalDateTime

enum class OrderType {
    LIMIT_BUY,
    LIMIT_SELL
}

enum class FillType {
    PARTIAL_FILL,
    FILL;
}

enum class MarketOrderType {
    SELL,
    BUY;
}

data class Market(
        val MarketCurrency: String,
        val BaseCurrency: String,
        val MarketCurrencyLong: String,
        val BaseCurrencyLong: String,
        val MinTradeSize: BigDecimal,
        val MarketName: String,
        val IsActive: Boolean,
        val Created: LocalDateTime,
        val Notice: String?,
        val IsSponsored: Boolean?,
        val LogoUrl: String?
)

data class Currency(
        val Currency: String,
        val CurrencyLong: String,
        val MinConfirmation: Int,
        val TxFee: Double,
        val IsActive: Boolean,
        val CoinType: String,
        val BaseAddress: String?
)

data class Ticker(
        val Bid: BigDecimal,
        val Ask: BigDecimal,
        val Last: BigDecimal
)

data class MarketSummary(
        val MarketName: String,
        val High: BigDecimal,
        val Low: BigDecimal,
        val Volume: BigDecimal,
        val Last: BigDecimal,
        val BaseVolume: BigDecimal,
        val TimeStamp: LocalDateTime,
        val Bid: BigDecimal,
        val Ask: BigDecimal,
        val OpenBuyOrders: Int,
        val OpenSellOrders: Int,
        val PrevDay: BigDecimal,
        val Created: LocalDateTime,
        val DisplayMarketName: String
)

data class OrderEntry(
        val Quantity: BigDecimal,
        val Rate: BigDecimal
)

data class OrderBook(
        val buy: List<OrderEntry>?,
        val sell: List<OrderEntry>?
)

data class MarketHistory(
        val Id: Int,
        val TimeStamp: LocalDateTime,
        val Quantity: Double,
        val Price: BigDecimal,
        val Total: Double,
        val FillType: FillType,
        val OrderType: MarketOrderType
)

data class Balance(
        val Currency: String,
        val Balance: BigDecimal,
        val Available: BigDecimal,
        val Pending: BigDecimal,
        val CryptoAddress: String,
        val Requested: Boolean,
        val Uuid: String?
)

data class DepositAddress(
        val Currency: String,
        val Address: String
)

data class DepositHistoryEntry(
        val Id: String,
        val Confirmations: Int,
        val LastUpdated: LocalDateTime,
        val PaymentUuid: String,
        val Currency: String,
        val Amount: BigDecimal,
        val Address: String,
        val Opened: LocalDateTime,
        val Authorized: Boolean,
        val PendingPayment: Boolean,
        val TxCost: BigDecimal,
        val TxId: String,
        val Canceled: Boolean,
        val InvalidAddress: Boolean
)

data class WithDrawConfirmation(
        val uuid: String
)

data class Order(
        val AccountId: String?,
        val OrderUuid: String,
        val Exchange: String,
        val Type: OrderType,
        val Quantity: BigDecimal,
        val QuantityRemaining: BigDecimal,
        val Limit: BigDecimal,
        val Reserved: BigDecimal,
        val ReserveRemaining: BigDecimal,
        val CommissionReserved: BigDecimal,
        val CommissionReserveRemaining: BigDecimal,
        val CommissionPaid: BigDecimal,
        val Price: BigDecimal,
        val PricePerUnit: BigDecimal?,
        val Opened: LocalDateTime,
        val Closed: LocalDateTime?,
        val IsOpen: Boolean,
        val Sentinel: String,
        val CancelInitiated: Boolean,
        val ImmediateOrCancel: Boolean,
        val IsConditional: Boolean,
        val Condition: String,
        val ConditionTarget: String?
)

data class OrderHistory(
        val OrderUuid: String, //UUID
        val Exchange: String, //"BTC-LTC"
        val TimeStamp: LocalDateTime,
        val OrderType: OrderType,
        val Limit: BigDecimal,
        val Quantity: BigDecimal,
        val QuantityRemaining: BigDecimal,
        val Commission: BigDecimal,
        val Price: BigDecimal,
        val PricePerUnit: BigDecimal?,
        val IsConditional: Boolean,
        val Condition: String?,  //NONE
        val ConditionTarget: String?,
        val ImmediateOrCancel: Boolean,
        val Closed: LocalDateTime?
)

data class WithdrawalHistory(
        val PaymentUuid: String,
        val Currency: String,
        val Amount: BigDecimal,
        val Address: String,
        val Opened: LocalDateTime,
        val Authorized: Boolean,
        val PendingPayment: Boolean,
        val TxCost: BigDecimal,
        val TxId: String?,
        val Canceled: Boolean,
        val InvalidAddress: Boolean

)

data class DepositHistory(
        private val Id: String,
        val Confirmations: Int,
        val LastUpdated: LocalDateTime,
        val PaymentUuid: String,
        val Currency: String,
        val Amount: BigDecimal,
        val CryptoAddress: String,
        val Opened: LocalDateTime,
        val Authorized: Boolean,
        val PendingPayment: Boolean,
        val TxCost: BigDecimal,
        val TxId: String,
        val Canceled: Boolean,
        val InvalidAddress: Boolean
)

data class OrderCreated(
        val uuid: String
)

data class OpenOrder(
        val Uuid: String?,
        val OrderUuid: String,
        val Exchange: String, //"BTC-LTC"
        val OrderType: OrderType,
        val Quantity: BigDecimal,
        val QuantityRemaining: BigDecimal,
        val Limit: BigDecimal,
        val CommissionPaid: BigDecimal,
        val Price: BigDecimal,
        val PricePerUnit: BigDecimal,
        val Opened: LocalDateTime,
        val Closed: LocalDateTime?,
        val CancelInitiated: Boolean,
        val ImmediateOrCancel: Boolean,
        val IsConditional: Boolean,
        val Condition: String?, //TODO: i don't know "NONE"
        val ConditionTarget: String? //TODO: i don't know i think it might be BigDecimal
)

data class ChartData(
        @Json(name = "T")
        val TimeStamp: LocalDateTime,
        @Json(name = "O")
        val Open: BigDecimal,
        @Json(name = "C")
        val Close: BigDecimal,
        @Json(name = "H")
        val High: BigDecimal,
        @Json(name = "L")
        val Low: BigDecimal,
        @Json(name = "V")
        val Volume: BigDecimal,
        @Json(name = "BV")
        val BaseVolume: BigDecimal
)