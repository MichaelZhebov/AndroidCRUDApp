package com.example.crudtest.util;

import com.example.crudtest.rest.JSONPlaceHolderApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkService {

    private static NetworkService mInstance;
    private static final String BASE_URL = "https://androidcrudtest.herokuapp.com";
    private Retrofit mRetrofit;

    private NetworkService() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static NetworkService getInstance() {
        NetworkService result = mInstance;
        if (result != null) {
            return mInstance;
        }
        synchronized (NetworkService.class) {
            if (mInstance == null) {
                mInstance = new NetworkService();
            }
            return mInstance;
        }
    }

    public JSONPlaceHolderApi getJSONApi() {
        return mRetrofit.create(JSONPlaceHolderApi.class);
    }
}
