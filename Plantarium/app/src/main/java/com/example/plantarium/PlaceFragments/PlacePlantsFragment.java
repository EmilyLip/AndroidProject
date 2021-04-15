package com.example.plantarium.PlaceFragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plantarium.MainActivity;
import com.example.plantarium.Models.DBModels.PlantModel;
import com.example.plantarium.Models.Place;
import com.example.plantarium.Models.Plant;
import com.example.plantarium.PlacesFragments.PlacesListFragment;
import com.example.plantarium.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PlacePlantsFragment extends Fragment {

    RecyclerView plantsList;
    LinearLayoutManager layoutManager;
    public PlantAdapter adapter;
    private ProgressBar progressbarList;
    public Plant currPlant = null;
    public final static PlacePlantsFragment instance = new PlacePlantsFragment();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_place_listplants, container, false);

        Place place = PlacesListFragment.instance.getCurrPlace();
        TextView placeName = view.findViewById(R.id.place_plants_place_name);
        CircleImageView placeImage = view.findViewById(R.id.place_plants_place_image);
        plantsList = view.findViewById(R.id.placef_plantslist);
        ProgressBar progressBarImg = view.findViewById(R.id.listplant_progressBarImage);
        FloatingActionButton addPlantBtn = view.findViewById(R.id.fab_add_plant);
        TextView empty1 = view.findViewById(R.id.plants_list_empty1);
        TextView empty2 = view.findViewById(R.id.plants_list_empty2);
        progressbarList = view.findViewById(R.id.plants_list_progressbar);
        progressbarList.setVisibility(View.VISIBLE);
        empty1.setVisibility(View.INVISIBLE);
        empty2.setVisibility(View.INVISIBLE);

        placeName.setText(place.getName());
        placeImage.setVisibility(View.INVISIBLE);
        progressBarImg.setVisibility(View.VISIBLE);
        if (place.getImageUrl() != null){
            Picasso.get().load(place.getImageUrl()).into(placeImage);
            placeImage.setVisibility(View.VISIBLE);
        }

        plantsList.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        plantsList.setLayoutManager(layoutManager);
        LiveData<List<Plant>> data = PlantModel.instance.getPlantsByPlaceId(place.getId());
        adapter = new PlantAdapter(data, new PlantAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Plant plant = data.getValue().get(position);
                instance.currPlant = plant;
                Log.d("TAG","plant was clicked " + plant.getName());

                NavController nav = Navigation.findNavController(view);
                if (nav.getCurrentDestination().getId() == R.id.placePlantsFragment) {
                    PlacePlantsFragmentDirections.ActionPlacePlantsToPlantWateringList action = PlacePlantsFragmentDirections.actionPlacePlantsToPlantWateringList(plant);
                    nav.navigate(action);
                }
            }
        });
        plantsList.setAdapter(adapter);
        plantsList.addItemDecoration(new MarginItemDecoration(40));

        data.observe(getViewLifecycleOwner(), new Observer<List<Plant>>() {
            @Override
            public void onChanged(List<Plant> plants) {
                adapter.notifyDataSetChanged();
                progressbarList.setVisibility(View.INVISIBLE);

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
                    PlacePlantsFragmentDirections.ActionPlacePlantsToAddPlantToPlace action = PlacePlantsFragmentDirections.actionPlacePlantsToAddPlantToPlace(place, null);
                    nav.navigate(action);
                }
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NavController nav = Navigation.findNavController(view);
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                nav.popBackStack(R.id.placesListFragment, false);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);
    }

    public Plant getCurrPlant() {
        return currPlant;
    }
}