package com.example.plantarium.PlaceFragments;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plantarium.Models.DBModels.RoomModels.RoomWatering;
import com.example.plantarium.Models.DBModels.WateringModel;
import com.example.plantarium.Models.Plant;
import com.example.plantarium.Models.User;
import com.example.plantarium.Models.Watering;
import com.example.plantarium.MyApplication;
import com.example.plantarium.PlacesFragments.PlacesListAdapter;
import com.example.plantarium.PlacesFragments.PlacesListFragmentDirections;
import com.example.plantarium.R;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class PlantAdapter extends RecyclerView.Adapter<PlantAdapter.PlantViewHolder> {

    LiveData<List<Plant>> mData;
    private OnItemClickListener mListener;

    public PlantAdapter(LiveData<List<Plant>> data, OnItemClickListener listener) {
        mData = data;
        mListener = listener;
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    @NonNull
    @Override
    public PlantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) MyApplication.context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View view = inflater.inflate(R.layout.plant_row, parent, false);
        PlantViewHolder holder = new PlantViewHolder(view, mListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PlantViewHolder holder, int i) {
        Plant p = mData.getValue().get(i);
        holder.plantName.setText(p.getName());

        WateringModel.instance.getPlantLastWatering(p.getId(), new RoomWatering.WateringAsynchDaoListener<Watering>() {
            @Override
            public void onComplete(Watering lastWatering) {
                if (lastWatering != null) {

                    if (lastWatering.getImageUrl() != null) {
                        Picasso.get().load(lastWatering.getImageUrl()).into(holder.plantImg);
                    }

                    int nowDays = (int) (new Date()).getTime() / (1000 * 60 * 60 * 24);
                    int lastWateringDays = (int) lastWatering.getWateringDate().getTime() / (1000 * 60 * 60 * 24);

                    int days = nowDays - lastWateringDays;

                    if (days == 0) {
                        holder.lastWatering.setText("היום");
                    } else if (days == 1) {
                        holder.lastWatering.setText("אתמול");
                    } else {
                        holder.lastWatering.setText("לפני " + days + " ימים");
                    }

                } else {
                    holder.plantImg.setImageResource(R.drawable.plants_backgrounds);
                    holder.lastWatering.setText("לא הושקה");
                }
            }
        });

        holder.drop.setImageResource(R.drawable.drop);
        holder.itemView.setTag(i);
    }

    public static long getDifferenceDays(Date d1, Date d2) {
        long diff = d2.getTime() - d1.getTime();
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }

    @Override
    public int getItemCount() {
        return mData.getValue() != null ? mData.getValue().size() : 0;
    }

    public interface OnItemClickListener {
        void onClick(int position);
    }

    static class PlantViewHolder extends RecyclerView.ViewHolder {
        public OnItemClickListener listener;
        TextView plantName;
        TextView lastWatering;
        ImageView plantImg;
        ImageView drop;

        public PlantViewHolder(@NonNull View view, final OnItemClickListener _listener) {
            super(view);
            listener = _listener;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener !=  null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onClick(position);
                        }
                    }
                }
            });

            plantName = view.findViewById(R.id.row_plant_name);
            plantImg = view.findViewById(R.id.row_plant_image);
            lastWatering = view.findViewById(R.id.row_plant_last_watering);
            drop = view.findViewById(R.id.row_plant_drop);
        }
    }
}
