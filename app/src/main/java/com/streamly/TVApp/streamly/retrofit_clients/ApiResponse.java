package com.streamly.TVApp.streamly.retrofit_clients;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ApiResponse<T> {
    private final T apiResponse;
    private final Throwable error;
    private int responseCode;


    public ApiResponse(@Nullable T result, int responseCode) {
        this.apiResponse = result;
        this.error = null;
        this.responseCode=responseCode;
    }

    public ApiResponse(@Nullable Throwable error) {
        this.apiResponse = null;
        this.error = error;
    }

    @NonNull
    public static <T> ApiResponse<T> success(@NonNull T result, int responseCode) {
        return new ApiResponse(result,responseCode);
    }

    @NonNull
    public static <T> ApiResponse<T> error(@NonNull Throwable error) {
        return new ApiResponse(error);
    }

    @Nullable
    public T getApiResponse() {
        return apiResponse;
    }

    @Nullable
    public Throwable getError() {
        return error;
    }

    public int getResponseCode() {
        return responseCode;
    }
}
