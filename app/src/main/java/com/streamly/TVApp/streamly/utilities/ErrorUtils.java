package com.streamly.TVApp.streamly.utilities;

import android.util.Log;

import com.streamly.TVApp.streamly.retrofit_clients.ApiClient;
import java.lang.annotation.Annotation;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;

public class ErrorUtils {

    public static ApiError parseError(Response<?> response){
        Converter<ResponseBody, ApiError> converter= ApiClient.retrofit.responseBodyConverter(ApiError.class,new Annotation[0]);
        ApiError apiError;
        try{
//            Log.e("TAG", "errorBody= "+response.errorBody());
//            Log.e("TAG", "resCode= "+response.code());
            apiError=converter.convert(response.errorBody());
        }catch (Exception e) {
            return new ApiError();
        }
        return apiError;
    }
}
