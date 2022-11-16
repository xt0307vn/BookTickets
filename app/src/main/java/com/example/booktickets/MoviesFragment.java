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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MoviesFragment extends Fragment {
    List<Movie> movieList;
    MoviesAdapter moviesAdapter;
    RecyclerView frag_moviesRecycleview;

    FirebaseFirestore fdb;
    CollectionReference collec_movies;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movies, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fdb = FirebaseFirestore.getInstance();
        collec_movies = fdb.collection("movies");

        movieList = new ArrayList<>();
        frag_moviesRecycleview = view.findViewById(R.id.frag_moviesRecycleview);
        moviesAdapter = new MoviesAdapter(movieList, getContext());

        frag_moviesRecycleview.setLayoutManager(new LinearLayoutManager(getContext()));
        frag_moviesRecycleview.setAdapter(moviesAdapter);

        collec_movies.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            for(QueryDocumentSnapshot documentSnapshot: task.getResult()) {
                                Movie movie = documentSnapshot.toObject(Movie.class);
                                movie.setMovieID(documentSnapshot.getId());
                                movieList.add(movie);
                            }
                            moviesAdapter.notifyDataSetChanged();
                        }
                    }
                });



    }
}