package com.example.plantarium.PlacesFragments;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plantarium.Models.Place;
import com.example.plantarium.MyApplication;
import com.example.plantarium.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PlacesListAdapter extends RecyclerView.Adapter<PlacesListAdapter.MyViewHolder> {
    List<Place> mData;

    private OnItemClickListener mListener;

    public PlacesListAdapter(List<Place> data){
        mData = data;
    }

    void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public void updateList(List<Place> itemList)
    {
        mData = new ArrayList<Place>();
        this.mData.addAll(itemList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) MyApplication.context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View view = inflater.inflate(R.layout.row_view_places_list_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        holder.listener = mListener;
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Place place = mData.get(position);
        holder.placeName.setText(place.getName());
        holder.editPlace.setImageResource(R.drawable.edit);
        holder.leavePlace.setImageResource(R.drawable.exit_place);

        if(place.getImageUrl() != null){
            Picasso.get().load(place.getImageUrl()).into(holder.placeImage);
        }

        holder.leavePlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController nav = Navigation.findNavController(v);
                if (nav.getCurrentDestination().getId() == R.id.placesListFragment) {
                    PlacesListFragmentDirections.ActionPlacesListToLeavePlaceDialog action = PlacesListFragmentDirections.actionPlacesListToLeavePlaceDialog(place);
                    nav.navigate(action);
                }
            }
        });

        holder.editPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController nav = Navigation.findNavController(v);
                if (nav.getCurrentDestination().getId() == R.id.placesListFragment) {
                   PlacesListFragmentDirections.ActionPlacesListToAddPlace action = PlacesListFragmentDirections.actionPlacesListToAddPlace(place);
                   nav.navigate(action);
                }
            }
        });
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

    class MyViewHolder extends RecyclerView.ViewHolder{
        public OnItemClickListener listener;
        CircleImageView placeImage;
        TextView placeName;
        ImageButton editPlace;
        ImageButton leavePlace;

        public MyViewHolder(@NonNull View itemView) {
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
}
