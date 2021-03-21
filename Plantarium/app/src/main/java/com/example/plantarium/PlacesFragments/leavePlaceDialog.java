package com.example.plantarium.PlacesFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.plantarium.HomePageFragments.LoginPageFragment;
import com.example.plantarium.Models.DBModels.PlaceMemberModel;
import com.example.plantarium.Models.DBModels.PlaceModel;
import com.example.plantarium.Models.Place;
import com.example.plantarium.Models.PlaceMember;
import com.example.plantarium.MyApplication;
import com.example.plantarium.R;

public class leavePlaceDialog extends DialogFragment  implements View.OnClickListener {
    View view;
    FragmentTransaction ft;
    FragmentManager fragmentManager;
    Place place;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_leave_place_dialog, container, false);
        view.findViewById(R.id.cancel_leave_btn).setOnClickListener((View.OnClickListener) this);
        view.findViewById(R.id.leave_btn).setOnClickListener((View.OnClickListener) this);
        place = AddMemberToPlaceArgs.fromBundle(getArguments()).getPlace();
        view.findViewById(R.id.progressBardialog).setVisibility(View.INVISIBLE);
        return view;
    }

    private void signOut() {
        LoginPageFragment.getmGoogleSignInClient().signOut();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel_leave_btn:
                this.dismiss();
                break;
            case R.id.leave_btn:
                Log.w("TAG", "removing from" + place.getName());
                view.findViewById(R.id.cancel_leave_btn).setVisibility(View.INVISIBLE);
                view.findViewById(R.id.leave_btn).setVisibility(View.INVISIBLE);
                view.findViewById(R.id.progressBardialog).setVisibility(View.VISIBLE);
                PlaceMemberModel.instance.getPlaceMemberByPlaceAndUser(LoginPageFragment.getAccount().getEmail(), place.getId(), new PlaceMemberModel.getPlaceMemberByPlaceAndUserListener(){
                    @Override
                    public void onComplete(PlaceMember o) {
                        Log.w("TAG", o.getId());
                        o.setDeleted(1);
                        PlaceMemberModel.instance.updatePlaceMember(o, new PlaceMemberModel.UpdatePlaceMemberListener() {
                            @Override
                            public void onComplete() {
                                view.findViewById(R.id.progressBardialog).setVisibility(View.INVISIBLE);
                                dismiss();
                            }
                        });
                    }
                });

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