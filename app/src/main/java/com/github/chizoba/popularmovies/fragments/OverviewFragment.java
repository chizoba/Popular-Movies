package com.github.chizoba.popularmovies.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.chizoba.popularmovies.R;
import com.github.chizoba.popularmovies.model.Movie;


/**
 * A simple {@link Fragment} subclass.
 */
public class OverviewFragment extends Fragment {

    private static final String ARG_MOVIE = "movie";

    private Movie mMovie;

    public OverviewFragment() {
        // Required empty public constructor
    }

    @SuppressWarnings("unused")
    public static OverviewFragment newInstance(Movie movie) {
        OverviewFragment fragment = new OverviewFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_MOVIE, movie);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mMovie = getArguments().getParcelable(ARG_MOVIE);
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_overview, container, false);

        TextView plotTextView = (TextView) view.findViewById(R.id.textViewPlot);
        TextView dateTextView = (TextView) view.findViewById(R.id.textViewReleaseDate);
        TextView ratingTextView = (TextView) view.findViewById(R.id.textViewRating);

        plotTextView.setText(mMovie.plotSynopsis);
        dateTextView.setText(mMovie.releaseDate);
        ratingTextView.setText(mMovie.userRating);

        return view;
    }

}
