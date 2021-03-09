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
        Log.d(TAG, place.getName());
        return view;
    }
}