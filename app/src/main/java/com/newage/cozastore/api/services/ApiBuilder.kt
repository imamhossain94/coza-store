package com.newage.cozastore.api.services

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiBuilder {

    // Before release, change this URL to your live server URL such as "https://smartherd.com/"
    private const val URL = "http://cozastore.newagedevs.com/api/"

    // Create OkHttp Client
    private val okHttp = OkHttpClient.Builder()

    var gson = GsonBuilder().setLenient().create()

    // Create Retrofit Builder
    private val builder = Retrofit.Builder().baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(okHttp.build())

    // Create Retrofit Instance
    private val retrofit = builder.build()

    fun <T> buildService(serviceType: Class<T>): T {
        return retrofit.create(serviceType)
    }
}