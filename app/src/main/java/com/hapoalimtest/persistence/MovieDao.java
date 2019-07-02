package com.hapoalimtest.persistence;


import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.hapoalimtest.model.Movie;

import java.util.List;


@Dao
public interface MovieDao {

    @Query("SELECT * FROM movies")
    LiveData<List<Movie>> getMoviess();

    @Query("SELECT * FROM movies")
    DataSource.Factory<Integer, Movie> getAllPaged();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertMovie(Movie movie);

    @Query("DELETE FROM movies")
    void deleteAllMovies();
}
