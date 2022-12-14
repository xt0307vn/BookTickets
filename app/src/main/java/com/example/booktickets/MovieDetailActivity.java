package com.example.booktickets;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MovieDetailActivity extends AppCompatActivity {
    FirebaseFirestore fdb;
    CollectionReference collec_movies;
    TextView movie_detail_movieName;
    ImageView movie_detail_movieImage;
    Button movie_detail_seatBook;
    ImageButton chose_seat_btnback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        fdb = FirebaseFirestore.getInstance();
        collec_movies = fdb.collection("movies");

        initID();

        Bundle bundle = getIntent().getBundleExtra("movieData");
        if(bundle != null) {
            collec_movies.document(bundle.getString("movieID")).get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if(task.isSuccessful()) {
                                DocumentSnapshot doc = task.getResult();
                                Movie movie = doc.toObject(Movie.class);
                                if(movie != null) {
                                    movie_detail_movieName.setText(movie.getMovieName());
                                    Glide.with(getApplicationContext()).load(movie.getMovieUrlImage()).into(movie_detail_movieImage);
                                }
                            }
                        }
                    });

            movie_detail_seatBook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    gotoChoseTime(bundle.getString("movieID"));
                }
            });
        } else {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }


        chose_seat_btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


    }

    public void initID() {
        movie_detail_movieName = findViewById(R.id.movie_detail_movieName);
        movie_detail_movieImage = findViewById(R.id.movie_detail_movieImage);
        movie_detail_seatBook = findViewById(R.id.movie_detail_seatBook);
        chose_seat_btnback = findViewById(R.id.chose_seat_btnback);
    }

    public void gotoChoseTime(String movieID) {
        Intent intent = new Intent(this, ChooseTimeActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("movieID",movieID);
        intent.putExtra("movieData", bundle);
        startActivity(intent);
    }

}