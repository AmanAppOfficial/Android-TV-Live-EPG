package com.streamly.TVApp.streamly.services

import okhttp3.ResponseBody

/**
 * Created by karanjeet on 5/10/19
 */
interface NetworkCallResponse {
    fun networkCallResponse(
        result: Boolean,
        apiTag: String,
        responseBody: retrofit2.Response<ResponseBody>
    )

    fun networkFailureResponse(
        result: Boolean,
        apiTag: String,
        throwable: Throwable
    )

}