package com.example.booktickets;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ChooseTimeActivity extends AppCompatActivity {
    TextView choosetime_movieName;
    RecyclerView choosetime_recycleview;
    ImageButton chosetime_btnback;

    String movieID;

    TimeAdapter timeAdapter;
    List<TimeStart> timeStarts;

    FirebaseFirestore fdb;
    CollectionReference coll_tickets, coll_movies, coll_rooms;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_time);

        Bundle bundle = getIntent().getBundleExtra("movieData");
        if(bundle != null) {
            movieID = bundle.getString("movieID");
        }


        initID();
        timeStarts = new ArrayList<>();
        timeAdapter = new TimeAdapter(timeStarts, this);

        choosetime_recycleview.setLayoutManager(new LinearLayoutManager(this));
        choosetime_recycleview.setAdapter(timeAdapter);

        fdb = FirebaseFirestore.getInstance();
        coll_tickets = fdb.collection("tickets");
        coll_movies = fdb.collection("movies");
        coll_rooms = fdb.collection("rooms");


        coll_movies.document(movieID).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()) {
                            DocumentSnapshot doc = task.getResult();
                            Movie movie = doc.toObject(Movie.class);
                            if(movie != null) {
                                choosetime_movieName.setText(movie.getMovieName());
                            }
                        }
                    }
                });

        coll_rooms.whereEqualTo("movieID", movieID)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            for(QueryDocumentSnapshot documentSnapshot: task.getResult()) {
                                timeStarts.add(new TimeStart(documentSnapshot.getString("timestart"), documentSnapshot.getString("room")));
                            }
                            timeAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(ChooseTimeActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        chosetime_btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void initID() {
        choosetime_movieName = findViewById(R.id.choosetime_movieName);
        choosetime_recycleview = findViewById(R.id.choosetime_recycleview);
        chosetime_btnback = findViewById(R.id.chosetime_btnback);
    }


}