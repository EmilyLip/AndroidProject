package com.example.plantarium.HomePageFragments;



import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.fragment.NavHostFragment;

import com.example.plantarium.R;


public class LogoutDialogFragment extends DialogFragment implements View.OnClickListener {
    View view;
    FragmentTransaction ft;
    FragmentManager fragmentManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_logout_dialog, container, false);
        view.findViewById(R.id.cancel_leave_btn).setOnClickListener((View.OnClickListener) this);
        view.findViewById(R.id.leave_btn).setOnClickListener((View.OnClickListener) this);
        return view;
    }

    private void signOut() {
        LoginPageFragment.getmGoogleSignInClient().signOut();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel_leave_btn:
                signOut();
                NavHostFragment.findNavController(this).navigate(R.id.action_logoutDialogFragment_to_loginPageFragment);
                this.dismiss();
                break;
            case R.id.leave_btn:
                this.dismiss();
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