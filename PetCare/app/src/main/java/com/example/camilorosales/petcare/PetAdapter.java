package com.example.camilorosales.petcare;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PetAdapter extends RecyclerView.Adapter {

    private ArrayList<Pet> mDataset;
    private static RecyclerViewOnItemClickListener mRecyclerViewOnItemClickListener;

    public static class PetViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public View mView;
        public PetViewHolder(View v){
            super(v);
            mView = v;
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mRecyclerViewOnItemClickListener.onClick(v, getAdapterPosition());
        }
    }

    public PetAdapter(ArrayList<Pet> dataset, RecyclerViewOnItemClickListener recyclerViewOnItemClickListener){
        mDataset = dataset;
        mRecyclerViewOnItemClickListener = recyclerViewOnItemClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View petView = (View) LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup, false);
        PetViewHolder vh = new PetViewHolder(petView);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        PetViewHolder pvh = (PetViewHolder) holder;
        View view = pvh.mView;
        TextView petName = view.findViewById(R.id.pet_name);
        petName.setText(mDataset.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
