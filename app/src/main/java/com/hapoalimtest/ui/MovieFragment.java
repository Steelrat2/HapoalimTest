package com.hapoalimtest.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hapoalimtest.R;
import com.hapoalimtest.model.Movie;
import com.hapoalimtest.net.PosterPoint;
import com.hapoalimtest.viewmodels.MainViewModel;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MovieFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MovieFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MovieFragment extends Fragment {

    private static final String TAG = MovieFragment.class.getSimpleName();
    private static final String ARG_PARAM_MOVIE = "param_movie";


    private OnFragmentInteractionListener mListener;

    private MainViewModel mViewModel;
    private Movie mMovie;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param
     * @return A new instance of fragment MovieFragment.
     */
    public static MovieFragment newInstance(Movie movie) {
        MovieFragment fragment = new MovieFragment();
        Bundle args = new Bundle();
//        args.putParcelable(ARG_PARAM_MOVIE, movie);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mMovie = (Movie)getArguments().getParcelable(ARG_PARAM_MOVIE);
        }
        mViewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie, container, false);
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
        mMovie = mViewModel.getMovie();
        if(mMovie != null) {
            TextView nameTextView = view.findViewById(R.id.nameTextView);
            nameTextView.setText(mMovie.getTitle());
            //  "https://image.tmdb.org/t/p/w154/grplMPQkepQEfkBQ2Oa5bZQ2mqa.jpg"
            Picasso.with(getContext()).load(PosterPoint.POSTER_POINT + PosterPoint.W154 + mMovie.getPoster_path())
                    .into((ImageView)view.findViewById(R.id.bigPosterImageView));

            TextView ratingTextView = view.findViewById(R.id.ratingTextView);
            ratingTextView.setText(String.valueOf(mMovie.getVote_average()));
            TextView releaseTextView = view.findViewById(R.id.releaseTextView);
            // 2019-04-18 "yyyy-MM-dd"  "dd MMMMM yyyy"
            String release = dateFormat(dateFromString(mMovie.getRelease_date(), "yyyy-MM-dd"),"dd MMM yyyy");
            releaseTextView.setText(release);
            TextView tw = view.findViewById(R.id.descriptionTextView);
            tw.setText(mMovie.getOverview());
            view.findViewById(R.id.likeButton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mViewModel.insertOrUpdate(mMovie);
                }
            });
        }
        return view;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        }
//        else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public static Date dateFromString(String data, String pattern){
        Date date = null;
        SimpleDateFormat formater = new SimpleDateFormat(pattern);
        try{
            date = formater.parse(data);
        } catch(ParseException e){
            Log.e(TAG, "Can't parse date '" + data + "' with pattern '" + pattern + "'", e);
        }
        return date;
    }

    public static String dateFormat(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format, new Locale("en", "US"));
        return sdf.format(date);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);
    }
}
