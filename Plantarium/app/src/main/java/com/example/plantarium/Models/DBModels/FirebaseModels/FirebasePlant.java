package com.example.plantarium.Models.DBModels.FirebaseModels;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.plantarium.Models.DBModels.PlantModel;
import com.example.plantarium.Models.Plant;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.LinkedList;
import java.util.List;

public class FirebasePlant {

    public void updatePlant(Plant plant, PlantModel.UpdatePlantListener listener) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("message");
        String plantId = String.valueOf(plant.getId());
        mDatabase.child("plants").child(plantId).setValue(plant.toMap())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TAG","place updated successfully");
                        listener.onComplete();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("TAG","failed updating place");
            }
        });
    }

    public interface GetAllPlantsListener{
        void onComplete(List<Plant> list);
    }
    public void getAllPlants(Long lastUpdated, final GetAllPlantsListener listener) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("message");
        mDatabase.child("plants").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                List<Plant> data = new LinkedList<Plant>();
                if (task.isSuccessful()){
                    for (DataSnapshot doc: task.getResult().getChildren()) {
                        Plant plant = doc.getValue(Plant.class);
                        if(plant.getLastUpdated() > lastUpdated)
                            data.add(plant);
                    }
                }
                listener.onComplete(data);
            }
        });
    }
}
