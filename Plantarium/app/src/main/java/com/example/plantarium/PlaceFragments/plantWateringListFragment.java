package com.example.plantarium.PlaceFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.plantarium.Models.DBModels.WateringModel;
import com.example.plantarium.Models.Plant;
import com.example.plantarium.Models.Watering;
import com.example.plantarium.PlacesFragments.PlacesListFragment;
import com.example.plantarium.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class plantWateringListFragment extends Fragment {

    RecyclerView wateringList;
    LinearLayoutManager layoutManager;
    private ProgressBar progressbarList;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_plant_watering_list, container, false);
        Plant plant = plantWateringListFragmentArgs.fromBundle(getArguments()).getPlant();

        TextView plantName = view.findViewById(R.id.wateringlist_plant_name);
        TextView empty1 = view.findViewById(R.id.watering_list_empty1);
        TextView empty2 = view.findViewById(R.id.watering_list_empty2);
        FloatingActionButton addWateringBtn = view.findViewById(R.id.fab_add_watering);
        progressbarList = view.findViewById(R.id.watering_list_progressbar);
        ImageButton editPlantBtn = view.findViewById(R.id.edit_plant_btn);
        ImageButton backToPlantsBtn = view.findViewById(R.id.back_to_plants_btn);

        plantName.setText(plant.getName());
        empty1.setVisibility(View.INVISIBLE);
        empty2.setVisibility(View.INVISIBLE);
        progressbarList.setVisibility(View.VISIBLE);

        wateringList = view.findViewById(R.id.plant_watering_list_recycle);
        wateringList.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        wateringList.setLayoutManager(layoutManager);
        LiveData<List<Watering>> data = WateringModel.instance.getWateringByPlantId(PlacePlantsFragment.instance.getCurrPlant().getId());
        WateringAdapter adapter = new WateringAdapter(data, getLayoutInflater());
        wateringList.setAdapter(adapter);
        wateringList.addItemDecoration(new WateringItemDecoration(7));

        data.observe(getViewLifecycleOwner(), new Observer<List<Watering>>() {
            @Override
            public void onChanged(List<Watering> waterings) {
                adapter.notifyDataSetChanged();
                progressbarList.setVisibility(View.INVISIBLE);

                if (waterings != null && waterings.size() > 0) {
                    empty1.setVisibility(View.INVISIBLE);
                    empty2.setVisibility(View.INVISIBLE);
                    wateringList.setVisibility(View.VISIBLE);
                } else {
                    empty1.setVisibility(View.VISIBLE);
                    empty2.setVisibility(View.VISIBLE);
                    wateringList.setVisibility(View.INVISIBLE);
                }
            }
        });

        addWateringBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NavController nav = Navigation.findNavController(view);
                if (nav.getCurrentDestination().getId() == R.id.plantWateringListFragment) {
                    plantWateringListFragmentDirections.ActionPlantWateringListToAddWatering action = plantWateringListFragmentDirections.actionPlantWateringListToAddWatering(plant, null);
                    nav.navigate(action);
                }
            }
        });

        backToPlantsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController nav = Navigation.findNavController(view);

                if (nav.getCurrentDestination().getId() == R.id.plantWateringListFragment) {
                    nav.popBackStack();
                }
            }
        });

        editPlantBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController nav = Navigation.findNavController(view);
                if (nav.getCurrentDestination().getId() == R.id.plantWateringListFragment) {
                    plantWateringListFragmentDirections.ActionPlantWateringListToAddPlantToPlace action = plantWateringListFragmentDirections.actionPlantWateringListToAddPlantToPlace(
                            PlacesListFragment.instance.getCurrPlace(),
                            plant);
                    nav.navigate(action);
                }
            }
        });

        return view;
    }
}