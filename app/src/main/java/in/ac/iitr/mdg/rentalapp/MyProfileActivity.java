package in.ac.iitr.mdg.rentalapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import javax.annotation.Nullable;

import in.ac.iitr.mdg.rentalapp.Classes.User;

public class MyProfileActivity extends AppCompatActivity {

    private EditText myName, myEmail, myPhone, myCity;
    BottomNavigationView bottomNavigationView;
    Button saveChanges;
    ProgressBar progressBar;
    FirebaseAuth auth;
    FirebaseFirestore db;
    User user = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        myName = findViewById(R.id.owner_name_et);
        myEmail = findViewById(R.id.owner_email_et);
        myPhone = findViewById(R.id.owner_phone_et);
        myCity = findViewById(R.id.owner_city_et);
        saveChanges = findViewById(R.id.save_changes_bn);
        progressBar = findViewById(R.id.progressBar);

        bottomNavigationView = findViewById(R.id.nav_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);

        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);

        auth = FirebaseAuth.getInstance();
        String userId = auth.getCurrentUser().getUid();
        db = FirebaseFirestore.getInstance();
        db.collection("users").document(userId).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

                if (e != null) {
                    Log.d("ERROR", e.getMessage());
                    return;
                }

                if (documentSnapshot != null && documentSnapshot.exists()) {

                    User user = documentSnapshot.toObject(User.class);

                    myName.setText(user.getName());
                    myEmail.setText(user.getEmail());
                    myPhone.setText(user.getContactNo());
                    myCity.setText(user.getCity());
                }
            }
        });

        saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                SaveAndUpload();
            }
        });
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {

                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    switch (menuItem.getItemId()) {

                        case R.id.nav_home:
                            startActivity(new Intent(MyProfileActivity.this, MainActivity.class));
                            finish();
                            break;

                        case R.id.nav_profile:
                            break;

                        case R.id.nav_help:
                            startActivity(new Intent(MyProfileActivity.this, HelpActivity.class));
                            finish();
                            break;

                        case R.id.nav_my_uploads:
                            startActivity(new Intent(MyProfileActivity.this, MyUploadsActivity.class));
                            finish();
                            break;

                        case R.id.nav_exit:
                            finishAffinity();
                            break;
                    }

                    return false;
                }
            };


    private void SaveAndUpload(){

        auth = FirebaseAuth.getInstance();
        String userId = auth.getCurrentUser().getUid();
        db = FirebaseFirestore.getInstance();



        user.setName(myName.getText().toString().trim());
        user.setEmail(myEmail.getText().toString().trim());
        user.setContactNo(myPhone.getText().toString().trim());
        user.setCity(myCity.getText().toString().trim());
        db.collection("users").document(userId).update(
                "Name", user.getName(),
                "Email", user.getEmail(),
                "ContactNo", user.getContactNo(),
                "City", user.getCity()

        ).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                progressBar.setVisibility(View.GONE);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                Toast.makeText(MyProfileActivity.this, "Profile Updated", Toast.LENGTH_SHORT).show();
            }
        });
    }

}


