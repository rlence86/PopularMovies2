package com.ramonlence.popularmovies.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ramonlence.popularmovies.R;
import com.ramonlence.popularmovies.entities.Review;
import com.ramonlence.popularmovies.entities.Trailer;

import java.util.ArrayList;

/**
 * Created by ramon on 26/3/17.
 */

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewsViewHolder> {
    private int mNumberOfItems;
    private ArrayList<Review> mReviewsData;

    public void setmReviewsData(ArrayList<Review> reviewsData){
        mReviewsData = reviewsData;
        mNumberOfItems = reviewsData.size();
        notifyDataSetChanged();
    }

    @Override
    public ReviewsAdapter.ReviewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.review_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new ReviewsAdapter.ReviewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewsAdapter.ReviewsViewHolder holder, int position) {
        Review reviewInPosition = mReviewsData.get(position);
        holder.mViewReviewAuthor.setText(reviewInPosition.getAuthor());
        holder.mViewReviewContent.setText(reviewInPosition.getContent());
    }

    @Override
    public int getItemCount() {
        return mNumberOfItems;
    }

    public class ReviewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mViewReviewAuthor;
        private TextView mViewReviewContent;
        public ReviewsViewHolder(View view){
            super(view);
            mViewReviewAuthor = (TextView) view.findViewById(R.id.review_author);
            mViewReviewContent = (TextView) view.findViewById(R.id.review_content);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v){
        }
    }
}
