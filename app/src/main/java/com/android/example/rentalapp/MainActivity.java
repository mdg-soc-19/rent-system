package com.android.example.rentalapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth auth;
    private Button logout, GiveOnRent, TakeOnRent;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    Toolbar toolbar;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);

        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        getSupportActionBar();
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            // User is logged in
            Toast.makeText(MainActivity.this, "You're now logged in. Welcome to rentalApp!", Toast.LENGTH_SHORT).show();
        }

        GiveOnRent =  findViewById(R.id.giveOnRent_bn);
        TakeOnRent =  findViewById(R.id.takeOnRent_bn);

        GiveOnRent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferences = getSharedPreferences("buttonChoice", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit().putBoolean("ChosenButton", true);
                editor.apply();
                startActivity(new Intent(MainActivity.this, CategoriesActivity.class));
            }
        });

        TakeOnRent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferences = getSharedPreferences("buttonChoice", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit().putBoolean("ChosenButton", false);
                editor.apply();
                startActivity(new Intent(MainActivity.this, CategoriesActivity.class));
            }
        });

    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){

            case R.id.nav_profile:
                startActivity(new Intent(MainActivity.this, MyProfileActivity.class));
                break;

            case R.id.nav_help:
                startActivity(new Intent(MainActivity.this, HelpActivity.class));
                break;

//            case R.id.nav_settings:
//                Toast.makeText(MainActivity.this, "Settings Selected", Toast.LENGTH_SHORT).show();
//                break;

            case R.id.nav_signout:
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                auth.signOut();
                finish();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}


