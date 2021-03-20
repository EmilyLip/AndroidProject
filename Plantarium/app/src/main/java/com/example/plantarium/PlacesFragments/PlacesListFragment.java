package com.example.plantarium.PlacesFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.plantarium.Models.Place;
import com.example.plantarium.MyApplication;
import com.example.plantarium.R;

import java.util.List;

public class PlacesListFragment extends Fragment {
    View view;
    RecyclerView placesList;
    RecyclerView.LayoutManager layoutManager;
    private PlacesListViewModel viewModel;
    PlacesListAdapter adapter;

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
               if(viewModel.getUsersPlaceList().getValue().size() == 0 ){
                   Navigation.findNavController(view).navigate(R.id.action_placesList_to_noPlaces);
               }
            }
        });

        placesList = view.findViewById(R.id.places_list_rv);
        placesList.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(MyApplication.context);
        placesList.setLayoutManager(layoutManager);
        adapter = new PlacesListAdapter( viewModel.getUsersPlaceList().getValue());
        placesList.setAdapter(adapter);

        adapter.setOnItemClickListener(new PlacesListAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Log.d("TAG","row was clicked " + viewModel.getUsersPlaceList().getValue().get(position).getName());
                // TODO: Zohar you can navigate to place plants here
            }
        });

        return view;
    }
}