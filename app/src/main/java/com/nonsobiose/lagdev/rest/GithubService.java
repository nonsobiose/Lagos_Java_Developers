package com.nonsobiose.lagdev.rest;

import com.nonsobiose.lagdev.model.DeveloperProfile;
import com.nonsobiose.lagdev.model.JSONResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by slimb on 12/07/2017.
 */

public interface GithubService {

    @GET("https://api.github.com/search/users?q=location:lagos+language:java")
    Call<JSONResponse> getJSONResponse();

    @GET("https://api.github.com/users/{user}")
    Call<DeveloperProfile> getDeveloperProfile(@Path("user") String developerName);

}