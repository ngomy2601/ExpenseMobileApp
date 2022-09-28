package com.example.expensemobileapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    private ArrayList _id, name, destination, trip_date, trip_assessment,trip_description;

    CustomAdapter(Context context,
                  ArrayList _id,
                  ArrayList name,
                  ArrayList destination,
                  ArrayList trip_date,
                  ArrayList trip_assessment,
                  ArrayList trip_description){
        this.context = context;
        this._id = _id;
        this.name = name;
        this.destination = destination;
        this.trip_date = trip_date;
        this.trip_assessment = trip_assessment;
        this.trip_description = trip_description;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.trip_id_txt.setText(String.valueOf(_id.get(position)));
        holder.trip_name_txt.setText(String.valueOf(name.get(position)));
        holder.trip_destination_txt.setText(String.valueOf(destination.get(position)));
        holder.trip_date_txt.setText(String.valueOf(trip_date.get(position)));
        holder.trip_assessment_txt.setText(String.valueOf(trip_assessment.get(position)));
        holder.trip_description_txt.setText(String.valueOf(trip_description.get(position)));
    }

    @Override
    public int getItemCount() {
        return _id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView trip_id_txt, trip_name_txt, trip_destination_txt, trip_date_txt, trip_assessment_txt, trip_description_txt;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            trip_id_txt = itemView.findViewById(R.id.trip_id_txt);
            trip_name_txt = itemView.findViewById(R.id.trip_name_txt);
            trip_destination_txt = itemView.findViewById(R.id.trip_destination_txt);
            trip_date_txt = itemView.findViewById(R.id.trip_date_txt);
            trip_assessment_txt = itemView.findViewById(R.id.trip_assessment_txt);
            trip_description_txt = itemView.findViewById(R.id.trip_description_txt);
        }
    }
}
