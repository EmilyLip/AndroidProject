package com.example.plantarium.PlacesFragments;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.plantarium.PlacesFragments.AddPlaceFragmentDirections;
import com.example.plantarium.Models.DBModels.PlaceModel;
import com.example.plantarium.Models.Place;
import com.example.plantarium.R;

import static android.app.Activity.RESULT_OK;

public class AddPlaceFragment extends Fragment {

    private static final int PICK_IMAGE_REQUEST = 111;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    View view;
    Button savePlaceBtn;
    ImageView placeImage;
    AppCompatEditText placeName;
    PlaceModel placeModel = new PlaceModel();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=  inflater.inflate(R.layout.fragment_manage_place, container, false);

        placeImage = (ImageView) view.findViewById(R.id.place_image);
        savePlaceBtn = (Button) view.findViewById(R.id.save_place_btn);
        placeName = view.findViewById(R.id.edit_place_name);
        savePlaceBtn.setEnabled(false);

        ImageButton createPlaceBtn =  (ImageButton) view.findViewById(R.id.addImageButton);
        createPlaceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });


        savePlaceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // save place to db
                savePlace();
            }
        });

        placeName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                BitmapDrawable image =  (BitmapDrawable)placeImage.getDrawable();
                if(!s.toString().isEmpty() && image != null ){
                    savePlaceBtn.setEnabled(true);
                }
                else {
                    savePlaceBtn.setEnabled(false);
                }
            }
        });

        return view;
    }

    private void savePlace() {
        Bitmap imageBitmap =  ((BitmapDrawable)placeImage.getDrawable()).getBitmap();
        Place newPlace = new Place(placeName.getText().toString());
        placeModel.uploadPlaceImage(imageBitmap, newPlace.getId(),
                new PlaceModel.UploadImageListenr() {
            @Override
            public void onComplete(String url) {
                // save to DB
                newPlace.setImageUrl(url);
                placeModel.updatePlace(newPlace, new PlaceModel.UpdatePlaceListener() {
                    @Override
                    public void onComplete() {
                        AddPlaceFragmentDirections.ActionAddPlaceToEmptyPlaceView action = AddPlaceFragmentDirections.actionAddPlaceToEmptyPlaceView(newPlace);
                        Navigation.findNavController(view).navigate(action);
                    }
                });
            }
        } );
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
            placeImage.setImageBitmap(imageBitmap);
            if(!placeName.getText().toString().isEmpty()) {
                savePlaceBtn.setEnabled(true);
            }
        }
    }
}