package com.ramonlence.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ramonlence.popularmovies.adapters.MoviePosterAdapter;
import com.ramonlence.popularmovies.adapters.ReviewsAdapter;
import com.ramonlence.popularmovies.adapters.TrailersAdapter;
import com.ramonlence.popularmovies.entities.Movie;
import com.ramonlence.popularmovies.entities.Review;
import com.ramonlence.popularmovies.entities.Trailer;
import com.ramonlence.popularmovies.utilities.MovieReaderFromJson;
import com.ramonlence.popularmovies.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.net.URL;
import java.util.ArrayList;

public class MovieActivity extends AppCompatActivity {

    TextView mMovieTitle;
    TextView mMovieSynopsis;
    TextView mUserRating;
    TextView mReleaseDate;
    ImageView mPosterView;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    private static Movie movieToShow;

    private FloatingActionButton fabButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        //Get all movie data
        Intent currentIntent = getIntent();
        if(currentIntent.hasExtra("SelectedMovie")){
            movieToShow = (Movie) currentIntent.getSerializableExtra("SelectedMovie");
        }

        fabButton = (FloatingActionButton) findViewById(R.id.fab);
        fabButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Context context = getApplicationContext();
                CharSequence text = movieToShow.getOriginal_title() + "saved!";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_movie, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            Bundle args = getArguments();
            int section = 1;
            if(args != null){
                section = args.getInt(ARG_SECTION_NUMBER);
            }
            View rootView = null;
            switch (section){
                case 1:
                    rootView = showMovieDetailsView(inflater, container);
                    break;
                case 2:
                    rootView = showVideosForMovie(inflater, container);
                    break;
                case 3:
                    rootView = showReviewsForMovie(inflater, container);
                    break;
            }
            return rootView;
        }

        private View showMovieDetailsView(LayoutInflater inflater, ViewGroup container) {
            View rootView = inflater.inflate(R.layout.fragment_movie, container, false);
            TextView mMovieTitle = (TextView) rootView.findViewById(R.id.movie_title_in_single);
            TextView mMovieSynopsis = (TextView) rootView.findViewById(R.id.movie_synopsis);
            TextView mUserRating = (TextView) rootView.findViewById(R.id.user_rating);
            TextView mReleaseDate = (TextView) rootView.findViewById(R.id.release_date);
            ImageView mPosterView = (ImageView) rootView.findViewById(R.id.movie_poster);
            mMovieTitle.setText(movieToShow.getOriginal_title());
            mMovieSynopsis.setText(movieToShow.getOverview());
            mUserRating.setText(movieToShow.getVote_average().toString());
            mReleaseDate.setText(movieToShow.getRelease_date());
            URL imageUrl = NetworkUtils.buildImageUrl(movieToShow.getPoster_path(),"small");
            Picasso.with(mPosterView.getContext()).load(imageUrl.toString()).into(mPosterView);
            return rootView;
        }

        private View showVideosForMovie(LayoutInflater inflater, ViewGroup container) {
            View rootView = inflater.inflate(R.layout.fragment_videos, container, false);
            final ProgressBar progressBar = (ProgressBar) rootView.findViewById(R.id.loading_indicator_trailer);
            final TextView errorView = (TextView) rootView.findViewById(R.id.video_error_message_display);
            final TextView emptyMessageView = (TextView) rootView.findViewById(R.id.video_no_video_message);
            progressBar.setVisibility(View.VISIBLE);
            final RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview_trailers);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setHasFixedSize(true);
            final TrailersAdapter trailersAdapter = new TrailersAdapter();
            recyclerView.setAdapter(trailersAdapter);
            AsyncTask trailersFetch = new AsyncTask() {
                @Override
                protected Object doInBackground(Object[] objects) {
                    if(objects.length == 0){
                        return null;
                    }
                    int movieId = (int) objects[0];
                    URL trailersURL = NetworkUtils.buildExtraUrl(movieId, "videos");
                    try {
                        String jsonTrailerResponse = NetworkUtils
                                .getResponseFromHttpUrl(trailersURL);

                        ArrayList<Trailer> result = MovieReaderFromJson.readTrailers(jsonTrailerResponse);

                        return result;

                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                }

                @Override
                protected void onPostExecute(Object o) {
                    progressBar.setVisibility(View.INVISIBLE);
                    if (o != null) {
                        ArrayList<Trailer> trailers = (ArrayList<Trailer>) o;
                        trailersAdapter.setTrailersData(trailers);
                        if(trailersAdapter.getItemCount()==0){
                            emptyMessageView.setVisibility(View.VISIBLE);
                        } else {
                            recyclerView.setVisibility(View.VISIBLE);
                        }
                    } else {
                        errorView.setVisibility(View.VISIBLE);
                    }
                }
            };
            trailersFetch.execute(movieToShow.getId());
            return rootView;
        }

        private View showReviewsForMovie(LayoutInflater inflater, ViewGroup container) {
            View rootView = inflater.inflate(R.layout.frament_reviews, container, false);
            final ProgressBar progressBar = (ProgressBar) rootView.findViewById(R.id.loading_indicator_reviews);
            final TextView errorView = (TextView) rootView.findViewById(R.id.reviews_error_message_display);
            final TextView emptyMessageView = (TextView) rootView.findViewById(R.id.reviews_no_video_message);
            progressBar.setVisibility(View.VISIBLE);
            final RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview_reviews);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setHasFixedSize(true);
            final ReviewsAdapter reviewsAdapter = new ReviewsAdapter();
            recyclerView.setAdapter(reviewsAdapter);
            AsyncTask trailersFetch = new AsyncTask() {
                @Override
                protected Object doInBackground(Object[] objects) {
                    if(objects.length == 0){
                        return null;
                    }
                    int movieId = (int) objects[0];
                    URL reviewsURL = NetworkUtils.buildExtraUrl(movieId, "reviews");
                    try {
                        String jsonReviewsResponse = NetworkUtils
                                .getResponseFromHttpUrl(reviewsURL);

                        ArrayList<Review> result = MovieReaderFromJson.readReviews(jsonReviewsResponse);

                        return result;

                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                }

                @Override
                protected void onPostExecute(Object o) {
                    progressBar.setVisibility(View.INVISIBLE);
                    if (o != null) {
                        ArrayList<Review> reviews = (ArrayList<Review>) o;
                        reviewsAdapter.setmReviewsData(reviews);
                        if(reviewsAdapter.getItemCount()==0){
                            emptyMessageView.setVisibility(View.VISIBLE);
                        } else {
                            recyclerView.setVisibility(View.VISIBLE);
                        }
                    } else {
                        errorView.setVisibility(View.VISIBLE);
                    }
                }
            };
            trailersFetch.execute(movieToShow.getId());
            return rootView;
        }

    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.details);
                case 1:
                    return getString(R.string.videos);
                case 2:
                    return getString(R.string.reviews);
            }
            return null;
        }
    }
}
