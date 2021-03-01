package com.example.plantarium;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Main Activity";
    NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFFFFF")));
        actionBar.setTitle(Html.fromHtml("<font color='#008290'>Plantarium</font>"));
        navController = Navigation.findNavController(this, R.id.fragment_navhost);
        //NavigationUI.setupActionBarWithNavController(this, navController);
        NavigationView navigationView = findViewById(R.id.nav_controller_view_tag);
        //NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu ,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.logout_manu_button){
            Log.w(TAG, R.id.logout_manu_button + " ");
            navController.navigate(R.id.logoutDialogFragment);
           return true;
        }
        return NavigationUI.onNavDestinationSelected(item,navController);
    }

    public void onDialogLogoutClose(){
        navController.navigate(R.id.action_logoutDialogFragment_to_loginPageFragment);
    }
}