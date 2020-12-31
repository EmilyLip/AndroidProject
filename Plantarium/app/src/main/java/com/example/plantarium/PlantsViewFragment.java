package com.example.plantarium;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.io.Serializable;


public class PlantsViewFragment extends Fragment implements View.OnClickListener, Serializable {

    private static final String TAG = "Plants Fragment";

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_plants_view, container, false);
        TextView greetingMessage = view.findViewById(R.id.greeting_message);
        greetingMessage.setText("שלום " + LoginPageFragment.getAccount().getDisplayName());
        Button logoutButton = view.findViewById(R.id.logout_button);
        view.findViewById(R.id.logout_button).setOnClickListener((View.OnClickListener) this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // ...
            case R.id.logout_button:
                signOut();
                break;
            // ...
        }
    }

    private void signOut() {
        LoginPageFragment.getmGoogleSignInClient().signOut();
        Navigation.findNavController(view).navigate(R.id.action_plantsViewFragment_pop);
    }
}