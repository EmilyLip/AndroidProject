package com.example.plantarium.PlacesFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.plantarium.HomePageFragments.LoginPageFragment;
import com.example.plantarium.Models.DBModels.PlaceMemberModel;
import com.example.plantarium.Models.DBModels.PlaceModel;
import com.example.plantarium.Models.Place;
import com.example.plantarium.Models.PlaceMember;
import com.example.plantarium.MyApplication;
import com.example.plantarium.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class PlacesListFragment extends Fragment {
    View view;
    RecyclerView placesList;
    RecyclerView.LayoutManager layoutManager;
    private PlacesListViewModel viewModel;
    public PlacesListAdapter adapter;
    public Place currPlace = null;
    public final static PlacesListFragment instance = new PlacesListFragment();
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=  inflater.inflate(R.layout.fragment_places_list, container, false);
        viewModel= new ViewModelProvider(this).get(PlacesListViewModel.class);
        mSwipeRefreshLayout = view.findViewById(R.id.swiperefresh_items);

        placesList = view.findViewById(R.id.places_list_rv);
        placesList.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(MyApplication.context);
        placesList.setLayoutManager(layoutManager);
        adapter = new PlacesListAdapter( viewModel.getUsersPlaceList().getValue());
        placesList.setAdapter(adapter);
        view.findViewById(R.id.progressBarList).setVisibility(View.VISIBLE);

        PlaceModel.instance.refreshAllPlaces(new PlaceModel.GetAllPlacesListener() {
            @Override
            public void onComplete() {
                view.findViewById(R.id.progressBarList).setVisibility(View.INVISIBLE);
            }
        });

        PlaceMemberModel.instance.refreshAllPlaceMembers(new PlaceMemberModel.GetAllPlaceMembersListener() {
            @Override
            public void onComplete() {
                view.findViewById(R.id.progressBarList).setVisibility(View.INVISIBLE);
            }
        });

        viewModel.getUsersPlaceList().observe(getViewLifecycleOwner(), new Observer<List<Place>>() {
            @Override
            public void onChanged(List<Place> places) {
                adapter.updateList(places);
                view.findViewById(R.id.progressBarList).setVisibility(View.INVISIBLE);
            }
        });

        viewModel.getPlaceMembersListList().observe(getViewLifecycleOwner(), new Observer<List<PlaceMember>>() {
            @Override
            public void onChanged(List<PlaceMember> placeMembers) {
                boolean isFoundInPlace = false;
                if(placeMembers != null && placeMembers.size() > 0 ){
                    for(PlaceMember placeMember : placeMembers){
                        if(placeMember.getUserEmail().equals(LoginPageFragment.getAccount().getEmail()) && placeMember.getDeleted() != 1){
                            isFoundInPlace = true;
                        }
                    }
                    if(!isFoundInPlace){
                        Navigation.findNavController(view).navigate(R.id.action_placesList_to_noPlaces);
                    }
                } else if(placeMembers.size() == 0) {
                    Navigation.findNavController(view).navigate(R.id.action_placesList_to_noPlaces);
                }
            }
        });

        adapter.setOnItemClickListener(new PlacesListAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Log.d("TAG","row was clicked " + viewModel.getUsersPlaceList().getValue().get(position).getName());
                Place place = viewModel.getUsersPlaceList().getValue().get(position);
                instance.currPlace = place;
                NavController nav = Navigation.findNavController(view);
                if (nav.getCurrentDestination().getId() == R.id.placesListFragment) {
                   PlacesListFragmentDirections.ActionPlacesListToPlacePlants action = PlacesListFragmentDirections.actionPlacesListToPlacePlants(place);
                    nav.navigate(action);
                }
            }
        });

        FloatingActionButton addPlaceBtn = view.findViewById(R.id.add_place_btn);

        addPlaceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController nav = Navigation.findNavController(v);
                if (nav.getCurrentDestination().getId() == R.id.placesListFragment) {
                    PlacesListFragmentDirections.ActionPlacesListToAddPlace action = PlacesListFragmentDirections.actionPlacesListToAddPlace(null);
                    nav.navigate(action);
                }
            }
        });

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to make your refresh action
                // CallYourRefreshingMethod();
                viewModel.refreshPlaces();
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

    public Place getCurrPlace() {
        return currPlace;
    }
}