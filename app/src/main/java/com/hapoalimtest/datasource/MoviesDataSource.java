package com.hapoalimtest.datasource;


import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;

import com.hapoalimtest.model.DiscoverResponse;
import com.hapoalimtest.model.Movie;
import com.hapoalimtest.net.Client;
import com.hapoalimtest.net.ThemoviedbApi;
import com.hapoalimtest.utils.NetworkState;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Callback;




public class MoviesDataSource extends PageKeyedDataSource<Long, Movie> {

    private static final String TAG = MoviesDataSource.class.getSimpleName();

//    private AppController appController;

    private MutableLiveData<NetworkState> networkState = new MutableLiveData<>();;
    private MutableLiveData<NetworkState> initialLoading = new MutableLiveData<>();

//    public FeedDataSource(/*AppController appController*/) {
////        this.appController = appController;
//
//        networkState = new MutableLiveData<>();
//        initialLoading = new MutableLiveData<>();
//    }


    public MutableLiveData<NetworkState> getNetworkState() {
        return networkState;
    }

    public MutableLiveData<NetworkState> getInitialLoading() {
        return initialLoading;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Long> params,
                            @NonNull final LoadInitialCallback<Long, Movie> callback) {

        initialLoading.postValue(NetworkState.LOADING);
        networkState.postValue(NetworkState.LOADING);

        //List<Country> clist = null;
        ThemoviedbApi themoviedbApi = Client.getThemoviedbApi();
//api_key=227d4a07bb9eb0b72ea9a707f9ca0e1a&language=en-US&sort_by=vote_average.desc&include_adult=false&include_video=false&page=1&year=2019
        Map<String, String> reqParam = new HashMap<>();
        reqParam.put("api_key", ThemoviedbApi.API_KEY);
        reqParam.put("language", "en-US");
        reqParam.put("sort_by", "vote_average.desc");
        reqParam.put("include_adult", "false");
        reqParam.put("include_video", "false");
        reqParam.put("page", "1");
        reqParam.put("year", "2019");

        themoviedbApi.getLastTopMovies(reqParam)
                .enqueue(new Callback<DiscoverResponse>() {
                    @Override
                    public void onResponse(Call<DiscoverResponse> call, Response<DiscoverResponse> response) {
                        if(response.isSuccessful()) {
                            callback.onResult(response.body().getResults(), null, 2L);
                            initialLoading.postValue(NetworkState.LOADED);
                            networkState.postValue(NetworkState.LOADED);

                        } else {
                            initialLoading.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
                            networkState.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
                        }
                    }

                    @Override
                    public void onFailure(Call<DiscoverResponse> call, Throwable t) {
                        String errorMessage = t == null ? "unknown error" : t.getMessage();
                        networkState.postValue(new NetworkState(NetworkState.Status.FAILED, errorMessage));
                    }
                });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Long> params,
                           @NonNull LoadCallback<Long, Movie> callback) {

    }

    @Override
    public void loadAfter(@NonNull final LoadParams<Long> params,
                          @NonNull final LoadCallback<Long, Movie> callback) {

        Log.i(TAG, "Loading Rang " + params.key + " Count " + params.requestedLoadSize);

        networkState.postValue(NetworkState.LOADING);
        ThemoviedbApi themoviedbApi = Client.getThemoviedbApi();

        Map<String, String> reqParam = new HashMap<>();
        reqParam.put("api_key", ThemoviedbApi.API_KEY);
        reqParam.put("language", "en-US");
        reqParam.put("sort_by", "vote_average.desc");
        reqParam.put("include_adult", "false");
        reqParam.put("include_video", "false");
        reqParam.put("page", params.key.toString());
        reqParam.put("year", "2019");

        themoviedbApi.getLastTopMovies(reqParam).enqueue(new Callback<DiscoverResponse>() {
            @Override
            public void onResponse(Call<DiscoverResponse> call, Response<DiscoverResponse> response) {
                if(response.isSuccessful()) {
                    long nextKey = (params.key == response.body().getTotal_results()) ? null : (params.key + 1);
                    callback.onResult(response.body().getResults(), nextKey);
                    networkState.postValue(NetworkState.LOADED);

                }
                else {
                    networkState.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
                }
            }

            @Override
            public void onFailure(Call<DiscoverResponse> call, Throwable t) {
                String errorMessage = t == null ? "unknown error" : t.getMessage();
                networkState.postValue(new NetworkState(NetworkState.Status.FAILED, errorMessage));
            }
        });
    }
}
