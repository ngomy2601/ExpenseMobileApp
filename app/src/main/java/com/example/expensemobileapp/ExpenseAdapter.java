package com.example.expensemobileapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.MyViewHolder> {

    private Context context;
    Activity activity;
    private ArrayList _id, type, amount, date;
    int position;

    public ExpenseAdapter(Context context,
                          Activity activity,
                          ArrayList _id,
                          ArrayList type,
                          ArrayList amount,
                          ArrayList date) {
        this.context = context;
        this.activity = activity;
        this._id = _id;
        this.type = type;
        this.amount = amount;
        this.date = date;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.expense_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        this.position = position;
        holder.expense_id_txt.setText(String.valueOf(_id.get(position)));
        holder.type_txt.setText(String.valueOf(type.get(position)));
        holder.amount_txt.setText(String.valueOf(amount.get(position)));
        holder.date_txt.setText(String.valueOf(date.get(position)));

    }

    @Override
    public int getItemCount() {
        return _id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView expense_id_txt, type_txt, date_txt, amount_txt;
        LinearLayout mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            expense_id_txt = itemView.findViewById(R.id.expense_id_txt);
            type_txt = itemView.findViewById(R.id.type_txt);
            date_txt = itemView.findViewById(R.id.date_txt);
            amount_txt = itemView.findViewById(R.id.amount_txt);
            mainLayout = itemView.findViewById(R.id.mainLayout2);
        }
    }
}
