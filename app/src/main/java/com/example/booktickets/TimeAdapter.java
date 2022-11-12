package com.example.booktickets;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TimeAdapter extends RecyclerView.Adapter<TimeAdapter.TimeViewHolder> {
    List<TimeStart> list;
    Context context;

    public TimeAdapter(List<TimeStart> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public TimeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_timestart, parent, false);
        return new TimeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TimeViewHolder holder, int position) {
        TimeStart t = list.get(position);
        if(t == null) {
            return;
        }

        holder.item_time.setText(t.getTime());
    }

    @Override
    public int getItemCount() {
        if(list != null) {
            return list.size();
        }
        return 0;
    }

    public class TimeViewHolder extends RecyclerView.ViewHolder {
        TextView item_time;
        public TimeViewHolder(@NonNull View itemView) {
            super(itemView);
            item_time = itemView.findViewById(R.id.idaaaa);
        }
    }
}
