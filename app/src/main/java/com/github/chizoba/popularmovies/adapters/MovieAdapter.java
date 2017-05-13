package com.github.chizoba.popularmovies.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.github.chizoba.popularmovies.Constants;
import com.github.chizoba.popularmovies.model.Movie;
import com.github.chizoba.popularmovies.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Chizoba on 4/4/2017.
 */

public class MovieAdapter extends ArrayAdapter<Movie> {

    /**
     * This is our own custom constructor (it doesn't mirror a superclass constructor).
     * The context is used to inflate the layout file, and the List is the data we want
     * to populate into the lists
     *
     * @param context The current context. Used to inflate the layout file.
     * @param objects A List of Movie objects to display in a list
     */
    public MovieAdapter(Context context, List<Movie> objects) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Movie movie = getItem(position);

        // Adapters recycle views to AdapterViews.
        // If this is a new View object we're getting, then inflate the layout.
        // If not, this view already has the layout inflated from a previous call to getView,
        // and we modify the View widgets as usual.
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.movie_item, parent, false);
        }

        ViewHolder viewHolder = new ViewHolder();
        viewHolder.imageView = (ImageView) convertView.findViewById(R.id.movie_poster);
        convertView.setTag(viewHolder);

        String url = Constants.POSTER_BASE_URL + Constants.POSTER_SIZE + movie.moviePoster;
        Picasso.with(getContext()).load(url).into(viewHolder.imageView);

        return convertView;
    }

    private static class ViewHolder {
        ImageView imageView;
    }
}
