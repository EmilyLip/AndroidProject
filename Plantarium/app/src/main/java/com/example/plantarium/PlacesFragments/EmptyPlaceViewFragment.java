package com.example.plantarium.PlacesFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.plantarium.PlacesFragments.EmptyPlaceViewFragmentArgs;
import com.example.plantarium.Models.Place;
import com.example.plantarium.R;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import io.perfmark.Tag;

public class EmptyPlaceViewFragment extends Fragment {

    View view;
    private static final String TAG  = "Empty Place";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_empty_place_view, container, false);

        Place place = EmptyPlaceViewFragmentArgs.fromBundle(getArguments()).getPlace();
        CircleImageView placeImage =view.findViewById(R.id.place_image);

        if (place.getImageUrl() != null){
            Picasso.get().load(place.getImageUrl()).into(placeImage);
        }
        Log.d(TAG, place.getName());
        return view;
    }
}