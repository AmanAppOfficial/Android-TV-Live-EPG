package com.streamly.TVApp.streamly.playervideo.geofence;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.streamly.TVApp.streamly.retrofit_clients.ApiResponse;

import java.util.Map;

public class GeofenceViewModel extends ViewModel {
    private MediatorLiveData<ApiResponse> mApiResponse;
    private GeofenceRepo mApiRepo;

    public GeofenceViewModel() {
        mApiResponse = new MediatorLiveData<>();
        mApiRepo = new GeofenceRepo();
    }

    public LiveData<ApiResponse> getData(Map<String, Double> map, Context context) {
        mApiResponse.addSource(mApiRepo.getPosts(map, context), new Observer<ApiResponse>() {
            @Override
            public void onChanged(@Nullable ApiResponse apiResponse){
                mApiResponse.setValue(apiResponse);
            }
        });
        return mApiResponse;
    }
}
