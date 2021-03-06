package com.example.plantarium.PlacesFragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
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
import android.widget.ProgressBar;

import com.example.plantarium.HomePageFragments.LoginPageFragment;
import com.example.plantarium.Models.DBModels.PlaceMemberModel;
import com.example.plantarium.Models.PlaceMember;
import com.example.plantarium.Models.DBModels.PlaceModel;
import com.example.plantarium.Models.Place;
import com.example.plantarium.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class AddPlaceFragment extends Fragment {

    private static final int PICK_IMAGE_REQUEST = 111;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    View view;
    Button savePlaceBtn;
    ImageView placeImage;
    ProgressBar progressBar;
    AppCompatEditText placeName;
    PlaceModel placeModel = new PlaceModel();
    PlaceMemberModel placeMemberModel = new PlaceMemberModel();
    Place place;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=  inflater.inflate(R.layout.fragment_manage_place, container, false);

        placeImage = (ImageView) view.findViewById(R.id.place_image);
        savePlaceBtn = (Button) view.findViewById(R.id.save_place_btn);
        placeName = view.findViewById(R.id.edit_place_name);
        progressBar = view.findViewById(R.id.progressBar);

        savePlaceBtn.setEnabled(false);
        progressBar.setVisibility(View.INVISIBLE);

        place = AddPlaceFragmentArgs.fromBundle(getArguments()).getPlace();
        if (place != null && place.getImageUrl() != null){
            Picasso.get().load(place.getImageUrl()).into(placeImage);
        }
        if (place != null){
            Picasso.get().load(place.getImageUrl()).into(placeImage);
            placeName.setText(place.getName());
            savePlaceBtn.setText("שמור");
        }

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
                if(place == null)
                    savePlace();
                else
                    editPlace();
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
        PlaceMember newPlaceMember = new PlaceMember(LoginPageFragment.getAccount().getEmail(), newPlace.getId());

        progressBar.setVisibility(View.VISIBLE);
        placeModel.uploadPlaceImage(imageBitmap, newPlace.getId(),
                new PlaceModel.UploadImageListenr() {
            @Override
            public void onComplete(String url) {
                // save to DB
                newPlace.setImageUrl(url);
                NavController nav = Navigation.findNavController(view);
                placeModel.updatePlace(newPlace, new PlaceModel.UpdatePlaceListener() {
                    @Override
                    public void onComplete() {
                        placeMemberModel.updatePlaceMember(newPlaceMember, new PlaceMemberModel.UpdatePlaceMemberListener() {
                            @Override
                            public void onComplete() {
                                if (nav.getCurrentDestination().getId() == R.id.addPlaceFragment) {
                                    PlacesListFragment.instance.currPlace = newPlace;
                                    AddPlaceFragmentDirections.ActionAddPlaceFragmentToPlacePlantsFragment action = AddPlaceFragmentDirections.actionAddPlaceFragmentToPlacePlantsFragment(newPlace);
                                    progressBar.setVisibility(View.INVISIBLE);
                                    nav.navigate(action);
                                }
                            }
                        });
                    }
                });
            }
        } );
    }

    private void editPlace() {
        Bitmap imageBitmap =  ((BitmapDrawable)placeImage.getDrawable()).getBitmap();
        place.setName(placeName.getText().toString());
        progressBar.setVisibility(View.VISIBLE);
        placeModel.uploadPlaceImage(imageBitmap, place.getId(),
                new PlaceModel.UploadImageListenr() {
                    @Override
                    public void onComplete(String url) {
                        // save to DB
                        place.setImageUrl(url);
                        NavController nav = Navigation.findNavController(view);
                        placeModel.updatePlace(place, new PlaceModel.UpdatePlaceListener() {
                            @Override
                            public void onComplete() {
                                progressBar.setVisibility(View.INVISIBLE);
                                nav.popBackStack();
                            }
                        });
                    }
                } );
    }

    private void showFileChooser() {
        final CharSequence[] options = { "צילום תמונה", "בחירה מהגלריה","ביטול" };

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("בחירת תמונה עבור ההשקייה");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("צילום תמונה")) {
                    Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 0);

                } else if (options[item].equals("בחירה מהגלריה")) {
                    //Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    //startActivityForResult(pickPhoto , 1);
                    Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                    photoPickerIntent.setType("image/*");
                    startActivityForResult(photoPickerIntent, 1);

                } else if (options[item].equals("ביטול")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK && data != null) {
                        Bundle extras = data.getExtras();
                        Bitmap imageBitmap = (Bitmap) extras.get("data");
                        placeImage.setImageBitmap(imageBitmap);
                        if(!placeName.getText().toString().isEmpty()) {
                            savePlaceBtn.setEnabled(true);
                        }
                    }
                    break;
                case 1:
                    if (resultCode == RESULT_OK && data != null) {
                        Uri selectedImage = data.getData();
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                            placeImage.setImageBitmap(bitmap);
                            if(!placeName.getText().toString().isEmpty()) {
                                savePlaceBtn.setEnabled(true);
                            }
                        } catch (IOException e) {

                        }
                    }
                    break;
            }
        }
    }
}