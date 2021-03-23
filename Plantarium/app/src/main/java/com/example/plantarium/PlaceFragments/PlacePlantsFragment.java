package com.example.plantarium.PlaceFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.plantarium.Models.DBModels.PlantModel;
import com.example.plantarium.Models.Place;
import com.example.plantarium.Models.Plant;
import com.example.plantarium.R;
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

        List<Plant> data = PlantModel.instance.getAllPlants();
        PlantAdapter adapter = new PlantAdapter(data, getLayoutInflater());

        plantsList.setAdapter(adapter);
        plantsList.addItemDecoration(new MarginItemDecoration(40));

        return view;
    }
}