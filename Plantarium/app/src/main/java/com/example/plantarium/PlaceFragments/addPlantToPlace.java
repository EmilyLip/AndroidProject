package com.example.plantarium.PlaceFragments;

import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.plantarium.Models.DBModels.PlantModel;
import com.example.plantarium.Models.Place;
import com.example.plantarium.Models.Plant;
import com.example.plantarium.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.stream.Collectors;

import de.hdodenhof.circleimageview.CircleImageView;

public class addPlantToPlace extends Fragment {

    View view;
    Button savePlantBtn;
    AppCompatEditText plantName;
    AppCompatEditText plantType;
    PlantModel plantModel = new PlantModel();
    ArrayList<CheckBox> wateringDaysCheckboxes = new ArrayList<CheckBox>();
    ProgressBar progressBar;
    Place place;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_plant_to_place, container, false);
        place = addPlantToPlaceArgs.fromBundle((getArguments())).getPlace();

        plantName = view.findViewById(R.id.edit_plant_name);
        plantType = view.findViewById(R.id.edit_plant_type);
        savePlantBtn = view.findViewById(R.id.save_plant);
        View wateringDay = view.findViewById(R.id.wateringDays_day1);
        wateringDaysCheckboxes.add(0, null);
        wateringDaysCheckboxes.add(1, (CheckBox)(view.findViewById(R.id.wateringDays_day1)));
        wateringDaysCheckboxes.add(2, (CheckBox)(view.findViewById(R.id.wateringDays_day2)));
        wateringDaysCheckboxes.add(3, (CheckBox)(view.findViewById(R.id.wateringDays_day3)));
        wateringDaysCheckboxes.add(4, (CheckBox)(view.findViewById(R.id.wateringDays_day4)));
        wateringDaysCheckboxes.add(5, (CheckBox)(view.findViewById(R.id.wateringDays_day5)));
        wateringDaysCheckboxes.add(6, (CheckBox)(view.findViewById(R.id.wateringDays_day6)));
        wateringDaysCheckboxes.add(7, (CheckBox)(view.findViewById(R.id.wateringDays_day7)));
        progressBar = view.findViewById(R.id.add_plant_progressbar);
        progressBar.setVisibility(View.INVISIBLE);

        CircleImageView placeImage = view.findViewById(R.id.add_plant_place_image);
        TextView placeName = view.findViewById(R.id.add_plant_place_name);
        ProgressBar progressBarImg = view.findViewById(R.id.add_plant_progressBarImage);

        placeName.setText(place.getName());
        placeImage.setVisibility(View.INVISIBLE);
        progressBarImg.setVisibility(View.VISIBLE);
        if (place.getImageUrl() != null){
            Picasso.get().load(place.getImageUrl()).into(placeImage);
            placeImage.setVisibility(View.VISIBLE);
            progressBarImg.setVisibility(View.INVISIBLE);
        }

        savePlantBtn.setEnabled(false);

        savePlantBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                // save plant to db
                savePlant();
            }
        });

        plantName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!s.toString().isEmpty()){
                    savePlantBtn.setEnabled(true);
                }
                else {
                    savePlantBtn.setEnabled(false);
                }
            }
        });

        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void savePlant() {
        ArrayList<Integer> wateringDaysInt = (ArrayList<Integer>) wateringDaysCheckboxes.stream().map(ch -> ch != null ? (ch.isChecked() ? 1 : 0) : 0).collect(Collectors.toList());

        Plant newPlant = new Plant(
                plantName.getText().toString(),
                plantType.getText().toString(),
                place.getId(),
                wateringDaysInt);

        progressBar.setVisibility(View.VISIBLE);
        NavController nav = Navigation.findNavController(view);

        plantModel.updatePlant(newPlant, new PlantModel.UpdatePlantListener() {
            @Override
            public void onComplete() {
                if (nav.getCurrentDestination().getId() == R.id.addPlantToPlace) {
                    //AddPlaceFragmentDirections.ActionAddPlaceToEmptyPlaceView action = AddPlaceFragmentDirections.actionAddPlaceToEmptyPlaceView(newPlace);
                    progressBar.setVisibility(View.INVISIBLE);
                    nav.popBackStack();
                }
            }
        });
    }
}