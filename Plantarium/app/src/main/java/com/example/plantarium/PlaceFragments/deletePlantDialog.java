package com.example.plantarium.PlaceFragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.fragment.NavHostFragment;

import com.example.plantarium.Models.DBModels.PlantModel;
import com.example.plantarium.Models.Plant;
import com.example.plantarium.R;

public class deletePlantDialog extends DialogFragment implements View.OnClickListener {
    View view;
    FragmentTransaction ft;
    FragmentManager fragmentManager;
    Plant plant;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        plant = deletePlantDialogArgs.fromBundle(getArguments()).getPlant();

        view = inflater.inflate(R.layout.fragment_delete_plant_dialog, container, false);
        view.findViewById(R.id.delete_plant_cancel).setOnClickListener((View.OnClickListener) this);
        view.findViewById(R.id.delete_plant_confirm).setOnClickListener((View.OnClickListener) this);
        TextView title = view.findViewById(R.id.delete_plant_question);
        title.setText("למחוק את " + plant.getName() + "?");
        view.findViewById(R.id.delete_plant_progressbar_dialog).setVisibility(View.INVISIBLE);
        return view;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.delete_plant_cancel:
                this.dismiss();
                break;
            case R.id.delete_plant_confirm:
                Log.w("TAG", "deleting plant " + plant.getName());
                view.findViewById(R.id.delete_plant_cancel).setVisibility(View.INVISIBLE);
                view.findViewById(R.id.delete_plant_confirm).setVisibility(View.INVISIBLE);
                view.findViewById(R.id.delete_plant_cancel_text).setVisibility(View.INVISIBLE);
                view.findViewById(R.id.delete_plant_progressbar_dialog).setVisibility(View.VISIBLE);
                deletePlantDialog x = this;
                if (plant != null) {
                    plant.setDeleted(1);
                    PlantModel.instance.updatePlant(plant, new PlantModel.UpdatePlantListener() {
                        @Override
                        public void onComplete() {
                            NavHostFragment.findNavController(x).navigate(R.id.action_deletePlantDialog_pop);
                            dismiss();
                        }
                    });
                }

                break;
        }
    }

    @Override
    public void show(@NonNull FragmentManager manager, @Nullable String tag) {
        super.show(manager, tag);
        fragmentManager = manager;
        ft = manager.beginTransaction();
    }
}