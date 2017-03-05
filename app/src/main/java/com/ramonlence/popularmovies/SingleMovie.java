package com.ramonlence.popularmovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.ramonlence.popularmovies.entities.Movie;
import com.ramonlence.popularmovies.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.net.URL;

public class SingleMovie extends AppCompatActivity {

    TextView mMovieTitle;
    TextView mMovieSynopsis;
    TextView mUserRating;
    TextView mReleaseDate;
    ImageView mPosterView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_movie);
        mMovieTitle = (TextView) findViewById(R.id.movie_title_in_single);
        mMovieSynopsis = (TextView) findViewById(R.id.movie_synopsis);
        mUserRating = (TextView) findViewById(R.id.user_rating);
        mReleaseDate = (TextView) findViewById(R.id.release_date);
        mPosterView = (ImageView) findViewById(R.id.movie_poster);

        Intent currentIntent = getIntent();

        if(currentIntent.hasExtra("SelectedMovie")){
            Movie movieToShow = (Movie) currentIntent.getSerializableExtra("SelectedMovie");
            mMovieTitle.setText(movieToShow.getOriginal_title());
            mMovieSynopsis.setText(movieToShow.getOverview());
            mUserRating.setText(movieToShow.getVote_average().toString());
            mReleaseDate.setText(movieToShow.getRelease_date());
            URL imageUrl = NetworkUtils.buildImageUrl(movieToShow.getPoster_path(),"small");
            Picasso.with(mPosterView.getContext()).load(imageUrl.toString()).into(mPosterView);
        }
    }
}
