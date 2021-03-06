package com.example.plantarium.HomePageFragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.plantarium.Models.DBModels.UserModel;
import com.example.plantarium.Models.User;
import com.example.plantarium.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import java.io.Serializable;

public class LoginPageFragment extends Fragment  implements View.OnClickListener , Serializable {

    @SuppressLint("StaticFieldLeak")
    public static GoogleSignInClient mGoogleSignInClient;
    public static GoogleSignInAccount account;
    private static final String TAG = "Main Fragment";
    private static final int RC_SIGN_IN = 7;
    UserModel userModel = new UserModel();

    SignInButton signInButton;
    View view;
    User user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         view =  inflater.inflate(R.layout.fragment_login_page, container, false);

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(getContext(), gso);
        account = GoogleSignIn.getLastSignedInAccount(view.getContext());
        signInButton = (SignInButton) view.findViewById(R.id.login_button);

        TextView textView = (TextView) signInButton.getChildAt(0);
        textView.setText("התחברות עם חשבון גוגל");

        view.findViewById(R.id.login_button).setOnClickListener((View.OnClickListener) this);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        updateUI(account);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_button:
                // Check for existing Google Sign In account, if the user is already signed in
                // the GoogleSignInAccount will be non-null.
                if (account == null){
                    signIn();
                    account = GoogleSignIn.getLastSignedInAccount(v.getContext());
                } else {
                    updateUI(account);
                }
                break;
        }
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }

    private void updateUI(Object o) {
        if (o instanceof GoogleSignInAccount) {
            String imageUrl = (account.getPhotoUrl() != null) ? account.getPhotoUrl().toString() : "";
            user = new User(account.getEmail(), account.getDisplayName(), imageUrl, account.getId());
            userModel.updateUser(user, new UserModel.AddUserListener() {
                @Override
                public void onComplete() {
                    Log.i(TAG, "user logged in");
                }
            });

            NavController nav = Navigation.findNavController(view);
            if (nav.getCurrentDestination().getId() == R.id.loginPageFragment) {
                nav.navigate(R.id.action_loginPage_to_placesList);
            }
        }
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item=menu.findItem(R.id.go_to_places);
        if(item!=null)
            item.setVisible(false);
    }

    public static GoogleSignInClient getmGoogleSignInClient(){
        return  mGoogleSignInClient;
    }
    public static GoogleSignInAccount getAccount(){
        return  account;
    }
}