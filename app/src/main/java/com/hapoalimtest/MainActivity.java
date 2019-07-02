package com.hapoalimtest;

import android.content.Intent;
import android.os.Bundle;

import com.hapoalimtest.model.Movie;
import com.hapoalimtest.ui.DialogProgressManagement;
import com.hapoalimtest.ui.LikedMoviesActivity;
import com.hapoalimtest.ui.MainFragment;
import com.hapoalimtest.viewmodels.MainViewModel;
import com.hapoalimtest.ui.MovieFragment;
import com.hapoalimtest.ui.ProgressDialogFragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;

import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements DialogProgressManagement, MainFragment.OnFragmentListener {

    private ProgressDialogFragment mProgressDialog = new ProgressDialogFragment();
    private MainViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.top_movies_2019);

        if(savedInstanceState==null) {
            replaceFragment(MainFragment.newInstance());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

       if (id == R.id.action_liked_movies) {
           Intent intent = new Intent(getApplicationContext(), LikedMoviesActivity.class);
           //intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
           startActivity(intent);
           return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected void replaceFragment(@NonNull Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        if(fm != null) {
            fm.beginTransaction().replace(R.id.mount_point, fragment, fragment.getClass().getSimpleName()).commitAllowingStateLoss();
        }
    }

    protected void addFragment(@NonNull Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        if(fm != null) {
            fm.beginTransaction().add(R.id.mount_point, fragment, fragment.getClass().getSimpleName()).addToBackStack(fragment.getClass().getName()).commitAllowingStateLoss();
        }
    }

    @Override
    public void showDialogProgress() {
        if(!mProgressDialog.isAdded()) {
            mProgressDialog.show(getFragmentManager(), "progressDialog");
        }
    }

    @Override
    public boolean isShowingDialogProgress() {
        return mProgressDialog.isAdded();
    }

    @Override
    public void dismissDialogProgress() {
        mProgressDialog.dismissAllowingStateLoss();
    }

    @Override
    public void onOpenMovieFragment(Movie movie) {
        mViewModel.setMovie(movie);
        addFragment(MovieFragment.newInstance(movie));
    }
}
