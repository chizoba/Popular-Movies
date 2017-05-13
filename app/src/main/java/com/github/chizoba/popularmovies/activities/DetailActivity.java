package com.github.chizoba.popularmovies.activities;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.chizoba.popularmovies.Constants;
import com.github.chizoba.popularmovies.R;
import com.github.chizoba.popularmovies.adapters.ViewPagerAdapter;
import com.github.chizoba.popularmovies.data.MoviesContract;
import com.github.chizoba.popularmovies.fragments.OverviewFragment;
import com.github.chizoba.popularmovies.fragments.ReviewFragment;
import com.github.chizoba.popularmovies.fragments.TrailerFragment;
import com.github.chizoba.popularmovies.model.Movie;
import com.github.chizoba.popularmovies.model.Review;
import com.github.chizoba.popularmovies.model.Trailer;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity
        implements ReviewFragment.OnListFragmentInteractionListener,
        TrailerFragment.OnListFragmentInteractionListener {

    Movie movie;
    ViewPager viewPager;
    //    ActivityDetailBinding mBinding;
    FloatingActionButton fab;
//    public static final String = /;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
        setContentView(R.layout.activity_detail);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPreferences.edit();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        if (getIntent().hasExtra("MOVIE")) {
            movie = getIntent().getParcelableExtra("MOVIE");

            ImageView moviePoster = (ImageView) findViewById(R.id.poster_image);
            String url = Constants.POSTER_BASE_URL + Constants.POSTER_SIZE + movie.moviePoster;
            Picasso.with(this).load(url).into(moviePoster);

//            ViewCompat.setTransitionName(findViewById(R.id.appbar), movie.originalTitle);

            collapsingToolbarLayout.setTitle(movie.originalTitle);
        }


        fab = (FloatingActionButton) findViewById(R.id.fab);

        if(sharedPreferences.getBoolean(movie.id, false)){
            fab.setImageDrawable(ContextCompat.getDrawable(DetailActivity.this, R.drawable.ic_favorite_white_24px));
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!sharedPreferences.getBoolean(movie.id, false)) {
                    //true
                    fab.setImageDrawable(ContextCompat.getDrawable(DetailActivity.this, R.drawable.ic_favorite_white_24px));
                    editor.putBoolean(movie.id, true);
                    editor.apply();

                    // Insert new movie data via a ContentResolver
                    // Create new empty ContentValues object
                    ContentValues contentValues = new ContentValues();
                    // Put the movie details into the
                    contentValues.put(MoviesContract.MoviesEntry.COLUMN_MOVIE_POSTER, movie.moviePoster);
                    contentValues.put(MoviesContract.MoviesEntry.COLUMN_ORIGINAL_TITLE, movie.originalTitle);
                    contentValues.put(MoviesContract.MoviesEntry.COLUMN_PLOT_SYNOPSIS, movie.plotSynopsis);
                    contentValues.put(MoviesContract.MoviesEntry.COLUMN_RELEASE_DATE, movie.releaseDate);
                    contentValues.put(MoviesContract.MoviesEntry.COLUMN_USER_RATING, movie.userRating);

                    // Insert the content values via a ContentResolver
                    Uri uri = getContentResolver().insert(MoviesContract.MoviesEntry.CONTENT_URI, contentValues);

                    // Display the URI that's returned with a Toast
                    // [Hint] Don't forget to call finish() to return to MainActivity after this insert is complete
                    if(uri != null) {
                        Toast.makeText(getBaseContext(), "Added to Favorites", Toast.LENGTH_LONG).show();
                    }
                } else {
                    //false
                    fab.setImageDrawable(ContextCompat.getDrawable(DetailActivity.this, R.drawable.ic_favorite_border_white_24px));
                    editor.putBoolean(movie.id, false);
                    editor.apply();
                }
            }
        });

        initViewPagerTabs();


    }

    private void initViewPagerTabs() {
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText("Tab 1"));
        tabs.addTab(tabs.newTab().setText("Tab 2"));
        tabs.addTab(tabs.newTab().setText("Tab 3"));
        // Set Tabs inside Toolbar
        tabs.setupWithViewPager(viewPager);
    }

    //     Add Fragments to Tabs
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new OverviewFragment().newInstance(movie), "Overview");
        adapter.addFragment(new TrailerFragment().newInstance(1, movie.id), "Trailers");
        adapter.addFragment(new ReviewFragment().newInstance(1, movie.id), "Reviews");
        viewPager.setAdapter(adapter);
    }


    @Override
    public void onListFragmentInteraction(Trailer item) {
//        String youtubeUrl = "https://www.youtube.com/watch?v=" + item.key;
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://" + item.key));
        startActivity(intent);
    }

    @Override
    public void onListFragmentInteraction(Review item) {

    }
}
