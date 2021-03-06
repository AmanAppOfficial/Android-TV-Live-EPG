package com.streamly.TVApp.streamly.services

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by karanjeet on 5/10/19
 */
 class ServiceBuilder {
     fun <`object`> buildClient(serviceClass: Class<`object`>, baseUrl: String): `object` {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val httpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor).build()

        val builder = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())

        val retrofit = builder.client(
            httpClient.newBuilder().connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS).writeTimeout(30, TimeUnit.SECONDS).build()
        ).build()
        return retrofit.create(serviceClass)
    }
}