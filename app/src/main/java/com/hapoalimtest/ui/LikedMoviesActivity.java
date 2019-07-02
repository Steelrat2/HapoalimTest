package com.hapoalimtest.ui;

import android.content.res.Configuration;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.hapoalimtest.R;
import com.hapoalimtest.model.Movie;
import com.hapoalimtest.viewmodels.LikedMoviesViewModel;


public class LikedMoviesActivity extends AppCompatActivity {

    private MoviesAdapter mMoviesAdapter;
    private LikedMoviesViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liked_movies);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.liked_movies);

        mViewModel = ViewModelProviders.of(this).get(LikedMoviesViewModel.class);
        RecyclerView recyclerView = findViewById(R.id.moviesRecyclerView);

        Configuration configuration = getResources().getConfiguration();
        int screenWidthDp = configuration.screenWidthDp;
        recyclerView.setLayoutManager(new GridLayoutManager(this, (int)(screenWidthDp/110)));
        mMoviesAdapter = new MoviesAdapter(null);
        recyclerView.setAdapter(mMoviesAdapter);

        mViewModel.getArticleLiveData().observe(this, new Observer<PagedList<Movie>>() {
            @Override
            public void onChanged(PagedList<Movie> movies) {
                mMoviesAdapter.submitList(movies);
            }
        });
    }


}
