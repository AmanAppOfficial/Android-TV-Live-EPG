package com.streamly.TVApp.streamly.services

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

/**
 * Created by karanjeet on 5/10/19
 */
interface WebServiceInterface {

    @Headers("Accept: application/json")
    @GET("/api/auth/channellist")
    fun getChannelList(@Header("Authorization") authToken : String) : Call<ResponseBody>

    @Headers("Accept: application/json")
    @GET("/api/auth/channeldayschdeule")
    fun getScheduleList(@Header("Authorization") authToken : String) : Call<ResponseBody>
}