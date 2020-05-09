package in.ac.iitr.mdg.rentalapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    Button GiveOnRent, TakeOnRent;
    BottomNavigationView bottomNavigationView;
    TextView SignOut;
    Toolbar toolbar;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        SignOut = findViewById(R.id.signout_tv);


        bottomNavigationView = findViewById(R.id.nav_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);

        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);

        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            // User is logged in
            Toast.makeText(MainActivity.this, "You're now logged in. Welcome to rentalApp!", Toast.LENGTH_SHORT).show();
        }

        SignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                FirebaseAuth.getInstance().signOut();
                finish();
            }
        });

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


    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {

            case R.id.nav_home:
                break;

            case R.id.nav_profile:
                startActivity(new Intent(MainActivity.this, MyProfileActivity.class));
                break;

            case R.id.nav_help:
                startActivity(new Intent(MainActivity.this, HelpActivity.class));
                break;

            case R.id.nav_my_uploads:
                startActivity(new Intent(MainActivity.this, MyUploadsActivity.class));
                break;

            case R.id.nav_exit:
                finishAffinity();
                break;
        }

        return false;
    }
    };
}


