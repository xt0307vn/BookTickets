package com.example.booktickets;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;

public class ChooseSeatActivity extends AppCompatActivity {
    ImageButton chose_seat_btnback;
    Button chose_seat_bookseat;
    TextView chose_seat_movieName;

    List<Seat> seatList;
    RecyclerView chose_seat_seat;
    SeatAdapter seatAdapter;
    int room = 2;
    Room rooms = new Room();
    List<String> seat;
    List<String> choseSeat;
    public TextView chose_seat_countseat, chose_seat_seatbook, chose_seat_amount;

    FirebaseFirestore fdb;
    CollectionReference coll_tickets, coll_movies;

    Bundle bundle;

    Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_seat);

        fdb = FirebaseFirestore.getInstance();
        coll_movies = fdb.collection("movies");
        coll_tickets = fdb.collection("tickets");


        initID();
        seatList = new ArrayList<>();
        chose_seat_seat = findViewById(R.id.chose_seat_seat);
        seatAdapter = new SeatAdapter(seatList, this);
        choseSeat = new ArrayList<>();

        bundle = getIntent().getBundleExtra("data");

        if(bundle != null) {

            coll_movies.document(bundle.getString("movieID")).get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if(task.isSuccessful()) {
                                DocumentSnapshot doc = task.getResult();
                                Movie movie = doc.toObject(Movie.class);
                                if(movie != null) {
                                    chose_seat_movieName.setText(movie.getMovieName());
                                }
                            }
                        }
                    });

            if(bundle.getString("room").equalsIgnoreCase("1")) {
                chose_seat_seat.setLayoutManager(new GridLayoutManager(this, 5));
                seat = rooms.room1;
            }
            if(bundle.getString("room").equalsIgnoreCase("2")) {
                chose_seat_seat.setLayoutManager(new GridLayoutManager(this, 4));
                seat = rooms.room2;
            }
        }

        chose_seat_seat.setAdapter(seatAdapter);

        coll_tickets.whereEqualTo("timestart", bundle.getString("timestart"))
                .whereEqualTo("room", bundle.getString("room"))
                .whereEqualTo("movieID", bundle.getString("movieID"))
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            if(task.getResult().isEmpty()) {
                                for(String str: seat) {
                                    seatList.add(new Seat(str, 0));
                                }

                                seatAdapter.notifyDataSetChanged();
                            } else {
                                for (String str: seat) {
                                coll_tickets.whereEqualTo("timestart", bundle.getString("timestart"))
                                        .whereEqualTo("room", bundle.getString("room"))
                                        .whereEqualTo("movieID", bundle.getString("movieID"))
                                        .whereEqualTo("seat", str)
                                        .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                if(task.isSuccessful()) {
                                                    if(!task.getResult().isEmpty()) {
                                                        seatList.add(new Seat(str, 1));

                                                    } else {
                                                        seatList.add(new Seat(str, 0));

                                                    }
                                                    seatAdapter.notifyDataSetChanged();

                                                } else {

                                                    Toast.makeText(ChooseSeatActivity.this, "Error", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            }
                                seatAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Toast.makeText(ChooseSeatActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


        chose_seat_btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        chose_seat_bookseat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(choseSeat.size() == 0) {
                    Toast.makeText(ChooseSeatActivity.this, "Bạn chưa đặt chỗ ngồi!!!", Toast.LENGTH_SHORT).show();
                } else {
                    for(String c: choseSeat) {
                        Map<String, Object> add = new HashMap<>();
                        add.put("movieID", bundle.getString("movieID"));
                        add.put("room", bundle.getString("room"));
                        add.put("seat", c);
                        add.put("timestart", bundle.getString("timestart"));
                        add.put("userID", "aaa");
                        coll_tickets.add(add).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {

                            }
                        });
                    }
                    AlertDialog.Builder alert = new AlertDialog.Builder(ChooseSeatActivity.this);
                    alert.setTitle("Thông báo");
                    alert.setMessage("Đặt chỗ thành công");
                    alert.show();

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            startActivity(new Intent(ChooseSeatActivity.this, MainActivity.class));
                        }
                    }, 2000);

                }

            }
        });
    }

    public void initID() {
        chose_seat_countseat = findViewById(R.id.chose_seat_countseat);
        chose_seat_seatbook = findViewById(R.id.chose_seat_seatbook);
        chose_seat_amount = findViewById(R.id.chose_seat_amount);
        chose_seat_btnback = findViewById(R.id.chose_seat_btnback);
        chose_seat_movieName = findViewById(R.id.chose_seat_movieName);
        chose_seat_bookseat = findViewById(R.id.chose_seat_bookseat);
    }






}