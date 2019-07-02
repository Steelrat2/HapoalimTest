package com.hapoalimtest.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.hapoalimtest.model.Movie;
import com.hapoalimtest.persistence.MoviesDatabase;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class LikedMoviesViewModel extends AndroidViewModel {

    private Executor executor;
    private LiveData<PagedList<Movie>> articleLiveData;

    public LikedMoviesViewModel(@NonNull Application application) {
        super(application);
        init();
    }

    private void init() {
        executor = Executors.newFixedThreadPool(2);

        DataSource.Factory<Integer, Movie> repository = MoviesDatabase.getInstance(getApplication()).movieDao().getAllPaged();

        articleLiveData = new LivePagedListBuilder<>(repository, 50)
                .setFetchExecutor(executor)
                .build();
    }

    public LiveData<PagedList<Movie>> getArticleLiveData() {
        return articleLiveData;
    }
}
