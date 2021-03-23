package com.example.plantarium;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Main Activity";
    NavController navController;
    BottomNavigationView bottomNavigationView;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Configuration configuration = getResources().getConfiguration();
        configuration.setLayoutDirection(new Locale("he"));
        getResources().updateConfiguration(configuration, getResources().getDisplayMetrics());

        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFFFFF")));
        actionBar.setTitle(Html.fromHtml("<font color='#008290'>Plantarium</font>"));
        navController = Navigation.findNavController(this, R.id.fragment_navhost);

        NavigationView navigationView = findViewById(R.id.nav_controller_view_tag);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case  R.id.members_menu_item:
                        // TODO: change and find way to pass args (maybe global place)
                        navController.navigate(R.id.placeMembersFragmnet);
                        return true;
                    case  R.id.plants_menu_item:
                        navController.navigate(R.id.placePlantsFragment);
                        return true;
                    case  R.id.prizes_menu_item:
                        // TODO: change and find way to pass args (maybe global place)
                        navController.navigate(R.id.placesListFragment);
                        return true;
                    default:
                        return false;
                }
            }
        });
        visibilityNavElements(navController);

    }

    private void visibilityNavElements(NavController navController) {
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller,
                                             @NonNull NavDestination destination, @Nullable Bundle arguments) {
                if(destination.getId() == R.id.placePlantsFragment ||
                   destination.getId() == R.id.placeMembersFragmnet ||
                   destination.getId() == R.id.placePlantsFragment ) {
                    bottomNavigationView.setVisibility(View.VISIBLE);
                } else {
                    bottomNavigationView.setVisibility(View.GONE);
                }
            }
        });
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
        switch (item.getItemId()){
            case  R.id.logout_manu_button:
                navController.navigate(R.id.logoutDialogFragment);
                return true;
            case  R.id.go_to_places:
                navController.navigate(R.id.placesListFragment);
                return true;
            default:
                return NavigationUI.onNavDestinationSelected(item,navController);
        }
    }
}