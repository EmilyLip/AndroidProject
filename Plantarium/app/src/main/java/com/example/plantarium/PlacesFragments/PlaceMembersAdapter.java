package com.example.plantarium.PlacesFragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plantarium.Models.User;
import com.example.plantarium.MyApplication;
import com.example.plantarium.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


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
        View view = inflater.inflate(R.layout.row_view_place_members_list_item, parent, false);
        PlaceMembersViewHolder holder = new PlaceMembersViewHolder(view);
        holder.listener = mListener;
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceMembersAdapter.PlaceMembersViewHolder holder, int position) {
        User user = mData.get(position);
        holder.userName.setText(user.getFullname());
        holder.userEmail.setText(user.getEmail());

        if(user.getImageUrl() != null && !user.getImageUrl().equals("")){
            Picasso.get().load(user.getImageUrl()).into(holder.userImage);
        } else {
            holder.userImage.setImageResource(R.drawable.user_placeholder);
        }
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
        CircleImageView userImage;
        TextView userName;
        TextView userEmail;

        public PlaceMembersViewHolder(@NonNull View itemView) {
            super(itemView);
            userImage = itemView.findViewById(R.id.user_place_members_image);
            userName = itemView.findViewById(R.id.user_place_members_name);
            userEmail = itemView.findViewById(R.id.user_place_members_email);
        }
    }
}
