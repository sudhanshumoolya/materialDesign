package com.example.materialdesign;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class myAdapater extends RecyclerView.Adapter<myAdapater.myViewHolder> { ;

    ArrayList<item> itemArrayList =new ArrayList<>();
    Context context;

    public myAdapater( Context context,ArrayList<item> itemArrayList) {
        this.itemArrayList = itemArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item,parent,false);

        return new myViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        item current = itemArrayList.get(position);

        holder.titleTV.setText(current.getTitle());
        holder.msgTV.setText(current.getMsg());


    }

    @Override
    public int getItemCount() {
        return itemArrayList.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        TextView titleTV, msgTV;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTV=itemView.findViewById(R.id.titleTextView);
            msgTV=itemView.findViewById(R.id.msgTextView);

        }
    }
}
