package com.example.plantarium.PlacesFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.plantarium.HomePageFragments.LoginPageFragment;
import com.example.plantarium.Models.Place;
import com.example.plantarium.Models.PlaceMember;
import com.example.plantarium.Models.User;
import com.example.plantarium.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PlaceMembersFragment extends Fragment {

    View view;
    Place place;
    private PlaceMembersViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_place_members, container, false);
        //place = PlaceMembersFragmentArgs.fromBundle(getArguments()).getPlace();

        place = PlacesListFragment.instance.getCurrPlace();
        CircleImageView placeImage = view.findViewById(R.id.place_image);
        TextView placeName = view.findViewById(R.id.place_name);
        ProgressBar progressBar = view.findViewById(R.id.progressBarImage);

        placeName.setText(place.getName());
        placeImage.setVisibility(View.INVISIBLE);
        if (place.getImageUrl() != null){
            Picasso.get().load(place.getImageUrl()).into(placeImage);
            placeImage.setVisibility(View.VISIBLE);
        }

        Button addMembers = view.findViewById(R.id.addMemberBtn);
        addMembers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController nav = Navigation.findNavController(view);
                if (nav.getCurrentDestination().getId() == R.id.placeMembersFragmnet) {
                    PlaceMembersFragmentDirections.ActionEmptyPlaceViewToAddMemberToPlace action = PlaceMembersFragmentDirections.actionEmptyPlaceViewToAddMemberToPlace(place);
                    nav.navigate(action);
                }
            }
        });

        viewModel= new ViewModelProvider(this).get(PlaceMembersViewModel.class);
        viewModel.getPlaceMembersList().observe(getViewLifecycleOwner(), new Observer<List<PlaceMember>>() {
            @Override
            public void onChanged(List<PlaceMember> places) {
                
            }
        });

        viewModel.getUsersPlaceList().observe(getViewLifecycleOwner(), new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> placeMembers) {

            }
        });

        return view;
    }
}