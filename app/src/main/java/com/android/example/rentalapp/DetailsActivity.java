package com.android.example.rentalapp;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {

    private ImageView pImage;
    private TextView pName, pRentalPrice, pSecurityCost, pAvailabilityDuration, pDescrpition, oName, oEmail, oPhone,
                     oCity;
    private String productID;
    private DocumentReference productReference, ownerReference;
    private boolean zoomOut =  false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        pName = findViewById(R.id.product_name_tv);
        pRentalPrice = findViewById(R.id.rental_price_tv);
        pSecurityCost = findViewById(R.id.security_cost_tv);
        pAvailabilityDuration = findViewById(R.id.availability_duration_tv);
        pDescrpition = findViewById(R.id.description_tv);
        pImage = findViewById(R.id.product_iv);

        oName = findViewById(R.id.owner_name_tv);
        oEmail = findViewById(R.id.owner_email_tv);
        oPhone = findViewById(R.id.owner_phone_tv);
        oCity = findViewById(R.id.owner_city_tv);

        productID = getIntent().getStringExtra("Product ID"); // getting thr product ID and using it to retrieve product details
        productReference = FirebaseFirestore.getInstance().collection("products").document(productID);

        productReference.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        final product productObject = documentSnapshot.toObject(product.class);

                        Picasso.get().load(productObject.getImage()).fit().into(pImage);
                        pName.setText(productObject.getProductName());
                        pRentalPrice.setText("Rental Price - " + "\u20B9" + productObject.getRentalPrice());
                        pSecurityCost.setText("Security Cost - " + "\u20B9" + productObject.getSecurityCost());
                        pAvailabilityDuration.setText("Availability duration - " + productObject.getAvailabilityDuration() + " days");
                        pDescrpition.setText("Description - " + productObject.getDescription());

                        String ownerID = productObject.getUserID();

                        ownerReference = FirebaseFirestore.getInstance().collection("users").document(ownerID);
                        ownerReference.get()
                                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                        User owner = documentSnapshot.toObject(User.class);
                                        oName.setText("Name : " + owner.getName());
                                        oCity.setText("City : " + owner.getCity());
                                        oEmail.setText("Email : " + owner.getEmail());
                                        oPhone.setText("Contact Number : " + owner.getContactNo());
                                    }
                                });
                    }
               })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(DetailsActivity.this, "Product details not available", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
