package com.tycek.bittrexapi


import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class AuthInterceptor(private val secret: String) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val url = request.url().toString()
        return chain.proceed(
                if (url.contains("/market/") || url.contains("/account/")) {
                    request.newBuilder()
                            .addHeader("apisign", ApiKeySigningUtil.createSign(url, secret))
                            .build()
                } else {
                    request
                })
    }
}
