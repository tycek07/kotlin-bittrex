package com.tycek.bittrexapi

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.tycek.bittrexapi.adapters.BigDecimalAdapter
import com.tycek.bittrexapi.adapters.LocalDateTimeAdapter
import com.tycek.bittrexapi.adapters.OrderTypeAdapter
import com.tycek.bittrexapi.bodies.ChartDataPeriodType
import com.tycek.bittrexapi.bodies.OrderTypeBody
import com.tycek.bittrexapi.bodies.TickerBody
import com.tycek.bittrexapi.responses.*
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Query
import java.math.BigDecimal

//TODO: throw excpetion when wrpapping object returns success = false

class RestApi() {
    private lateinit var apiKey: String
    private lateinit var secret: String
    private lateinit var bittrexApi: BittrexApi

    constructor(builder: Builder) : this() {
        this.apiKey = builder.apiKey ?: ""
        this.secret = builder.secret ?: ""


        val okHttp = builder.okHttpClient ?: run {
            val b = OkHttpClient.Builder()
            if (apiKey.isNotEmpty() && secret.isNotEmpty()) {
                b.addInterceptor(AuthInterceptor(secret))
            }
            builder.interceptors.forEach { b.addInterceptor(it) }
            b.build()
        }

        val moshi = Moshi.Builder()
                .add(BigDecimalAdapter())
                .add(LocalDateTimeAdapter())
                .add(OrderTypeAdapter())
                .add(KotlinJsonAdapterFactory())
                .build()

        val retrofit = builder.retrofit ?: Retrofit.Builder()
                .client(okHttp)
                .baseUrl("https://bittrex.com")
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build()

        bittrexApi = retrofit.create(BittrexApi::class.java)
    }

    class Builder {
        var apiKey: String? = null
        var secret: String? = null
        var okHttpClient: OkHttpClient? = null
        var retrofit: Retrofit? = null

        val interceptors = mutableListOf<Interceptor>()

        fun withApiKeys(apiKey: String, secret: String): Builder {
            this.apiKey = apiKey
            this.secret = secret
            return this
        }

        fun withRetrofit(retrofit: Retrofit): Builder {
            this.retrofit = retrofit
            return this
        }

        fun withOkHttp(okHttpClient: OkHttpClient): Builder {
            this.okHttpClient = okHttpClient
            return this
        }

        fun addOkHttpInterceptor(interceptor: Interceptor): Builder {
            interceptors.add(interceptor)
            return this
        }

        fun build() = RestApi(this)
    }

    fun getMarkets(): Single<BittrexResponseList<Market>> {
        return bittrexApi.getMarkets()
    }

    fun getCurrencies(): Single<BittrexResponseList<Currency>> {
        return bittrexApi.getCurrencies()
    }

    fun getTicker(currencyPair: TickerBody): Single<BittrexResponseObject<Ticker>> {
        return bittrexApi.getTicker(currencyPair)
    }

    fun getMarketSummaries(): Single<BittrexResponseList<MarketSummary>> {
        return bittrexApi.getMarketSummaries()
    }

    fun getMarketSummary(currencyPair: TickerBody): Single<BittrexResponseList<MarketSummary>> {
        return bittrexApi.getMarketSummary(currencyPair)
    }

    //TODO: think it you can mrth it somehow so just use 1 method...
    fun getOrderBook(currencyPair: TickerBody, orderTypeBody: OrderTypeBody): Single<BittrexResponseObject<OrderBook>> {
        return bittrexApi.getOrderBook(currencyPair, orderTypeBody)
    }

    fun getOrderBookBoth(currencyPair: TickerBody): Single<BittrexResponseList<OrderBook>> {
        return bittrexApi.getOrderBookBoth(currencyPair)
    }

    fun getMarketHistory(currencyPair: TickerBody): Single<BittrexResponseList<MarketHistory>> {
        return bittrexApi.getMarketHistory(currencyPair)
    }

    fun getOpenOrders(currencyPair: TickerBody): Single<BittrexResponseList<OpenOrder>> {
        return bittrexApi.getOpenOrders(apiKey, ApiKeySigningUtil.createNonce(), currencyPair)
    }

    fun getDepositHistory(currency: String): Single<BittrexResponseList<DepositHistoryEntry>> {
        return bittrexApi.getDepositHistory(apiKey, ApiKeySigningUtil.createNonce(), currency)
    }

    fun getWithdrawalHistory(currency: String): Single<BittrexResponseList<WithdrawalHistory>> {
        return bittrexApi.getWithdrawalHistory(apiKey, ApiKeySigningUtil.createNonce(), currency)
    }

    fun getOrderHistory(): Single<BittrexResponseList<OrderHistory>> {
        return bittrexApi.getOrderHistory(apiKey, ApiKeySigningUtil.createNonce())
    }

    fun getOrderHistory(market: TickerBody): Single<BittrexResponseList<OrderHistory>> {
        return bittrexApi.getOrderHistory(apiKey, ApiKeySigningUtil.createNonce(), market)
    }

    fun getOrder(uuid: String): Single<BittrexResponseObject<Order>> {
        return bittrexApi.getOrder(apiKey, ApiKeySigningUtil.createNonce(), uuid)
    }

    fun getDepositAddress(currency: String): Single<BittrexResponseObject<DepositAddress>> {
        return bittrexApi.getDepositAddress(apiKey, ApiKeySigningUtil.createNonce(), currency)
    }

    fun getBalance(currency: String): Single<BittrexResponseObject<Balance>> {
        return bittrexApi.getBalance(apiKey, ApiKeySigningUtil.createNonce(), currency)
    }

    fun getBalances(): Single<BittrexResponseList<Balance>> {
        return bittrexApi.getBalances(apiKey, ApiKeySigningUtil.createNonce())
    }

    fun buyLimit(currencyPair: TickerBody, quantity: Double, rate: BigDecimal): Single<BittrexResponseObject<OrderCreated>> {
        return bittrexApi.buyLimit(apiKey, ApiKeySigningUtil.createNonce(), currencyPair, quantity, rate)
    }

    fun sellLimit(currencyPair: TickerBody, quantity: Double, rate: BigDecimal): Single<BittrexResponseObject<OrderCreated>> {
        return bittrexApi.sellLimit(apiKey, ApiKeySigningUtil.createNonce(), currencyPair, quantity, rate)
    }

    fun cancelOrder(uuid: String): Single<BittrexResponseObject<Any>> {
        return bittrexApi.cancelOrder(apiKey, ApiKeySigningUtil.createNonce(), uuid)
    }

    fun getChartData(market: TickerBody, tickInterval: ChartDataPeriodType): Single<BittrexResponseList<ChartData>> {
        return bittrexApi.getChartData(market, tickInterval)
    }
    fun getLatestTick(market: TickerBody, tickInterval: ChartDataPeriodType, timestamp: Long): Single<BittrexResponseList<ChartData>> {
        return bittrexApi.getLatestTick(market, tickInterval, timestamp)
    }
}