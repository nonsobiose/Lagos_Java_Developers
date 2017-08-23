package com.nonsobiose.lagdev.rest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by slimb on 12/07/2017.
 */

public class ApiClient {
    private static final String GITHUB_API_STRING = "https://api.github.com/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if(retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(GITHUB_API_STRING)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
