package com.example.plantarium.Models;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;

import java.io.Serializable;

public class UserAuth implements Serializable  {

    public transient GoogleSignInClient mGoogleSignInClient;
    public transient GoogleSignInAccount account;

    public UserAuth(GoogleSignInClient newGoogleSignInClient, GoogleSignInAccount newAccount){
        this.mGoogleSignInClient = newGoogleSignInClient;
        this.account = newAccount;
    }
}
