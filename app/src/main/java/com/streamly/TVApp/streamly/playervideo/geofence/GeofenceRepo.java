package com.streamly.TVApp.streamly.playervideo.geofence;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.streamly.TVApp.streamly.local_storage.TokenStorage;
import com.streamly.TVApp.streamly.playervideo.drm.DrmKeyModel;
import com.streamly.TVApp.streamly.retrofit_clients.ApiClient;
import com.streamly.TVApp.streamly.retrofit_clients.ApiInterface;
import com.streamly.TVApp.streamly.retrofit_clients.ApiResponse;
import com.streamly.TVApp.streamly.utilities.ConstantUtility;
import com.streamly.TVApp.streamly.utilities.NetworkKeys;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GeofenceRepo {
    private final String TAG= GeofenceRepo.class.getSimpleName();

    public ApiInterface endpoints;

    public GeofenceRepo() {
        endpoints = ApiClient.getClient().create(ApiInterface.class);
    }


    public LiveData<ApiResponse> getPosts(Map<String, Double> map, Context context) {
        final MutableLiveData<ApiResponse> apiResponse = new MutableLiveData<>();
        String  authToken= TokenStorage.readSharedToken(context, ConstantUtility.AUTH_TOKEN, null);

        Call<GeofenceModel> call = endpoints.postGeofenceCall(NetworkKeys.KEY_BEARER+authToken,map);
        call.enqueue(new Callback<GeofenceModel>() {
            @Override
            public void onResponse(Call<GeofenceModel> call, Response<GeofenceModel> response) {
               try{
                if (response.isSuccessful()) {
                    apiResponse.postValue(new ApiResponse(response.body(),response.code()));
                    Log.e(TAG, "success "+response);
                }else {
                    Log.e(TAG, "elseResponse "+response);
                    GeofenceModel geofenceModel=new Gson().fromJson(response.errorBody().string(),GeofenceModel.class);
                    apiResponse.postValue(new ApiResponse(geofenceModel,response.code()));

                }
            }catch (Exception ex){
                ex.printStackTrace();
                Log.e(TAG, "Exception "+ex);
            }
            }

            @Override
            public void onFailure(Call<GeofenceModel> call, Throwable t) {
                apiResponse.postValue(new ApiResponse(t));
                Log.e(TAG, "failure "+t);
            }
        });

        return apiResponse;
    }
}
