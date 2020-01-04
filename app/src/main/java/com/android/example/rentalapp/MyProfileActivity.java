package com.android.example.rentalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MyProfileActivity extends AppCompatActivity {

    private TextView myName, myEmail, myPhone, myCity;
    FirebaseAuth auth;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        myName = findViewById(R.id.owner_name_tv);
        myEmail = findViewById(R.id.owner_email_tv);
        myPhone = findViewById(R.id.owner_phone_tv);
        myCity = findViewById(R.id.owner_city_tv);

        auth = FirebaseAuth.getInstance();
        String userId = auth.getCurrentUser().getUid();
        db = FirebaseFirestore.getInstance();
        db.collection("users").document(userId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                User user = documentSnapshot.toObject(User.class);

                myName.setText(user.getName());
                myEmail.setText(user.getEmail());
                myPhone.setText(user.getContactNo());
                myCity.setText(user.getCity());
            }
        });
    }
}
