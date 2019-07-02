package com.hapoalimtest.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.hapoalimtest.datasource.MoviesDataSource;
import com.hapoalimtest.datasource.Repository;
import com.hapoalimtest.model.Movie;
import com.hapoalimtest.persistence.MoviesDatabase;
import com.hapoalimtest.utils.NetworkState;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainViewModel extends AndroidViewModel {

    public MainViewModel(@NonNull Application application) {
        super(application);
        init();
    }

    private Executor executor;
    private LiveData<NetworkState> networkState;
    private LiveData<PagedList<Movie>> articleLiveData;

    private Movie movie;


    private void init() {
        executor = Executors.newFixedThreadPool(5);

        Repository repository = new Repository(Repository.TypeRepository.CLOUD);
        networkState = Transformations.switchMap(repository.getMutableLiveData(),
                new Function<MoviesDataSource, LiveData<NetworkState>>() {
                    @Override
                    public LiveData<NetworkState> apply(MoviesDataSource dataSource) {
                        return dataSource.getNetworkState();
                    }
                });

        PagedList.Config pagedListConfig = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
            //    .setInitialLoadSizeHint(20)
                .setPageSize(20)
                .build();

        articleLiveData = new LivePagedListBuilder<>(repository, pagedListConfig)
                .setFetchExecutor(executor)
                .build();
    }


    public LiveData<NetworkState> getNetworkState() {
        return networkState;
    }

    public LiveData<PagedList<Movie>> getArticleLiveData() {
        return articleLiveData;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public void insertOrUpdate(final Movie movie) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                MoviesDatabase.getInstance(getApplication()).movieDao().insertMovie(movie);
            }
        });

    }
}
