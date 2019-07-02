package com.hapoalimtest.ui;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hapoalimtest.R;
import com.hapoalimtest.model.Movie;
import com.hapoalimtest.utils.NetworkState;
import com.hapoalimtest.viewmodels.MainViewModel;


public class MainFragment extends Fragment {

    public interface OnFragmentListener {
        void onOpenMovieFragment(Movie movie);
    }

    private MainViewModel mViewModel;
    private MoviesAdapter mMoviesAdapter;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
        RecyclerView recyclerView = view.findViewById(R.id.moviesRecyclerView);

//        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
//        float dpHeight = displayMetrics.heightPixels / displayMetrics.density;
//        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;

        Configuration configuration = getResources().getConfiguration();
        int screenWidthDp = configuration.screenWidthDp;
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), (int)(screenWidthDp/110)));
        mMoviesAdapter = new MoviesAdapter((OnFragmentListener)getActivity());
        recyclerView.setAdapter(mMoviesAdapter);

        mViewModel.getArticleLiveData().observe(this, new Observer<PagedList<Movie>>() {
            @Override
            public void onChanged(PagedList<Movie> movies) {
                mMoviesAdapter.submitList(movies);
                ((DialogProgressManagement)getActivity()).dismissDialogProgress();
            }
        });

        mViewModel.getNetworkState().observe(this, new Observer<NetworkState>() {
            @Override
            public void onChanged(NetworkState networkState) {
                mMoviesAdapter.setNetworkState(networkState);
                if(networkState.getStatus()== NetworkState.Status.FAILED) {
                    ((DialogProgressManagement)getActivity()).dismissDialogProgress();
                    Toast.makeText(getContext(), networkState.getMsg(), Toast.LENGTH_LONG).show();
                }
            }
        });

        ((DialogProgressManagement)getActivity()).showDialogProgress();

        return view;
    }


//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
// //       mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
//    }

}
