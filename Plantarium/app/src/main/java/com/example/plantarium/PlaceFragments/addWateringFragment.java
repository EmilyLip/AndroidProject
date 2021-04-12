package com.example.plantarium.PlaceFragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.plantarium.Models.DBModels.UserModel;
import com.example.plantarium.Models.DBModels.WateringModel;
import com.example.plantarium.Models.Plant;
import com.example.plantarium.Models.User;
import com.example.plantarium.Models.Watering;
import com.example.plantarium.MyApplication;
import com.example.plantarium.PlacesFragments.PlacesListFragment;
import com.example.plantarium.R;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class addWateringFragment extends Fragment implements AdapterView.OnItemSelectedListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private List<User> placeMembersUsers;
    private User wateringUser;
    private EditText wateringDateInput;
    private Date wateringDate;
    int hour, minute, day, month, year;
    private Button saveWateringBtn;
    private ImageView wateringImage;
    private Watering watering = null;
    private ProgressBar wateringProgressBar;
    private Plant plant;
    private WateringModel wateringModel = new WateringModel();
    private View view;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_watering, container, false);
        plant = addWateringFragmentArgs.fromBundle(getArguments()).getPlant();

        TextView plantName = view.findViewById(R.id.add_watering_plant_name);
        Spinner userNameDropdown = view.findViewById(R.id.add_watering_user);
        wateringDateInput = view.findViewById(R.id.add_watering_date);
        saveWateringBtn = (Button) view.findViewById(R.id.add_watering_save);
        wateringProgressBar = view.findViewById(R.id.add_water_progressbar);
        wateringImage = view.findViewById(R.id.add_watering_image);
        ImageButton addImage = view.findViewById(R.id.add_watering_image_button);

        plantName.setText(plant.getName());
        wateringImage.setVisibility(View.INVISIBLE);
        saveWateringBtn.setEnabled(false);
        wateringProgressBar.setVisibility(View.INVISIBLE);

        // Drop down users
        UserModel.instance.getAllPlaceMembersUser(PlacesListFragment.instance.getCurrPlace().getId())
                .observe(getViewLifecycleOwner(), new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> _placeMembers) {
                placeMembersUsers = _placeMembers;
                if(placeMembersUsers != null && placeMembersUsers.size() > 0) {
                    List<String> placeMembersNames = placeMembersUsers.stream().map(mu -> mu.fullname).collect(Collectors.toList());
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(MyApplication.context, android.R.layout.simple_spinner_item, placeMembersNames);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    userNameDropdown.setAdapter(adapter);
                    userNameDropdown.setOnItemSelectedListener(addWateringFragment.this);
                }
            }
        });

        // Date
        wateringDateInput.setInputType(InputType.TYPE_NULL);
        wateringDateInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), addWateringFragment.this, year, month, day);
                datePickerDialog.show();
            }
        });

        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });

        saveWateringBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // save place to db
                if(watering == null)
                    saveWatering();
//                else
//                    editWatering();
            }
        });

        return view;
    }

    private Boolean enableSaveButton() {
        return (wateringUser != null) && (wateringDate != null) && ((BitmapDrawable)wateringImage.getDrawable() != null);
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        Log.d("TAG", placeMembersUsers.get(pos).fullname + " was selected");
        wateringUser = placeMembersUsers.get(pos);
        saveWateringBtn.setEnabled(enableSaveButton());
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
        Log.d("TAG", "nothing selected");
        wateringUser = null;
        saveWateringBtn.setEnabled(enableSaveButton());
    }

    @Override
    public void onDateSet(DatePicker view, int _year, int _month, int _dayOfMonth) {
        day = _dayOfMonth;
        month = _month;
        year = _year;

        Calendar c = Calendar.getInstance();
        int cHour = c.get(Calendar.HOUR_OF_DAY);
        int cMinute = c.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                getActivity(),
                addWateringFragment.this,
                cHour,
                cMinute,
                true);
        timePickerDialog.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onTimeSet(TimePicker view, int _hour, int _minute) {
        hour = _hour;
        minute = _minute;

        ZoneId zoneId_IL = ZoneId.of("Asia/Jerusalem");
        ZonedDateTime zdt_IL = ZonedDateTime.of(year, month + 1, day, hour , minute , 1 , 0 , zoneId_IL );

        LocalDate localDate_IL = zdt_IL.toLocalDate();

        wateringDate = Date.from(localDate_IL.atTime(hour , minute)
                .atZone(zoneId_IL)
                .toInstant());
        Log.d("TAG", wateringDate.toString());

        wateringDateInput.setText(String.format("%02d/%02d/%04d, %02d:%02d", day, month + 1, year, hour, minute));
        saveWateringBtn.setEnabled(enableSaveButton());
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
                        wateringImage.setVisibility(View.VISIBLE);
                        wateringImage.setImageBitmap(imageBitmap);
                        saveWateringBtn.setEnabled(enableSaveButton());

                    }
                    break;
                case 1:
                    if (resultCode == RESULT_OK && data != null) {
                        Uri selectedImage = data.getData();
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                            wateringImage.setVisibility(View.VISIBLE);
                            wateringImage.setImageBitmap(bitmap);
                            saveWateringBtn.setEnabled(enableSaveButton());

                        } catch (IOException e) {

                        }
                    }
                    break;
            }
        }
    }

    private void saveWatering() {
        if (enableSaveButton()) {
            Bitmap imageBitmap = ((BitmapDrawable) wateringImage.getDrawable()).getBitmap();
            watering = new Watering(plant.getId(), wateringUser.getId(), wateringDate);

            wateringProgressBar.setVisibility(View.VISIBLE);
            wateringModel.uploadWateringImage(imageBitmap, watering.getId(),
                    new WateringModel.UploadImageListenr() {
                        @Override
                        public void onComplete(String url) {
                            // save to DB
                            watering.setImageUrl(url);
                            NavController nav = Navigation.findNavController(view);
                            wateringModel.updateWatering(watering, new WateringModel.UpdateWateringListener() {
                                @Override
                                public void onComplete() {
                                    Navigation.findNavController(view).popBackStack();
                                }
                            });
                        }
                    });
        }
    }
}