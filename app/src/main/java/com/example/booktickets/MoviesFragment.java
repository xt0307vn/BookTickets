package com.example.booktickets;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class MoviesFragment extends Fragment {
    List<Movie> movieList;
    MoviesAdapter moviesAdapter;
    RecyclerView frag_moviesRecycleview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movies, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        movieList = new ArrayList<>();
        frag_moviesRecycleview = view.findViewById(R.id.frag_moviesRecycleview);
        moviesAdapter = new MoviesAdapter(movieList, getContext());

        frag_moviesRecycleview.setLayoutManager(new LinearLayoutManager(getContext()));
        frag_moviesRecycleview.setAdapter(moviesAdapter);

        movieList.add(new Movie("aaaaaaa", "aaaaaaa", "aaaa", "aaaaaa"));
        movieList.add(new Movie("aaaaaaa", "aaaaaaa", "aaaa", "aaaaaa"));
        movieList.add(new Movie("aaaaaaa", "aaaaaaa", "aaaa", "aaaaaa"));
        movieList.add(new Movie("aaaaaaa", "aaaaaaa", "aaaa", "aaaaaa"));
        movieList.add(new Movie("aaaaaaa", "aaaaaaa", "aaaa", "aaaaaa"));

    }
}