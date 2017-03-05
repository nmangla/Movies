package com.nmangla.movies.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.nmangla.movies.R;

import com.nmangla.movies.helpers.Util;
import com.nmangla.movies.models.MovieListItem;
import com.squareup.picasso.Picasso;

import java.util.List;

// Adapter for recycler view to show movie list item
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private final Context mContext;
    private List<MovieListItem> mMovies;

    // Click listener to open info about movie
    private static OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    public void setMovies(List<MovieListItem> movies) {
        mMovies = movies;
    }

    public MovieAdapter(Context context, List<MovieListItem> movies) {
        mContext = context;
        mMovies = movies;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.list_item_movie, parent, false);

        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        MovieListItem item = mMovies.get(position);

        holder.mTitleTextView.setText(item.getTitle());

        holder.mPopularityTextView.setText("Popularity: " + item.getPopularity() + "");

        // Loading poster image and putting placeholder if image takes time to load
        if(item.getPosterPath() != null && !item.getPosterPath().isEmpty()) {
            Picasso.with(mContext).load(Util.getSmallImage(item.getPosterPath())).placeholder(R.drawable.placeholder1).
                    into(holder.mImageView);
        } else {
            Picasso.with(mContext).load(R.drawable.placeholder1).into(holder.mImageView);
        }
    }

    @Override
    public int getItemCount() {
        if(mMovies == null) {
            return 0;
        }
        return mMovies.size();
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final ImageView mImageView;
        private final TextView mTitleTextView;
        private final TextView mPopularityTextView;


        public MovieViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            mImageView = (ImageView) itemView.findViewById(R.id.image);
            mTitleTextView = (TextView) itemView.findViewById(R.id.title);
            mPopularityTextView = (TextView) itemView.findViewById(R.id.popularity);
        }

        @Override
        public void onClick(View v) {
            mOnItemClickListener.onItemClick(v, getAdapterPosition());
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }
}