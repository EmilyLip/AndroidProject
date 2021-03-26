package com.example.plantarium.PlaceFragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plantarium.Models.DBModels.PlantModel;
import com.example.plantarium.Models.Place;
import com.example.plantarium.Models.Plant;
import com.example.plantarium.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlacePlantsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlacePlantsFragment extends Fragment {

    RecyclerView plantsList;
    LinearLayoutManager layoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_place_listplants, container, false);

        Place place = PlacePlantsFragmentArgs.fromBundle(getArguments()).getPlace();
        TextView placeName = view.findViewById(R.id.place_plants_place_name);
        CircleImageView placeImage = view.findViewById(R.id.place_plants_place_image);
        plantsList = view.findViewById(R.id.placef_plantslist);
        ProgressBar progressBar = view.findViewById(R.id.listplant_progressBarImage);
        FloatingActionButton addPlantBtn = view.findViewById(R.id.fab_add_plant);
        TextView empty1 = view.findViewById(R.id.plants_list_empty1);
        TextView empty2 = view.findViewById(R.id.plants_list_empty2);

        placeName.setText(place.getName());
        placeImage.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        if (place.getImageUrl() != null){
            Picasso.get().load(place.getImageUrl()).into(placeImage);
            placeImage.setVisibility(View.VISIBLE);
        }

        plantsList.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        plantsList.setLayoutManager(layoutManager);
        LiveData<List<Plant>> data = PlantModel.instance.getPlantsByPlaceId(place.getId());
        PlantAdapter adapter = new PlantAdapter(data, getLayoutInflater());
        plantsList.setAdapter(adapter);
        plantsList.addItemDecoration(new MarginItemDecoration(40));

        data.observe(getViewLifecycleOwner(), new Observer<List<Plant>>() {
            @Override
            public void onChanged(List<Plant> plants) {
                adapter.notifyDataSetChanged();

                if (plants != null && plants.size() > 0) {
                    empty1.setVisibility(View.INVISIBLE);
                    empty2.setVisibility(View.INVISIBLE);
                    plantsList.setVisibility(View.VISIBLE);
                } else {
                    empty1.setVisibility(View.VISIBLE);
                    empty2.setVisibility(View.VISIBLE);
                    plantsList.setVisibility(View.INVISIBLE);
                }
            }
        });

        addPlantBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NavController nav = Navigation.findNavController(view);
                if (nav.getCurrentDestination().getId() == R.id.placePlantsFragment) {
                    PlacePlantsFragmentDirections.ActionPlacePlantsToAddPlantToPlace action = PlacePlantsFragmentDirections.actionPlacePlantsToAddPlantToPlace(place);
                    nav.navigate(action);
                }
            }
        });

        return view;
    }
}