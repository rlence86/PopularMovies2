package com.ramonlence.popularmovies.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ramonlence.popularmovies.R;
import com.ramonlence.popularmovies.entities.Movie;
import com.ramonlence.popularmovies.entities.Trailer;
import com.ramonlence.popularmovies.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by ramon on 26/3/17.
 */

public class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.TrailersViewHolder> {

    private int mNumberOfItems;
    private ArrayList<Trailer> mTrailersData;

    public void setTrailersData(ArrayList<Trailer> trailersData){
        mTrailersData = trailersData;
        mNumberOfItems = trailersData.size();
        notifyDataSetChanged();
    }

    @Override
    public TrailersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.trailer_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new TrailersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TrailersViewHolder holder, int position) {
        Trailer trailerInPosition = mTrailersData.get(position);
        holder.mViewTrailerTitle.setText(trailerInPosition.getTitle());
    }

    @Override
    public int getItemCount() {
        return mNumberOfItems;
    }

    public class TrailersViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mViewTrailerTitle;
        public TrailersViewHolder(View view){
            super(view);
            mViewTrailerTitle = (TextView) view.findViewById(R.id.trailer_title);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v){
            int adapterPosition = getAdapterPosition();
            Trailer selectedTrailer = mTrailersData.get(adapterPosition);
            if(selectedTrailer.getSite().equalsIgnoreCase("youtube")){
                Uri youtubeUri = Uri.parse(NetworkUtils.buildYoutubeURL(selectedTrailer.getKey()).toString());
                Intent intent = new Intent(Intent.ACTION_VIEW, youtubeUri);
                if (intent.resolveActivity(v.getContext().getPackageManager()) != null) {
                    v.getContext().startActivity(intent);
                }
            }
        }
    }
}
