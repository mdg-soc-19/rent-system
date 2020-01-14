package com.android.example.rentalapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {

    private ImageView pImage;
    private TextView pName, pCategory, pRentalPrice, pSecurityCost, pAvailabilityDuration, pDescrpition, oName, oEmail, oPhone,
                     oCity;
    private ImageButton ConnectWithOwner_whatsapp, ConnectWithOwner_call;
    private String productID;
    private DocumentReference productReference, ownerReference;
    private static final int REQUEST_PHONE_CALL = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        pName = findViewById(R.id.product_name_tv);
        pCategory = findViewById(R.id.category_tv);
        pRentalPrice = findViewById(R.id.rental_price_tv);
        pSecurityCost = findViewById(R.id.security_cost_tv);
        pAvailabilityDuration = findViewById(R.id.availability_duration_tv);
        pDescrpition = findViewById(R.id.description_tv);
        pImage = findViewById(R.id.product_iv);

        ConnectWithOwner_whatsapp = findViewById(R.id.oConnect_whatsapp_bn);
        ConnectWithOwner_call = findViewById(R.id.oConnect_call_bn);

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
                        pCategory.setText("Category - " + productObject.getCategory());
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
                                        final User owner = documentSnapshot.toObject(User.class);
                                        oName.setText("Name : " + owner.getName());
                                        oCity.setText("City : " + owner.getCity());
                                        oEmail.setText("Email : " + owner.getEmail());
                                        oPhone.setText("Contact Number : " + owner.getContactNo());

                                        ConnectWithOwner_whatsapp.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                openWhatsappContact(owner.getContactNo());
                                            }
                                        });

                                        ConnectWithOwner_call.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                setConnectWithOwner_call(owner.getContactNo());
                                            }
                                        });
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

    void openWhatsappContact(String number) {
        Uri uri = Uri.parse("smsto:" + number);
        Intent i = new Intent(Intent.ACTION_SENDTO, uri);
        i.setPackage("com.whatsapp");
        startActivity(Intent.createChooser(i, ""));
    }

    void setConnectWithOwner_call(String phone) {
        if (ContextCompat.checkSelfPermission(DetailsActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(DetailsActivity.this, new String[]{Manifest.permission.CALL_PHONE},REQUEST_PHONE_CALL);
        }
        else {
            Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
            startActivity(callIntent);
        }

        }
    }

