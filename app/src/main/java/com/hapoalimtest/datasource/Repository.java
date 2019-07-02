package com.hapoalimtest.datasource;


import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.hapoalimtest.model.Movie;

public class Repository extends DataSource.Factory<Long, Movie> {

    private MutableLiveData<MoviesDataSource> mutableLiveData;
    private MoviesDataSource moviesDataSource;
    private TypeRepository typeRepository;

    public enum TypeRepository {
        CLOUD,
        DB
    }

    public Repository(TypeRepository typeRepository) {
        this.typeRepository = typeRepository;
        this.mutableLiveData = new MutableLiveData<>();
    }

    @Override
    public DataSource<Long, Movie> create() {
        moviesDataSource = new MoviesDataSource();
        mutableLiveData.postValue(moviesDataSource);
        return moviesDataSource;
    }


    public MutableLiveData<MoviesDataSource> getMutableLiveData() {
        return mutableLiveData;
    }
}
