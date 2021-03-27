package com.example.plantarium.PlacesFragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plantarium.Models.User;
import com.example.plantarium.MyApplication;
import com.example.plantarium.R;

import java.util.ArrayList;
import java.util.List;


public class PlaceMembersAdapter extends RecyclerView.Adapter<PlaceMembersAdapter.PlaceMembersViewHolder>{
    List<User> mData;

     private OnItemClickListener mListener;

    public PlaceMembersAdapter(List<User> data){
        mData = data;
    }

    void setOnItemClickListener(PlaceMembersAdapter.OnItemClickListener listener){
        mListener = listener;
    }

    public void updateList(List<User> itemList)
    {
        mData = new ArrayList<User>();
        this.mData.addAll(itemList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PlaceMembersAdapter.PlaceMembersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) MyApplication.context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View view = inflater.inflate(R.layout.row_view_places_list_item, parent, false);
        PlaceMembersViewHolder holder = new PlaceMembersViewHolder(view);
        holder.listener = mListener;
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceMembersAdapter.PlaceMembersViewHolder holder, int position) {
        User user = mData.get(position);
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
    class PlaceMembersViewHolder extends RecyclerView.ViewHolder{
        public OnItemClickListener listener;

        public PlaceMembersViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
