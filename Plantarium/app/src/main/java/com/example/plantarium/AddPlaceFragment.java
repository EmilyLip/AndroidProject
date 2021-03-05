package com.example.plantarium;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.plantarium.Models.Place;
import com.google.android.material.textfield.TextInputEditText;

import static android.app.Activity.RESULT_OK;


public class AddPlaceFragment extends Fragment {

    private static final int PICK_IMAGE_REQUEST = 111;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=  inflater.inflate(R.layout.fragment_manage_place, container, false);

        ImageButton createPlaceBtn =  (ImageButton) view.findViewById(R.id.addImageButton);
        createPlaceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });

        Button savePlaceBtn = (Button) view.findViewById(R.id.save_place_btn);
        savePlaceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // save place to db
                savePlace();
            }
        });

        return view;
    }

    private void savePlace() {
        AppCompatEditText placeName = view.findViewById(R.id.edit_place_name);
       Place newPlace = new Place(Place.curr_id, placeName.getText().toString(), "");

       // save to DB
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        } catch (ActivityNotFoundException e) {
            // display error state to the user
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ImageView placeImage = (ImageView) view.findViewById(R.id.place_image);
            placeImage.setImageBitmap(imageBitmap);
        }
    }
}