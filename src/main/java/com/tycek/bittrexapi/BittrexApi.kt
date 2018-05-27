package com.tycek.bittrexapi

import com.tycek.bittrexapi.bodies.ChartDataPeriodType
import com.tycek.bittrexapi.bodies.OrderTypeBody
import com.tycek.bittrexapi.bodies.TickerBody
import com.tycek.bittrexapi.responses.*
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import java.math.BigDecimal


interface BittrexApi {
    @GET("/api/v1.1/public/getmarkets")
    fun getMarkets(): Single<BittrexResponseList<Market>>

    @GET("/api/v1.1/public/getmarkets")
    fun getMarketsR(): Call<BittrexResponseList<Market>>

    @GET("/api/v1.1/public/getcurrencies")
    fun getCurrencies(): Single<BittrexResponseList<Currency>>

    @GET("/api/v1.1/public/getticker")
    fun getTicker(@Query("market") currencyPair: TickerBody): Single<BittrexResponseObject<Ticker>>

    @GET("/api/v1.1/public/getmarketsummaries")
    fun getMarketSummaries(): Single<BittrexResponseList<MarketSummary>>

    @GET("api/v1.1/public/getmarketsummary")
    fun getMarketSummary(@Query("market") currencyPair: TickerBody): Single<BittrexResponseList<MarketSummary>>

    @GET("api/v1.1/public/getorderbook")
    fun getOrderBook(@Query("market") currencyPair: TickerBody,
                     @Query("type") orderTypeBody: OrderTypeBody):
            Single<BittrexResponseObject<OrderBook>>

    @GET("api/v1.1/public/getorderbook")
    fun getOrderBookBoth(@Query("market") currencyPair: TickerBody,
                         @Query("type") type: String = "both"):
            Single<BittrexResponseList<OrderBook>>

    @GET("api/v1.1/public/getmarkethistory")
    fun getMarketHistory(@Query("market") currencyPair: TickerBody): Single<BittrexResponseList<MarketHistory>>

    @GET("api/v1.1/market/buylimit")
    fun buyLimit(@Query("apikey") apikey: String,
                 @Query("nonce") nonce: String,
                 @Query("market") currencyPair: TickerBody,
                 @Query("quantity") quantity: Double,
                 @Query("rate") rate: BigDecimal)
            : Single<BittrexResponseObject<OrderCreated>>

    @GET("api/v1.1/market/selllimit")
    fun sellLimit(@Query("apikey") apikey: String,
                  @Query("nonce") nonce: String,
                  @Query("market") currencyPair: TickerBody,
                  @Query("quantity") quantity: Double,
                  @Query("rate") rate: BigDecimal)
            : Single<BittrexResponseObject<OrderCreated>>


    @GET("api/v1.1/market/cancel")
    fun cancelOrder(@Query("apikey") apikey: String,
                    @Query("nonce") nonce: String,
                    @Query("uuid") uuid: String)
            : Single<BittrexResponseObject<Any>>

    @GET("api/v1.1/market/getopenorders")
    fun getOpenOrders(@Query("apikey") apikey: String,
                      @Query("nonce") nonce: String,
                      @Query("market") currencyPair: TickerBody):
            Single<BittrexResponseList<OpenOrder>>

    @GET("api/v1.1/account/getbalances")
    fun getBalances(@Query("apikey") apikey: String,
                    @Query("nonce") nonce: String)
            : Single<BittrexResponseList<Balance>>

    @GET("api/v1.1/account/getbalance")
    fun getBalance(@Query("apikey") apikey: String,
                   @Query("nonce") nonce: String,
                   @Query("currency") currency: String):
            Single<BittrexResponseObject<Balance>>

    @GET("api/v1.1/account/getdepositaddress")
    fun getDepositAddress(@Query("apikey") apikey: String,
                          @Query("nonce") nonce: String,
                          @Query("currency") currency: String):
            Single<BittrexResponseObject<DepositAddress>>

    @GET("api/v1.1/account/withdraw")
    fun withdraw(@Query("apikey") apikey: String,
                 @Query("nonce") nonce: String,
                 @Query("currency") currency: String,
                 @Query("quantity") quantity: BigDecimal,
                 @Query("address") address: String,
                 @Query("paymentId") paymentId: String?): //TODO: not sure about paymentId
            Single<BittrexResponseObject<WithDrawConfirmation>>

    @GET("api/v1.1/account/getorder")
    fun getOrder(@Query("apikey") apikey: String,
                 @Query("nonce") nonce: String,
                 @Query("uuid") uuid: String)
            : Single<BittrexResponseObject<Order>>

    @GET("api/v1.1/account/getorderhistory")
    fun getOrderHistory(@Query("apikey") apikey: String,
                        @Query("nonce") nonce: String)
            : Single<BittrexResponseList<OrderHistory>>

    @GET("api/v1.1/account/getorderhistory")
    fun getOrderHistory(@Query("apikey") apikey: String,
                        @Query("nonce") nonce: String,
                        @Query("market") market: TickerBody)
            : Single<BittrexResponseList<OrderHistory>>

    @GET("api/v1.1/account/getwithdrawalhistory")
    fun getWithdrawalHistory(@Query("apikey") apikey: String,
                             @Query("nonce") nonce: String,
                             @Query("currency") currency: String)
            : Single<BittrexResponseList<WithdrawalHistory>>

    @GET("api/v1.1/account/getdeposithistory")
    fun getDepositHistory(@Query("apikey") apikey: String,
                          @Query("nonce") nonce: String,
                          @Query("currency") currency: String)
            : Single<BittrexResponseList<DepositHistoryEntry>>

    @GET("api/v2.0/pub/market/getticks")
    fun getChartData(@Query("marketname") market: TickerBody, @Query("tickinterval") tickInterval: ChartDataPeriodType)
            : Single<BittrexResponseList<ChartData>>

    @GET("api/v2.0/pub/market/GetLatestTick")
    fun getLatestTick(
            @Query("marketName") market: TickerBody,
            @Query("tickInterval") tickInterval: ChartDataPeriodType,
            @Query("_") timeStamp: Long):
            Single<BittrexResponseList<ChartData>>

}