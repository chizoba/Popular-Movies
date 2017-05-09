package com.github.chizoba.popularmovies;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.chizoba.popularmovies.databinding.ActivityDetailBinding;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    ImageView moviePoster;
//    TextView toolbarTitle, userRating, releaseDate, plotSynopsis;

    ActivityDetailBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
//        setContentView(R.layout.activity_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setTitle("dddd");
        initializeViews();

//        Intent receivedIntent = getIntent();
//        Movie movie2 = getIntent().getExtras().getParcelable("MOVIE");

        if (getIntent().hasExtra("MOVIE")) {
            Movie movie = getIntent().getParcelableExtra("MOVIE");

            String url = Constants.POSTER_BASE_URL+Constants.POSTER_SIZE+movie.moviePoster;
            Picasso.with(this).load(url).into(moviePoster);

//            mBinding.toolbar.setTitle(movie.originalTitle);
//            toolbar.setTitle(movie.originalTitle);
//            toolbar.setTitle(receivedIntent.getStringExtra(Constants.ORIGINAL_TITLE));

            mBinding.toolbarTitle.setText(movie.originalTitle);
            mBinding.plotSynopsis.setText(movie.plotSynopsis);
            mBinding.userRating.setText(movie.userRating);
            mBinding.releaseDate.setText(movie.releaseDate);

//            toolbarTitle.setText(movie.originalTitle);
//            plotSynopsis.setText(movie.plotSynopsis);
//            userRating.setText(movie.userRating);
//            releaseDate.setText(movie.releaseDate);
        }
    }

    private void initializeViews() {
        moviePoster = (ImageView) findViewById(R.id.poster_image);
//        userRating = (TextView) findViewById(R.id.user_rating);
//        releaseDate = (TextView) findViewById(R.id.release_date);
//        plotSynopsis = (TextView) findViewById(R.id.plot_synopsis);
//        toolbarTitle = (TextView) findViewById(R.id.toolbar_title);
    }
}
