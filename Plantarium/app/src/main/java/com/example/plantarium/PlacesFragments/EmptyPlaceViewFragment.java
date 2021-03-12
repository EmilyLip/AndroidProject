package com.example.plantarium.PlacesFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.plantarium.HomePageFragments.LoginPageFragmentDirections;
import com.example.plantarium.PlacesFragments.EmptyPlaceViewFragmentArgs;
import com.example.plantarium.Models.Place;
import com.example.plantarium.R;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import io.perfmark.Tag;

public class EmptyPlaceViewFragment extends Fragment {

    View view;
    Place place;
    private static final String TAG  = "Empty Place";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_empty_place_view, container, false);
        place = EmptyPlaceViewFragmentArgs.fromBundle(getArguments()).getPlace();

        CircleImageView placeImage = view.findViewById(R.id.place_image);
        TextView placeName = view.findViewById(R.id.place_name);
        ProgressBar progressBar = view.findViewById(R.id.progressBarImage);

        placeName.setText(place.getName());
        placeImage.setVisibility(View.INVISIBLE);
        if (place.getImageUrl() != null){
            Picasso.get().load(place.getImageUrl()).into(placeImage);
            placeImage.setVisibility(View.VISIBLE);
        }

        Button addMembers = view.findViewById(R.id.addMemberBtn);
        addMembers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController nav = Navigation.findNavController(view);
                if (nav.getCurrentDestination().getId() == R.id.emptyPlaceView) {
                    EmptyPlaceViewFragmentDirections.ActionEmptyPlaceViewToAddMemberToPlace action = EmptyPlaceViewFragmentDirections.actionEmptyPlaceViewToAddMemberToPlace(place);
                    nav.navigate(action);
                }
            }
        });

        Log.d(TAG, place.getName());
        return view;
    }
}