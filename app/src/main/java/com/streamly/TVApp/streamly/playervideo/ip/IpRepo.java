package com.streamly.TVApp.streamly.playervideo.ip;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.streamly.TVApp.streamly.retrofit_clients.ApiClient;
import com.streamly.TVApp.streamly.retrofit_clients.ApiInterface;
import com.streamly.TVApp.streamly.retrofit_clients.ApiResponse;
import com.streamly.TVApp.streamly.utilities.CommonUtility;
import com.streamly.TVApp.streamly.utilities.ConstantUtility;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IpRepo {
    private final String TAG=IpRepo.class.getSimpleName();


    public MutableLiveData<ApiResponse> requestIp(String IP_Address, Context context) {
        final MutableLiveData<ApiResponse> mutableLiveData=new MutableLiveData<>();

        ApiInterface apiInterface= ApiClient.getIPClient().create(ApiInterface.class);
        apiInterface.getLatLong(IP_Address).enqueue(new Callback<IpModel>() {
            @Override
            public void onResponse(Call<IpModel> call, Response<IpModel> response) {
                try{
                    Log.e(TAG, "response_ip "+response);
                    if (response.isSuccessful()) {
                        mutableLiveData.postValue(new ApiResponse(response.body(),response.code()));
                        Log.e(TAG, "success "+response);
                    }else {
                        Log.e(TAG, "elseResponse "+response);
                        mutableLiveData.postValue(new ApiResponse(response.body(),response.code()));

                    }
                }catch (Exception ex){
                    ex.printStackTrace();
                    Log.e(TAG, "Exception "+ex);
                }
            }

            @Override
            public void onFailure(Call<IpModel> call, Throwable t) {
                Log.e(TAG, "response_error "+t.getLocalizedMessage());
                CommonUtility.showToast(context,ConstantUtility.SERVER_ISSUE);
            }
        });

        return mutableLiveData;
    }


}
