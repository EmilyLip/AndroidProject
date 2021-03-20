package com.example.plantarium.PlacesFragments;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plantarium.Models.Place;
import com.example.plantarium.MyApplication;
import com.example.plantarium.R;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Vector;

import de.hdodenhof.circleimageview.CircleImageView;

public class PlacesListAdapter extends RecyclerView.Adapter<PlacesListAdapter.MyViewHolder> {
    List<Place> mData;
    private OnItemClickListener mListener;

    public PlacesListAdapter( List<Place> data){
        mData = data;
    }

    void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{
        CircleImageView placeImage;
        TextView placeName;
        ImageButton editPlace;
        ImageButton leavePlace;

        public MyViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
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
            placeImage = itemView.findViewById(R.id.place_image);
            placeName = itemView.findViewById(R.id.place_name_title);
            editPlace = itemView.findViewById(R.id.edit_place);
            leavePlace = itemView.findViewById(R.id.leave_place);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) MyApplication.context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View view = inflater.inflate(R.layout.row_view_places_list_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view, mListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Place place = mData.get(position);
        Picasso.get().load(place.getImageUrl()).into(holder.placeImage);
        holder.placeName.setText(place.getName());
        holder.editPlace.setImageResource(R.drawable.edit);
        holder.leavePlace.setImageResource(R.drawable.exit_place);
    }

    @Override
    public int getItemCount() {
        if(mData != null)
        return mData.size();
        else return 0;
    }

    public interface OnItemClickListener {
        void onClick(int position);
    }
}
