package com.hapoalimtest.ui;



import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.hapoalimtest.R;
import com.hapoalimtest.model.Movie;
import com.hapoalimtest.net.PosterPoint;
import com.hapoalimtest.utils.NetworkState;
import com.squareup.picasso.Picasso;



public class MoviesAdapter extends PagedListAdapter<Movie, RecyclerView.ViewHolder> {


    public static class MovieHolder extends RecyclerView.ViewHolder {

        ImageView poster;

        public MovieHolder(View view) {
            super(view);
            poster = (ImageView) view.findViewById(R.id.posterImageView);
        }
    }

    private NetworkState newNetworkState;
    private MainFragment.OnFragmentListener listener;

    public MoviesAdapter(MainFragment.OnFragmentListener listener) {
        super(Movie.DIFF_CALLBACK);
        this.listener = listener;
    }

    @Override
    public MovieHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.card_movie, parent, false);
        return new MovieHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MovieHolder) {
            final Movie movie = getItem(position);
          //  "https://image.tmdb.org/t/p/w92/grplMPQkepQEfkBQ2Oa5bZQ2mqa.jpg"
            Picasso.with(holder.itemView.getContext()).load(PosterPoint.POSTER_POINT + PosterPoint.W92 + movie.getPoster_path()).into(((MovieHolder) holder).poster);
            ((MovieHolder) holder).poster.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null) {
                        listener.onOpenMovieFragment(movie);
                    }
                }
            });

        }
    }

    public void setNetworkState(NetworkState newNetworkState) {
        this.newNetworkState = newNetworkState;
    }

}
