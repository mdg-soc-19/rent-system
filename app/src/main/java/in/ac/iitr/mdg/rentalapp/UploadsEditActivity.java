package in.ac.iitr.mdg.rentalapp;

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

import javax.annotation.Nullable;

import in.ac.iitr.mdg.rentalapp.Classes.product;

public class UploadsEditActivity extends AppCompatActivity {

    private ImageView pImage;
    private EditText pName, pCategory, pRentalPrice, pSecurityCost, pAvailabilityDuration, pDescrpition;
    private String productID;
    private Button SaveChanges;
    private DocumentReference productReference;
    product productItem = new product();

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
                            Log.i("Document", "exists");
                            product productObject = documentSnapshot.toObject(product.class);

                            Picasso.get().load(productObject.getImage()).fit().into(pImage);
                            pName.setText(productObject.getProductName());
                            pCategory.setText( productObject.getCategory());
                            pRentalPrice.setText( productObject.getRentalPrice());
                            pSecurityCost.setText( productObject.getSecurityCost());
                            pAvailabilityDuration.setText( productObject.getAvailabilityDuration() );
                            pDescrpition.setText( productObject.getDescription());
                        }
                    }


                }); // Add Snapshot listener ends here

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

                productItem.setProductName(pName.getText().toString().trim());
                productItem.setAvailabilityDuration(pAvailabilityDuration.getText().toString().trim());
                productItem.setCategory(pCategory.getText().toString().trim());
                productItem.setDescription(pDescrpition.getText().toString().trim());
                productItem.setRentalPrice(pRentalPrice.getText().toString().trim());
                productItem.setSecurityCost(pSecurityCost.getText().toString().trim());
        Log.i("Rental Price", productItem.getRentalPrice());
        productReference.update(
                        "rentalPrice", productItem.getRentalPrice(),
                        "securityCost", productItem.getSecurityCost(),
                        "productName", productItem.getProductName(),
                        "category", productItem.getCategory(),
                        "description", productItem.getDescription(),
                        "availabilityDuration" , productItem.getAvailabilityDuration()
        ).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(UploadsEditActivity.this, "Product Updated", Toast.LENGTH_SHORT).show();
            }
        });

   }
}
