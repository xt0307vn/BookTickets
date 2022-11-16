package com.example.booktickets;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TimeAdapter extends RecyclerView.Adapter<TimeAdapter.TimeViewHolder> {
    List<TimeStart> list;
    ChooseTimeActivity chooseTimeActivity;

    public TimeAdapter(List<TimeStart> list, ChooseTimeActivity chooseTimeActivity) {
        this.list = list;
        this.chooseTimeActivity = chooseTimeActivity;
    }

    @NonNull
    @Override
    public TimeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(chooseTimeActivity).inflate(R.layout.item_timestart, parent, false);
        return new TimeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TimeViewHolder holder, int position) {
        TimeStart t = list.get(position);
        if(t == null) {
            return;
        }

        holder.timestart_start.setText(t.getTime());
        holder.timestart_room.setText(t.getRoom());

        holder.timestart_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(chooseTimeActivity, ChooseSeatActivity.class);

                Bundle bundle = new Bundle();
                bundle.putString("timestart",t.getTime());
                bundle.putString("room",t.getRoom());
                bundle.putString("movieID",chooseTimeActivity.movieID);

                intent.putExtra("data", bundle);
                chooseTimeActivity.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        if(list != null) {
            return list.size();
        }
        return 0;
    }

    public class TimeViewHolder extends RecyclerView.ViewHolder {
        TextView timestart_start, timestart_room;
        Button timestart_choose;
        public TimeViewHolder(@NonNull View itemView) {
            super(itemView);
            timestart_start = itemView.findViewById(R.id.timestart_start);
            timestart_room = itemView.findViewById(R.id.timestart_room);
            timestart_choose = itemView.findViewById(R.id.timestart_choose);
        }
    }
}
