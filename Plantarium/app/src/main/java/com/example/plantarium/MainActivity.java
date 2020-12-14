package com.example.plantarium;

import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.SignInButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SignInButton signInButton = (SignInButton) findViewById(R.id.login_button);

        TextView textView = (TextView) signInButton.getChildAt(0);
        textView.setText("התחברות עם חשבון גוגל");
    }

    @Override
    public void onClick(View view) {
        Log.d("TAG","button click");
    }
}