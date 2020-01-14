package com.android.example.rentalapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

public class UploadsEditActivity extends AppCompatActivity {

    private ImageView pImage;
    private EditText pName, pCategory, pRentalPrice, pSecurityCost, pAvailabilityDuration, pDescrpition;
    private String productID;
    private Button SaveChanges;
    private DocumentReference productReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploads_edit);

        pName = findViewById(R.id.product_name_et);
        pCategory = findViewById(R.id.category_et);
        pRentalPrice = findViewById(R.id.rental_price_et);
        pSecurityCost = findViewById(R.id.security_cost_et);
        pAvailabilityDuration = findViewById(R.id.availability_duration_et);
        pDescrpition = findViewById(R.id.description_et);
        pImage = findViewById(R.id.product_iv);
        SaveChanges = findViewById(R.id.save_changes_bn);

                productID = getIntent().getStringExtra("Product ID"); // getting thr product ID and using it to retrieve product details
                productReference = FirebaseFirestore.getInstance().collection("products").document(productID);
                Log.i("doc", productID);

                productReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

                        if (e != null) {
                            Log.d("ERROR", e.getMessage());
                            return;
                        }
                        if (documentSnapshot != null && documentSnapshot.exists()) {

                            product productObject = documentSnapshot.toObject(product.class);

                            Picasso.get().load(productObject.getImage()).fit().into(pImage);
                            pName.setText(productObject.getProductName());
                            pCategory.setText("Category - " + productObject.getCategory());
                            pRentalPrice.setText("Rental Price - " + "\u20B9" + productObject.getRentalPrice());
                            pSecurityCost.setText("Security Cost - " + "\u20B9" + productObject.getSecurityCost());
                            pAvailabilityDuration.setText("Availability duration - " + productObject.getAvailabilityDuration() + " days");
                            pDescrpition.setText("Description - " + productObject.getDescription());
                        }
                    }


                });




        SaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editAndSave();
            }
        });
    }


    private void editAndSave(){

        productID = getIntent().getStringExtra("Product ID"); // getting the product ID and using it to retrieve product details
        productReference = FirebaseFirestore.getInstance().collection("products").document(productID);

        productReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                Map<String, Object> product = new HashMap<>();
                product.put("productName", pName.getText().toString().trim());
                product.put("rentalPrice", pRentalPrice.getText().toString().trim());
                product.put("securityCost", pSecurityCost.getText().toString().trim());
                product.put("availabilityDuration", pAvailabilityDuration.getText().toString().trim());
                product.put("description",pDescrpition.getText().toString().trim());
                product.put("category", pCategory.getText().toString().trim());
                productReference.set(product).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //if the upload is successfull
                        //we display a success toast
                        Toast.makeText(getApplicationContext(), "File Uploaded ", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

   }
}
