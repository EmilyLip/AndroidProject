package com.example.plantarium.PlacesFragments;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.plantarium.HomePageFragments.LoginPageFragment;
import com.example.plantarium.Models.DBModels.UserModel;
import com.example.plantarium.Models.Place;
import com.example.plantarium.Models.PlaceMember;
import com.example.plantarium.Models.User;
import com.example.plantarium.MyApplication;
import com.example.plantarium.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PlaceMembersFragment extends Fragment {

    View view;
    Place place;
    private PlaceMembersViewModel viewModel;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView placeMembersList;
    public PlaceMembersAdapter adapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_place_members, container, false);
        view.findViewById(R.id.progressBarPlaceMembers).setVisibility(View.VISIBLE);
        mSwipeRefreshLayout = view.findViewById(R.id.swiperefresh_items_members);

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

        UserModel.instance.refreshAllUsers(new UserModel.GetAllUsersListener() {
            @Override
            public void onComplete() {

            }
        });

        placeMembersList = view.findViewById(R.id.place_members_list_rv);
        placeMembersList.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(MyApplication.context);
        placeMembersList.setLayoutManager(layoutManager);
        adapter = new PlaceMembersAdapter( viewModel.getUsersPlaceList().getValue());
        placeMembersList.setAdapter(adapter);

        viewModel.getPlaceMembersList().observe(getViewLifecycleOwner(), new Observer<List<PlaceMember>>() {
            @Override
            public void onChanged(List<PlaceMember> places) {

            }
        });

        viewModel.getUsersPlaceList().observe(getViewLifecycleOwner(), new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> placeMembers) {
                updateUI(placeMembers);
            }
         });

        FloatingActionButton addPlaceBtn = view.findViewById(R.id.add_user_place_member_btn);

        addPlaceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController nav = Navigation.findNavController(v);
                if (nav.getCurrentDestination().getId() == R.id.placeMembersFragmnet) {
                    PlaceMembersFragmentDirections.ActionEmptyPlaceViewToAddMemberToPlace action = PlaceMembersFragmentDirections.actionEmptyPlaceViewToAddMemberToPlace(place);
                    nav.navigate(action);
                }
            }
        });

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to make your refresh action
                // CallYourRefreshingMethod();
                viewModel.refresh();
                updateUI(viewModel.getUsersPlaceList().getValue());
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(mSwipeRefreshLayout.isRefreshing()) {
                            mSwipeRefreshLayout.setRefreshing(false);
                        }
                    }
                }, 1000);
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NavController nav = Navigation.findNavController(view);
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                nav.popBackStack(R.id.placesListFragment, false);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);
    }

    private void updateUI(List<User> placeMembers){
        boolean isAlone = true;
        adapter.updateList(placeMembers);
        for(User user: placeMembers){
            if(!user.getEmail().equals(LoginPageFragment.getAccount().getEmail())){
                isAlone = false;
            }
        }
        if(isAlone) {
            view.findViewById(R.id.progressBarPlaceMembers).setVisibility(View.INVISIBLE);
            view.findViewById(R.id.add_user_place_member_btn).setVisibility(View.INVISIBLE);
            view.findViewById(R.id.no_place_members_view).setVisibility(View.VISIBLE);
            view.findViewById(R.id.place_members_users_list_view).setVisibility(View.INVISIBLE);
        }
        else {
            view.findViewById(R.id.progressBarPlaceMembers).setVisibility(View.INVISIBLE);
            view.findViewById(R.id.place_members_users_list_view).setVisibility(View.VISIBLE);
            view.findViewById(R.id.no_place_members_view).setVisibility(View.INVISIBLE);
            view.findViewById(R.id.add_user_place_member_btn).setVisibility(View.VISIBLE);
        }
    }
}