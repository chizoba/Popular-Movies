package com.github.chizoba.popularmovies;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.chizoba.popularmovies.utilities.JsonUtils;
import com.github.chizoba.popularmovies.utilities.NetworkUtils;

import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ProgressBar mLoadingIndicator;

    ArrayList<Movie> movieList;

    private GridView gridView;

    private TextView mErrorMessageDisplay;

    private MovieAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();

        makeGithubSearchQuery(R.id.sort_by_popular);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.sort_by_popular) {
            makeGithubSearchQuery(id);
            return true;
        }

        if (id == R.id.sort_by_top_rated) {
            makeGithubSearchQuery(id);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initializeViews() {
        mLoadingIndicator = (ProgressBar) findViewById(R.id.progress_bar);
        mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);
        gridView = (GridView) findViewById(R.id.grid_view);
    }

    /**
     * This method constructs the URL (using {@link NetworkUtils}) for the movies
     * that are on themoviesdb, and finally fires off an AsyncTask to perform the GET request using
     * our {@link GithubQueryTask}
     */
    private void makeGithubSearchQuery(int id) {
        gridView.setVisibility(View.INVISIBLE);
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mLoadingIndicator.setVisibility(View.VISIBLE);
        URL searchUrl = NetworkUtils.buildUrl(id);
        new GithubQueryTask().execute(searchUrl);
    }

    /**
     * This method will make the View for the JSON data visible and
     * hide the error message.
     */
    private void showJsonDataView() {
        // First, make sure the error is invisible
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        // Then, make sure the JSON data is visible
        gridView.setVisibility(View.VISIBLE);
    }

    /**
     * This method will make the error message visible and hide the JSON
     * View.
     */
    private void showErrorMessage() {
        // First, hide the currently visible data
        gridView.setVisibility(View.INVISIBLE);
        // Then, show the error
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    private class GithubQueryTask extends AsyncTask<URL, Void, ArrayList<Movie>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<Movie> doInBackground(URL... urls) {
            URL searchUrl = urls[0];
            String searchResults = null;
            try {
                searchResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);

                movieList = JsonUtils
                        .getStringsFromJson(MainActivity.this, searchResults);

                return movieList;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(final ArrayList<Movie> searchResults) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);

            if (searchResults != null) {

                showJsonDataView();

                mAdapter = new MovieAdapter(MainActivity.this, searchResults);
                gridView.setAdapter(mAdapter);
                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                        Intent i = new Intent(MainActivity.this, DetailActivity.class);

                        i.putExtra(Constants.MOVIE_POSTER, searchResults.get(position).moviePoster);
                        i.putExtra(Constants.ORIGINAL_TITLE, searchResults.get(position).originalTitle);
                        i.putExtra(Constants.PLOT_SYNOPSIS, searchResults.get(position).plotSynopsis);
                        i.putExtra(Constants.USER_RATING, searchResults.get(position).userRating);
                        i.putExtra(Constants.RELEASE_DATE, searchResults.get(position).releaseDate);

                        startActivity(i);
                    }
                });
            } else {
                showErrorMessage();
            }
        }
    }
}
