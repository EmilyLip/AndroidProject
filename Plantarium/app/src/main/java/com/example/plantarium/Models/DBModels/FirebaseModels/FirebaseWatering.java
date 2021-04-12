package com.example.plantarium.Models.DBModels.FirebaseModels;

import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.plantarium.Models.DBModels.WateringModel;
import com.example.plantarium.Models.Watering;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.LinkedList;
import java.util.List;

public class FirebaseWatering {

    public void updateWatering(Watering watering, WateringModel.UpdateWateringListener listener) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("message");
        String wateringId = String.valueOf(watering.getId());
        mDatabase.child("waterings").child(wateringId).setValue(watering.toMap())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TAG","watering updated successfully");
                        listener.onComplete();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("TAG","failed updating watering");
            }
        });
    }

    public interface GetAllWateringsListener {
        void onComplete(List<Watering> list);
    }
    public void getAllWaterings(Long lastUpdated, final FirebaseWatering.GetAllWateringsListener listener) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("message");
        mDatabase.child("waterings").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                List<Watering> data = new LinkedList<Watering>();
                if (task.isSuccessful()){
                    for (DataSnapshot doc: task.getResult().getChildren()) {
                        Watering watering = doc.getValue(Watering.class);
                        if(watering.getLastUpdated() > lastUpdated)
                            data.add(watering);
                    }
                }
                listener.onComplete(data);
            }
        });
    }

    public void uploadImage(Bitmap imageBmp, String name, final WateringModel.UploadImageListenr listener){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        final StorageReference imagesRef = storage.getReference().child("images").child(name);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imageBmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        UploadTask uploadTask = imagesRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception exception) {
                listener.onComplete(null);
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imagesRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Uri downloadUrl = uri;
                        listener.onComplete(downloadUrl.toString());
                    }
                });
            }
        });
    }
}
