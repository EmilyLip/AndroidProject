package com.example.plantarium.Models.DBModels.FirebaseModels;

import android.util.Log;
import androidx.annotation.NonNull;

import com.example.plantarium.Models.DBModels.UserModel;
import com.example.plantarium.Models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Map;

public class FirebaseUser {

    public void getUserByID(String userId, UserModel.GetUsertListener listener) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("message");
        mDatabase.child("users").child(userId).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                    listener.onComplete(task.getResult().getValue(User.class));
                }
            }
        });
    }

    public void updateUser(User user, UserModel.AddUserListener listener) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("message");
        mDatabase.child("users").child(user.getId()).setValue(user.toMap())
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
