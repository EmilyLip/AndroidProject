package com.example.plantarium.PlacesFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.plantarium.R;


public class NoPlacesFragment extends Fragment {

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_no_places, container, false);

        Button createPlaceBtn =  (Button) view.findViewById(R.id.create_button);
        createPlaceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController nav = Navigation.findNavController(view);
                if (nav.getCurrentDestination().getId() == R.id.noPlacesFragment) {
                    NoPlacesFragmentDirections.ActionNoPlacesToAddPlace action = NoPlacesFragmentDirections.actionNoPlacesToAddPlace(null);
                    nav.navigate(action);
                }
            }
        });
        return view;
    }
}