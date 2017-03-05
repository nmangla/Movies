package com.nmangla.movies.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nmangla.movies.R;
import com.nmangla.movies.models.Genre;

import java.util.List;

// Adapter for recycler view to show list of genres
public class GenreAdapter extends RecyclerView.Adapter<GenreAdapter.GenreViewHolder> {
    private final Context mContext;
    private final List<Genre> mGenres;

    public GenreAdapter(Context context, List<Genre> genres) {
        mContext = context;
        mGenres = genres;
    }

    @Override
    public GenreViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.list_item_genre, parent, false);

        return new GenreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GenreViewHolder holder, int position) {
        Genre genre = mGenres.get(position);

        // Display genre name
        holder.mNameTextView.setText((position + 1) + ". " + genre.getName());
    }

    @Override
    public int getItemCount() {
        if (mGenres == null) {
            return 0;
        }

        return mGenres.size();
    }

    public static class GenreViewHolder extends RecyclerView.ViewHolder {
        private final TextView mNameTextView;

        public GenreViewHolder(View itemView) {
            super(itemView);
            mNameTextView = (TextView) itemView.findViewById(R.id.name);
        }
    }
}
