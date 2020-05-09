package in.ac.iitr.mdg.rentalapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HelpActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        bottomNavigationView = findViewById(R.id.nav_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);

        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {

                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    switch (menuItem.getItemId()) {

                        case R.id.nav_home:
                            startActivity(new Intent(HelpActivity.this, MainActivity.class));
                            finish();
                            break;

                        case R.id.nav_profile:
                            startActivity(new Intent(HelpActivity.this, MyProfileActivity.class));
                            finish();
                            break;

                        case R.id.nav_help:
                            break;

                        case R.id.nav_my_uploads:
                            startActivity(new Intent(HelpActivity.this, MyUploadsActivity.class));
                            finish();
                            break;

                        case R.id.nav_exit:
                            finishAffinity();
                            break;
                    }

                    return false;
                }
            };

}
