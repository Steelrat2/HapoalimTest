package com.hapoalimtest.net;

import com.hapoalimtest.model.DiscoverResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by Dmitriy on 19.06.2017.
 */

public interface ThemoviedbApi {

    String API_KEY = "227d4a07bb9eb0b72ea9a707f9ca0e1a";

    @GET("3/discover/movie")
    Call<DiscoverResponse> getLastTopMovies(@QueryMap Map<String, String> options);

}
