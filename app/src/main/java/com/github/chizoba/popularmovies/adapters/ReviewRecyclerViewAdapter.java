package com.github.chizoba.popularmovies.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.chizoba.popularmovies.R;
import com.github.chizoba.popularmovies.fragments.ReviewFragment.OnListFragmentInteractionListener;
import com.github.chizoba.popularmovies.model.Review;

import java.util.ArrayList;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Review} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class ReviewRecyclerViewAdapter extends RecyclerView.Adapter<ReviewRecyclerViewAdapter.ViewHolder> {

    private final ArrayList<Review> mValues;
    private final OnListFragmentInteractionListener mListener;

    public ReviewRecyclerViewAdapter(ArrayList<Review> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_review, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mAuthorTextView.setText(mValues.get(position).author);
        holder.mContentTextView.setText(mValues.get(position).content);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
//                     Notify the active callbacks interface (the activity, if the
//                     fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mAuthorTextView;
        public final TextView mContentTextView;
        public Review mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mAuthorTextView = (TextView) view.findViewById(R.id.author);
            mContentTextView = (TextView) view.findViewById(R.id.content);
        }

    }
}
