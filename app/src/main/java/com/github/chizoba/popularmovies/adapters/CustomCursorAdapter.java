package com.github.chizoba.popularmovies.adapters;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.github.chizoba.popularmovies.Constants;
import com.github.chizoba.popularmovies.R;
import com.github.chizoba.popularmovies.activities.DetailActivity;
import com.github.chizoba.popularmovies.data.MoviesContract;
import com.github.chizoba.popularmovies.model.Movie;
import com.squareup.picasso.Picasso;

/**
 * Created by Chizoba on 5/13/2017.
 */

public class CustomCursorAdapter extends RecyclerView.Adapter<CustomCursorAdapter.MovieViewHolder> {

    // Class variables for the Cursor that holds task data and the Context
    private Cursor mCursor;
    private Context mContext;

    /**
     * Constructor for the CustomCursorAdapter that initializes the Context.
     *
     * @param mContext the current Context
     */
    public CustomCursorAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public CustomCursorAdapter.MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the movie_item layout to a view
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.movie_item, parent, false);

        return new MovieViewHolder(view);
    }

    /**
     * Called by the RecyclerView to display data at a specified position in the Cursor.
     *
     * @param holder   The ViewHolder to bind Cursor data to
     * @param position The position of the data in the Cursor
     */
    @Override
    public void onBindViewHolder(CustomCursorAdapter.MovieViewHolder holder, int position) {
        int moviePosterIndex = mCursor.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_MOVIE_POSTER);

        mCursor.moveToPosition(position); // get to the right location in the cursor

        // Determine the values of the wanted data
        String moviePosterUrl = mCursor.getString(moviePosterIndex);

        //Set values
        String url = Constants.POSTER_BASE_URL + Constants.POSTER_SIZE + moviePosterUrl;
        Picasso.with(mContext).load(url).into(holder.imageView);

    }


    @Override
    public int getItemCount() {
        if (mCursor == null) {
            return 0;
        }
        return mCursor.getCount();
    }

    /**
     * When data changes and a re-query occurs, this function swaps the old Cursor
     * with a newly updated Cursor (Cursor c) that is passed in.
     */
    public Cursor swapCursor(Cursor c) {
        // check if this cursor is the same as the previous cursor (mCursor)
        if (mCursor == c) {
            return null; // bc nothing has changed
        }
        Cursor temp = mCursor;
        this.mCursor = c; // new cursor value assigned

        //check if this is a valid cursor, then update the cursor
        if (c != null) {
            this.notifyDataSetChanged();
        }
        return temp;
    }

    // Inner class for creating ViewHolders
    class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;


        /**
         * Constructor for the TaskViewHolders.
         *
         * @param itemView The view inflated in onCreateViewHolder
         */
        public MovieViewHolder(View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.movie_poster);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            mCursor.moveToPosition(clickedPosition);
            Intent intent = new Intent(mContext, DetailActivity.class);

            String moviePoster = mCursor.getString(mCursor.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_MOVIE_POSTER));
            String originalTitle = mCursor.getString(mCursor.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_ORIGINAL_TITLE));
            String plotSynopsis = mCursor.getString(mCursor.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_PLOT_SYNOPSIS));
            String releaseDate = mCursor.getString(mCursor.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_RELEASE_DATE));
            String userRating = mCursor.getString(mCursor.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_USER_RATING));
            String movieId = mCursor.getString(mCursor.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_MOVIE_ID));

            Movie movie = new Movie(moviePoster, originalTitle, plotSynopsis, releaseDate, userRating, movieId);

            intent.putExtra("MOVIE", movie);
            mContext.startActivity(intent);
        }
    }
}
