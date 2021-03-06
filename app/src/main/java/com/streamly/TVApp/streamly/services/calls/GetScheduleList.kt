package com.streamly.TVApp.streamly.services.calls

import com.streamly.TVApp.streamly.services.CommonEnqueueValidator
import com.streamly.TVApp.streamly.services.NetworkCallResponse
import com.streamly.TVApp.streamly.services.ServiceBuilder
import com.streamly.TVApp.streamly.services.WebServiceInterface

class GetScheduleList {
    private val apiTag = GetScheduleList::class.java.simpleName
    fun callGetScheduleListApi(
            authToken: String, mCallback: NetworkCallResponse) {
        val webServiceInterface = ServiceBuilder().buildClient(
                WebServiceInterface::class.java,
                "http://100.26.190.92")
        val getScheduleList = webServiceInterface.getScheduleList(authToken = authToken)
        val commonEnqueueValidator = CommonEnqueueValidator(getScheduleList,
                mCallback, apiTag)
        commonEnqueueValidator.enqueue()
    }
}