package com.ramonlence.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ramonlence.popularmovies.adapters.MoviePosterAdapter;
import com.ramonlence.popularmovies.entities.Movie;
import com.ramonlence.popularmovies.utilities.FavoriteMoviesManager;
import com.ramonlence.popularmovies.utilities.MovieReaderFromJson;
import com.ramonlence.popularmovies.utilities.NetworkUtils;

import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MoviePosterAdapter.MoviePosterClickHandler {

    public static final int POPULAR_OPTION = 1;
    public static final int RATED_OPTION = 2;
    public static final int FAVORITES_OPTION = 3;
    private static final String PATH_POPULAR = "popular";
    private static final String PATH_RATED = "top_rated";
    private static final String SELECTED_OPT = "selected_option";
    private static final String LIST_STATE = "list_state";

    private MoviePosterAdapter mPosterAdapter;
    private RecyclerView mRecyclerView;
    private TextView mErrorMessageDisplay;
    private ProgressBar mProgressBar;

    private TextView mTextView;

    private int selectedOption;

    private Parcelable mListState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_movies);

        mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);

        mRecyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, getResources()
                .getInteger(R.integer.number_cols)));

        mRecyclerView.setHasFixedSize(true);

        mPosterAdapter = new MoviePosterAdapter(this);

        mRecyclerView.setAdapter(mPosterAdapter);

        mProgressBar = (ProgressBar) findViewById(R.id.loading_indicator);

        if(savedInstanceState != null){
            selectedOption = savedInstanceState.getInt(SELECTED_OPT);
            mListState = savedInstanceState.getParcelable(LIST_STATE);
        } else {
            selectedOption = POPULAR_OPTION;
        }

        loadMovies(selectedOption);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(SELECTED_OPT, selectedOption);
        Parcelable mListState = mRecyclerView.getLayoutManager().onSaveInstanceState();
        outState.putParcelable(LIST_STATE, mListState);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(selectedOption == FAVORITES_OPTION) {
            loadMovies(FAVORITES_OPTION);
        }
    }

    /**
     * Shows error message and hides mRecyclerView
     */
    private void showError() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    /**
     * Hides error message and shows mRecyclerView
     */
    private void showMoviesPoster(){
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    /**
     * Loads movies by criteria
     */
    private void loadMovies(int criteria){
        selectedOption = criteria;
        new FetchMoviesTask().execute(criteria);
    }

    /**
     * Starts child activity when click on any Movie Poster
     * @param selectedMovie
     */
    public void onClick(Movie selectedMovie){
        Context context = MainActivity.this;
        Class destinationActivity = MovieActivity.class;
        Intent startChildActivityIntent = new Intent(context, destinationActivity);
        startChildActivityIntent.putExtra("SelectedMovie", selectedMovie);
        startActivity(startChildActivityIntent);
    }

    /**
     * FetchMoviesTask loads data from the API in background
     */
    public class FetchMoviesTask extends AsyncTask<Integer, Integer, ArrayList<Movie>> {

        @Override
        protected void onPreExecute() {
            mProgressBar.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected ArrayList<Movie> doInBackground(Integer... option) {

            if (option.length==0 || (POPULAR_OPTION != option[0] && RATED_OPTION != option[0] && FAVORITES_OPTION != option[0])) {
                return null;
            }
            String queryPath = "";
            if(POPULAR_OPTION == option[0]){
                queryPath = PATH_POPULAR;
            } else if(RATED_OPTION == option[0]){
                queryPath = PATH_RATED;
            } else if(FAVORITES_OPTION == option[0]){
                FavoriteMoviesManager fMoviesManager = new FavoriteMoviesManager();
                ArrayList<Movie> favoriteMovies = fMoviesManager.getAllFavoriteMovies(getApplicationContext());
                return favoriteMovies;
            }

            URL moviesRequestUrl = NetworkUtils.buildUrl(queryPath);

            try {
                String jsonMoviesResponse = NetworkUtils
                        .getResponseFromHttpUrl(moviesRequestUrl);

                ArrayList<Movie> result = MovieReaderFromJson.readMovies(jsonMoviesResponse);

                return result;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<Movie> movies) {
            mProgressBar.setVisibility(View.INVISIBLE);
            if (movies != null) {
                mPosterAdapter.setMoviesData(movies);
                showMoviesPoster();
                restoreLayoutPosition();
            } else {
                showError();
            }
        }
    }

    private void restoreLayoutPosition() {
        if (mListState != null) {
            mRecyclerView.getLayoutManager().onRestoreInstanceState(mListState);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedId = item.getItemId();
        if (itemThatWasClickedId == R.id.menu_popular) {
            loadMovies(POPULAR_OPTION);
            return true;
        } else if(itemThatWasClickedId == R.id.menu_toprated) {
            loadMovies(RATED_OPTION);
            return true;
        } else if(itemThatWasClickedId == R.id.menu_favorites) {
            loadMovies(FAVORITES_OPTION);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
