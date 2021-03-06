package com.streamly.TVApp.streamly.services.calls

import com.streamly.TVApp.streamly.services.CommonEnqueueValidator
import com.streamly.TVApp.streamly.services.NetworkCallResponse
import com.streamly.TVApp.streamly.services.ServiceBuilder
import com.streamly.TVApp.streamly.services.WebServiceInterface

class GetChannelList {
    private val apiTag = GetChannelList::class.java.simpleName
    fun callGetChannelListApi(
            authToken: String, mCallback: NetworkCallResponse) {
        val webServiceInterface = ServiceBuilder().buildClient(
                WebServiceInterface::class.java,
                "http://100.26.190.92")
        val getChannelList = webServiceInterface.getChannelList(authToken = authToken)
        val commonEnqueueValidator = CommonEnqueueValidator(getChannelList,
                mCallback, apiTag)
        commonEnqueueValidator.enqueue()
    }
}