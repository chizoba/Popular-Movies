package com.github.chizoba.popularmovies.fragments;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.chizoba.popularmovies.R;
import com.github.chizoba.popularmovies.adapters.TrailerRecyclerViewAdapter;
import com.github.chizoba.popularmovies.model.Trailer;
import com.github.chizoba.popularmovies.utilities.JsonUtils;
import com.github.chizoba.popularmovies.utilities.NetworkUtils;

import java.net.URL;
import java.util.ArrayList;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class TrailerFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final String ARG_MOVIE_ID = "movieId";

    private int mColumnCount = 1;
    private String mMovieId;

    private OnListFragmentInteractionListener mListener;

    private RecyclerView recyclerView;

    private ProgressBar mLoadingIndicator;

    private TextView mErrorMessageDisplay;

    ArrayList<Trailer> trailerList;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TrailerFragment() {
    }

    @SuppressWarnings("unused")
    public static TrailerFragment newInstance(int columnCount, String id) {
        TrailerFragment fragment = new TrailerFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        args.putString(ARG_MOVIE_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
            mMovieId = getArguments().getString(ARG_MOVIE_ID);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trailer_list, container, false);

        mLoadingIndicator = (ProgressBar) view.findViewById(R.id.progress_bar);
        mErrorMessageDisplay = (TextView) view.findViewById(R.id.tv_error_message_display);
        recyclerView = (RecyclerView) view.findViewById(R.id.trailer_list);

        if (mColumnCount <= 1) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), mColumnCount));
        }

        makeMovieTrailerQuery(mMovieId);

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity_detail and potentially other fragments contained in that
     * activity_detail.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(Trailer item);
    }

    private void makeMovieTrailerQuery(String id) {
        recyclerView.setVisibility(View.INVISIBLE);
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mLoadingIndicator.setVisibility(View.VISIBLE);
        URL searchUrl = NetworkUtils.buildUrl(id, "videos");
        new TrailerQueryTask().execute(searchUrl);
    }

    /**
     * This method will make the View for the JSON data visible and
     * hide the error message.
     */
    public void showJsonDataView() {
        // First, make sure the error is invisible
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        // Then, make sure the JSON data is visible
        recyclerView.setVisibility(View.VISIBLE);
    }

    /**
     * This method will make the error message visible and hide the JSON
     * View.
     */
    public void showErrorMessage() {
        // First, hide the currently visible data
        recyclerView.setVisibility(View.INVISIBLE);
        // Then, show the error
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    private class TrailerQueryTask extends AsyncTask<URL, Void, ArrayList<Trailer>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<Trailer> doInBackground(URL... urls) {
            URL searchUrl = urls[0];
            String searchResults = null;
            try {
                searchResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);

                trailerList = JsonUtils
                        .getTrailersFromJson(getContext(), searchResults);

                return trailerList;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(final ArrayList<Trailer> searchResults) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);

            if (searchResults != null) {

                showJsonDataView();

                recyclerView.setAdapter(new TrailerRecyclerViewAdapter(searchResults, mListener));

            } else {
                showErrorMessage();
            }
        }
    }
}

