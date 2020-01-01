package com.android.example.rentalapp;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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
    private DocumentReference documentReference;

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
        documentReference = FirebaseFirestore.getInstance().collection("products").document(productID);

        documentReference.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                ProductItem productItem = documentSnapshot.toObject(ProductItem.class);

                    Picasso.get().load(productItem.getImage()).fit().into(pImage); //here I get the null pointer exception
                    pName.setText(productItem.getProductName());
                    pRentalPrice.setText(productItem.getRentalPrice());
                    pSecurityCost.setText(productItem.getSecurityCost());
                    pAvailabilityDuration.setText(productItem.getAvailabilityDuration());
                    pDescrpition.setText(productItem.getDescription());

            }
        });
    }

}
