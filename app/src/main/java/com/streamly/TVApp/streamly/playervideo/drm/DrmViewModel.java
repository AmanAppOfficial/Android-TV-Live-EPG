package com.streamly.TVApp.streamly.playervideo.drm;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.streamly.TVApp.streamly.retrofit_clients.ApiResponse;

public class DrmViewModel extends ViewModel {
    private MediatorLiveData<ApiResponse> mApiResponse;
    private DrmKeyRepo mApiRepo;

    public DrmViewModel() {
        mApiResponse = new MediatorLiveData<>();
        mApiRepo = new DrmKeyRepo();
    }

    public LiveData<ApiResponse> getData(Context context) {
        mApiResponse.addSource(mApiRepo.getDrmKey(context), new Observer<ApiResponse>() {
            @Override
            public void onChanged(@Nullable ApiResponse apiResponse){
                mApiResponse.setValue(apiResponse);
            }
        });
        return mApiResponse;
    }
}