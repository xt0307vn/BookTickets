package com.example.booktickets;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SeatAdapter extends RecyclerView.Adapter<SeatAdapter.SeatViewHolder> {
    List<Seat> seatList;
    ChoseSeatActivity choseSeatActivity;

    public SeatAdapter(List<Seat> seatList, ChoseSeatActivity choseSeatActivity) {
        this.seatList = seatList;
        this.choseSeatActivity = choseSeatActivity;
    }

    @NonNull
    @Override
    public SeatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(choseSeatActivity).inflate(R.layout.item_seat, parent, false);

        return new SeatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SeatViewHolder holder, int position) {
        Seat seat = seatList.get(position);
        if(seat == null) {
            return;
        }
        holder.item_seat_seat_txt.setText(seat.getSeat_txt());
        if(seat.getSeat_position() == 1) {
            holder.item_seat_seat_position.setEnabled(false);
            holder.item_seat_seat_position.setButtonDrawable(R.drawable.seat_red);
        }

        holder.item_seat_seat_position.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b == true) {
                    choseSeatActivity.choseSeat.add(seat.getSeat_txt());
                    choseSeatActivity.chose_seat_seatbook.setText(choseSeatActivity.choseSeat.toString());
                    choseSeatActivity.chose_seat_countseat.setText(choseSeatActivity.choseSeat.size() + "");
                    choseSeatActivity.chose_seat_amount.setText(choseSeatActivity.choseSeat.size()*55000 + "");
                } else {
                    for(int index = 0; index <= choseSeatActivity.choseSeat.size(); index++) {
                        if(choseSeatActivity.choseSeat.get(index).equalsIgnoreCase(seat.getSeat_txt())) {
                            choseSeatActivity.choseSeat.remove(index);
                            choseSeatActivity.chose_seat_seatbook.setText(choseSeatActivity.choseSeat.toString());
                            choseSeatActivity.chose_seat_countseat.setText(choseSeatActivity.choseSeat.size() + "");
                            choseSeatActivity.chose_seat_amount.setText(choseSeatActivity.choseSeat.size()*55000 + "");
                        }
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if(seatList != null) {
            return seatList.size();
        }
        return 0;
    }

    public class SeatViewHolder extends RecyclerView.ViewHolder {
        TextView item_seat_seat_txt , chose_seat_seatbook, chose_seat_countseat  ;
        CheckBox item_seat_seat_position;
        public SeatViewHolder(@NonNull View itemView) {
            super(itemView);
            item_seat_seat_txt = itemView.findViewById(R.id.item_seat_seat_txt);
            item_seat_seat_position = itemView.findViewById(R.id.item_seat_seat_position);

        }
    }
}
