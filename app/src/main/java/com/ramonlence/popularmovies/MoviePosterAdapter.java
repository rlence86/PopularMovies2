package com.ramonlence.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ramonlence.popularmovies.entities.Movie;
import com.ramonlence.popularmovies.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ramon on 24/1/17.
 */

public class MoviePosterAdapter extends RecyclerView.Adapter<MoviePosterAdapter.MovieViewHolder> {

    private int mNumberOfItems;
    private ArrayList<Movie> mMoviesData;
    private final MoviePosterClickHandler mClickHandler;

    public MoviePosterAdapter(MoviePosterClickHandler mPClickHandler){
        mClickHandler = mPClickHandler;
    }

    public interface MoviePosterClickHandler {
        void onClick(Movie selectedMovie);
    }

    public void setMoviesData(ArrayList<Movie> moviesData){
        mMoviesData = moviesData;
        mNumberOfItems = moviesData.size();
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        Movie movieInPosition = mMoviesData.get(position);
        Context context = holder.mViewPoster.getContext();
        URL imageUrl = NetworkUtils.buildImageUrl(movieInPosition.getPoster_path(),"normal");
        Picasso.with(context).load(imageUrl.toString()).into(holder.mViewPoster);
    }

    @Override
    public int getItemCount() {
        return mNumberOfItems;
    }

    @Override
    public MoviePosterAdapter.MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.movie_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        int gridColsNumber = context.getResources()
                .getInteger(R.integer.number_cols);

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        view.getLayoutParams().height = (int) (parent.getWidth() / gridColsNumber * 1.5f);
        return new MovieViewHolder(view);
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView mViewPoster;
        public MovieViewHolder(View view){
            super(view);
            mViewPoster = (ImageView) view.findViewById(R.id.movie_poster_in_single);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v){
            int adapterPosition = getAdapterPosition();
            Movie selectedMovie = mMoviesData.get(adapterPosition);
            mClickHandler.onClick(selectedMovie);
        }
    }

}
