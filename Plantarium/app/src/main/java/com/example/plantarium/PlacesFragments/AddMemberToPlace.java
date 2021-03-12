package com.example.plantarium.PlacesFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.plantarium.Models.Place;
import com.example.plantarium.R;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddMemberToPlace extends Fragment {
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_member_to_place, container, false);
        Place place = AddMemberToPlaceArgs.fromBundle(getArguments()).getPlace();
        CircleImageView placeImage = view.findViewById(R.id.place_image);
        TextView placeName = view.findViewById(R.id.place_name);

        placeName.setText(place.getName());
        placeImage.setVisibility(View.INVISIBLE);
        if (place.getImageUrl() != null){
            Picasso.get().load(place.getImageUrl()).into(placeImage);
            placeImage.setVisibility(View.VISIBLE);
        }

        return view;
    }
}