package com.streamly.TVApp.streamly.services

import androidx.annotation.NonNull
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by karanjeet on 5/10/19
 */
class CommonEnqueueValidator(private var call: Call<ResponseBody>, private var networkCallResponse: NetworkCallResponse, private var apiTag:String) {
    fun enqueue() {
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(@NonNull call: Call<ResponseBody>, @NonNull response: Response<ResponseBody>) {
                if (response.isSuccessful)
                    networkCallResponse.networkCallResponse(true, apiTag, response)
                else
                    networkCallResponse.networkCallResponse(false, apiTag, response)
            }

            override fun onFailure(@NonNull call: Call<ResponseBody>, @NonNull t: Throwable) {
                networkCallResponse.networkFailureResponse(false, apiTag, t)
            }
        })
    }
}