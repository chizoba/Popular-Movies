package com.github.chizoba.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    ImageView moviePoster;
    TextView toolbarTitle, userRating, releaseDate, plotSynopsis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initializeViews();

        Intent receivedIntent = getIntent();

        if (receivedIntent.hasExtra(Constants.MOVIE_POSTER)) {
            String url = Constants.POSTER_BASE_URL+Constants.POSTER_SIZE+receivedIntent.getStringExtra(Constants.MOVIE_POSTER);
            Picasso.with(this).load(url).into(moviePoster);
            toolbar.setTitle(receivedIntent.getStringExtra(Constants.ORIGINAL_TITLE));
            toolbarTitle.setText(receivedIntent.getStringExtra(Constants.ORIGINAL_TITLE));
            plotSynopsis.setText(receivedIntent.getStringExtra(Constants.PLOT_SYNOPSIS));
            userRating.setText(String.valueOf(receivedIntent.getDoubleExtra(Constants.USER_RATING, -1)));
            releaseDate.setText(receivedIntent.getStringExtra(Constants.RELEASE_DATE));
        }
    }

    private void initializeViews() {
        moviePoster = (ImageView) findViewById(R.id.poster_image);
        userRating = (TextView) findViewById(R.id.user_rating);
        releaseDate = (TextView) findViewById(R.id.release_date);
        plotSynopsis = (TextView) findViewById(R.id.plot_synopsis);
        toolbarTitle = (TextView) findViewById(R.id.toolbar_title);
    }
}
