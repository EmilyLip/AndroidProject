package com.example.plantarium;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.example.plantarium.MainActivity;
import java.io.Serializable;

public class PlantsView extends AppCompatActivity implements View.OnClickListener, Serializable {

    private static final String TAG = "Plants Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plants_view);

        TextView greetingMessage = findViewById(R.id.greeting_message);
        //greetingMessage.setText("שלום" + userAuth.mGoogleSignInClient.toString());
        Button logoutButton = findViewById(R.id.logout_button);
        findViewById(R.id.logout_button).setOnClickListener((View.OnClickListener) this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // ...
            case R.id.logout_button:
                signOut();
                setContentView(R.layout.activity_main);
                break;
            // ...
        }
    }

    private void signOut() {
        MainActivity.getmGoogleSignInClient().signOut();
    }
}