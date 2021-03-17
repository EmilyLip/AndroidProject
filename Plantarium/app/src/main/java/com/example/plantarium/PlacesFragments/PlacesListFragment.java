package com.example.plantarium.PlacesFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.plantarium.HomePageFragments.LoginPageViewModel;
import com.example.plantarium.Models.Place;
import com.example.plantarium.R;

import java.util.List;

public class PlacesListFragment extends Fragment {
    View view;
    private PlacesListViewModel viewModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=  inflater.inflate(R.layout.fragment_places_list, container, false);
        viewModel= new ViewModelProvider(this).get(PlacesListViewModel.class);

        viewModel.getUsersPlaceList().observe(getViewLifecycleOwner(), new Observer<List<Place>>() {
            @Override
            public void onChanged(List<Place> students) {
               view.findViewById(R.id.progressBarList).setVisibility(View.INVISIBLE);
            }
        });

        return view;
    }
}