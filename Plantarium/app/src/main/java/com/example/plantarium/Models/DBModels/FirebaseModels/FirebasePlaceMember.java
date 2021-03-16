package com.example.plantarium.Models.DBModels.FirebaseModels;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.plantarium.Models.DBModels.PlaceMemberModel;
import com.example.plantarium.Models.DBModels.UserModel;
import com.example.plantarium.Models.Place;
import com.example.plantarium.Models.PlaceMember;
import com.example.plantarium.Models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.LinkedList;
import java.util.List;

public class FirebasePlaceMember {

    public interface GetAllStudentsListener{
        void onComplete(List<PlaceMember> list);
    }
    public void getAllPlaceMembers(Long lastUpdated, final GetAllStudentsListener listener) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("message");
        //Timestamp ts = new Timestamp(lastUpdated,0);
        mDatabase.child("placeMembers").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                List<PlaceMember> data = new LinkedList<PlaceMember>();
                if (task.isSuccessful()){
                    for (DataSnapshot doc: task.getResult().getChildren()) {
                        PlaceMember place = doc.getValue(PlaceMember.class);
                        if(place.getLastUpdated() > lastUpdated)
                            data.add(place);
                    }
                }
                listener.onComplete(data);
            }
        });
    }


//    public void getAllPlaceMembers(PlaceMemberModel.GetAllPlaceMembersListListener listener) {
//        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("message");
//        mDatabase.child("placeMembers").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DataSnapshot> task) {
//                List<PlaceMember> data = new LinkedList<PlaceMember>();
//                if (task.isSuccessful()){
//                    for (DataSnapshot doc: task.getResult().getChildren()) {
//                        PlaceMember place = doc.getValue(PlaceMember.class);
//                        data.add(place);
//                    }
//                }
//                listener.onComplete(data);
//            }
//        });
//    }

    public void updatePlaceMember(PlaceMember placeMember, PlaceMemberModel.UpdatePlaceListener listener) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("message");
        mDatabase.child("placeMembers").child(placeMember.getId()).setValue(placeMember.toMap())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TAG","user updated successfully");
                        listener.onComplete();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("TAG","failed updating user");
            }
        });
    }
}
