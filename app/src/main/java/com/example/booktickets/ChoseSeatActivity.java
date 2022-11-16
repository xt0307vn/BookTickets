package com.example.booktickets;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ChoseSeatActivity extends AppCompatActivity {

    List<Seat> seatList;
    RecyclerView chose_seat_seat;
    SeatAdapter seatAdapter;
    int room = 2;
    Room rooms = new Room();
    List<String> seat;
    List<String> choseSeat;
    public TextView chose_seat_countseat, chose_seat_seatbook, chose_seat_amount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chose_seat);

        initID();
        seatList = new ArrayList<>();
        chose_seat_seat = findViewById(R.id.chose_seat_seat);
        seatAdapter = new SeatAdapter(seatList, this);
        choseSeat = new ArrayList<>();

        if(room == 1) {
            chose_seat_seat.setLayoutManager(new GridLayoutManager(this, 5));
            seat = rooms.room1;
        } else {
            chose_seat_seat.setLayoutManager(new GridLayoutManager(this, 4));
            seat = rooms.room2;
        }

        chose_seat_seat.setAdapter(seatAdapter);
        for (String str: seat) {

            seatList.add(new Seat(str, 0));
        }



    }

    public void initID() {
        chose_seat_countseat = findViewById(R.id.chose_seat_countseat);
        chose_seat_seatbook = findViewById(R.id.chose_seat_seatbook);
        chose_seat_amount = findViewById(R.id.chose_seat_amount);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(!choseSeat.isEmpty()) {
                    chose_seat_seatbook.setText(choseSeat.toString());
        }


    }
}