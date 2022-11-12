package com.example.booktickets;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> {
    List<Movie> movieList;
    Context context;

    public MoviesAdapter(List<Movie> movieList, Context context) {
        this.movieList = movieList;
        this.context = context;
    }

    @NonNull
    @Override
    public MoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_moive, parent, false);

        return new MoviesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesViewHolder holder, int position) {
        Movie movie = movieList.get(position);
        if(movie == null) {
            return;
        }

        holder.item_tv_movieName.setText(movie.getMovieName());
        holder.item_tv_movieKind.setText(movie.getMovieKind());
        holder.item_tv_movieTimes.setText(movie.getMovieTimes());
    }

    @Override
    public int getItemCount() {
        if(movieList != null) {
            return movieList.size();
        }
        return 0;
    }

    public class MoviesViewHolder extends RecyclerView.ViewHolder{
        TextView item_tv_movieName, item_tv_movieKind, item_tv_movieTimes;
        public MoviesViewHolder(@NonNull View itemView) {
            super(itemView);

            item_tv_movieName = itemView.findViewById(R.id.item_tv_movieName);
            item_tv_movieKind = itemView.findViewById(R.id.item_tv_movieKind);
            item_tv_movieTimes = itemView.findViewById(R.id.item_tv_movieTimes);
        }
    }

}
