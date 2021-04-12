package com.example.plantarium.PlaceFragments;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plantarium.Models.DBModels.UserModel;
import com.example.plantarium.Models.User;
import com.example.plantarium.Models.Watering;
import com.example.plantarium.R;
import com.squareup.picasso.Picasso;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class WateringAdapter extends RecyclerView.Adapter<WateringAdapter.WateringViewHolder> {

    LayoutInflater inflater;
    LiveData<List<Watering>> mData;

    public WateringAdapter(LiveData<List<Watering>> data, LayoutInflater inflater) {
        mData = data;
        this.inflater = inflater;
    }

    @NonNull
    @Override
    public WateringViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_watering, parent, false);
        WateringViewHolder holder = new WateringViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull WateringViewHolder holder, int i) {
        Watering watering = mData.getValue().get(i);
        UserModel.instance.getUserByID(watering.getUserId(), new UserModel.GetUsertListener() {
            @Override
            public void onComplete(User user) {
                holder.username.setText("(" + user.getFullname() + ")");
                holder.username.setTextColor(Color.parseColor(getColorByUsername(user.getFullname())));
            }
        });

        // Date and time to locale strings
        Date wateringDate = watering.getWateringDate();
        Locale loc = new Locale("he", "IL");
        String date = new SimpleDateFormat("dd/MM/yyyy").format(wateringDate);
        String day = new SimpleDateFormat("EEEE", loc).format(wateringDate);
        holder.date.setText(String.format("%s, %s", day, date));

        String time = new SimpleDateFormat("HH:mm", loc).format(wateringDate);
        holder.time.setText(String.format(", בשעה %s", time));

        holder.itemView.setTag(i);

        if(watering.getImageUrl() != null){
            Picasso.get().load(watering.getImageUrl()).into(holder.waterImg);
        }
    }

    public String getColorByUsername(String name) {
        int n = name.length() > 1 ?
                (int)name.toLowerCase().charAt(0) + (int)name.toLowerCase().charAt(1) :
                name.length() == 1 ?
                        (int)name.toLowerCase().charAt(0) :
                        0;

        String[] colors = {"#008290", "#2A9D8F", "#E1BD64", "#F4A261", "#E76F51", "#B35BD2", "#87D15A"};
        return colors[n%colors.length];
    }

    @Override
    public int getItemCount() {
        return mData.getValue() != null ? mData.getValue().size() : 0;
    }

    static class WateringViewHolder extends RecyclerView.ViewHolder {
        TextView date;
        TextView time;
        TextView username;
        ImageView waterImg;

        public WateringViewHolder(@NonNull View view) {
            super(view);

            date = view.findViewById(R.id.row_watering_date);
            time = view.findViewById(R.id.row_watering_time);
            username = view.findViewById(R.id.row_watering_username);
            waterImg = view.findViewById(R.id.row_watering_image);
        }
    }
}
