package com.example.plantarium.PlaceFragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plantarium.Models.Plant;
import com.example.plantarium.R;

import java.util.List;

public class PlantAdapter extends RecyclerView.Adapter<PlantAdapter.PlantViewHolder> {

    LayoutInflater inflater;
    List<Plant> mData;

    public PlantAdapter(List<Plant> data, LayoutInflater inflater) {
        mData = data;
        this.inflater = inflater;
    }

    @NonNull
    @Override
    public PlantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.plant_row, parent, false);
        PlantViewHolder holder = new PlantViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PlantViewHolder holder, int i) {
        Plant p = mData.get(i);
        holder.plantName.setText(p.getName());
        holder.lastWatering.setText("יום שלישי");
        //holder.plantImg.setText(str);
        holder.itemView.setTag(i);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    static class PlantViewHolder extends RecyclerView.ViewHolder {
        TextView plantName;
        TextView lastWatering;
        ImageView plantImg;

        public PlantViewHolder(@NonNull View view) {
            super(view);

            plantName = view.findViewById(R.id.listrow_plant_name);
            plantImg = view.findViewById(R.id.listrow_image);
            lastWatering = view.findViewById(R.id.listrow_last_watering);
        }
    }


}