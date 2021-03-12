package com.example.plantarium.Models.DBModels.FirebaseModels;

import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.plantarium.Models.DBModels.PlaceModel;
import com.example.plantarium.Models.DBModels.UserModel;
import com.example.plantarium.Models.Place;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.LinkedList;
import java.util.List;


public class FirebasePlace {

    public void updatePlace(Place place, PlaceModel.UpdatePlaceListener listener) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("message");
        String placeId = String.valueOf(place.getId());
        mDatabase.child("places").child(placeId).setValue(place.toMap())
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

    public void getAllPlaces(final PlaceModel.GetAllPlacesListener listener) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("message");
        mDatabase.child("places").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                List<Place> data = new LinkedList<Place>();
                if (task.isSuccessful()){
                    for (DataSnapshot doc: task.getResult().getChildren()) {
                        Place place = doc.getValue(Place.class);
                        data.add(place);
                    }
                }
                listener.onComplete(data);
            }
        });
    }

    public void uploadImage(Bitmap imageBmp, String name, final PlaceModel.UploadImageListenr listener){
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
