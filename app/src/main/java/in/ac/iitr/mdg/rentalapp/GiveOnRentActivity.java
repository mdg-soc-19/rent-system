package in.ac.iitr.mdg.rentalapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GiveOnRentActivity extends AppCompatActivity {

        private static final int PICK_IMAGE_REQUEST = 1;

        private Button buttonChoose, buttonUpload;
        private ImageView productImage;
        private EditText productName, rentalPrice, securityCost, availabilityDuration, description;
        private Uri filePath;
        private StorageReference storageReference;
        private FirebaseFirestore fStore;
        private ProgressBar progressBar;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_give_on_rent);

            buttonChoose = findViewById(R.id.choose_bn);
            buttonUpload = findViewById(R.id.upload_bn);
            productImage = findViewById(R.id.product_iv);
            productName = findViewById(R.id.product_name_et);
            rentalPrice = findViewById(R.id.rental_price_et);
            securityCost = findViewById(R.id.security_cost_et);
            availabilityDuration = findViewById(R.id.availability_duration_et);
            description = findViewById(R.id.description_et);
            progressBar = findViewById(R.id.progressBar);

            fStore = FirebaseFirestore.getInstance();
            storageReference = FirebaseStorage.getInstance().getReference();

            //attaching listener
            buttonChoose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showFileChooser();
                }
            });
            buttonUpload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    uploadFile();
                }
            });


        }


        //method to show file chooser
        private void showFileChooser() {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
        }

        //handling the image chooser activity
        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == PICK_IMAGE_REQUEST && resultCode != RESULT_CANCELED && data != null && data.getData() != null) {
                filePath = data.getData();
                Picasso.get().load(filePath).into(productImage);
            }
        }

        private void uploadFile() {
            //if there is a file to upload

            final String ProductName = productName.getText().toString().trim();
            final String RentalPrice = rentalPrice.getText().toString().trim();
            final String SecurityCost = securityCost.getText().toString().trim();
            final String AvailabilityDuration = availabilityDuration.getText().toString().trim();
            final String Description = description.getText().toString().trim();

            if (filePath == null) {
                Toast.makeText(getApplicationContext(), "Image is mandatory", Toast.LENGTH_SHORT).show();
            }
            else if (TextUtils.isEmpty(ProductName)) {
                Toast.makeText(getApplicationContext(), "Enter the name of product!", Toast.LENGTH_SHORT).show();
            }
            else if (TextUtils.isEmpty(RentalPrice) || RentalPrice.equalsIgnoreCase("0")) {
                Toast.makeText(getApplicationContext(), "Enter a valid Rental Price!", Toast.LENGTH_SHORT).show();
            }
            else if (TextUtils.isEmpty(SecurityCost)) {
                Toast.makeText(getApplicationContext(), "Enter Security Cost!", Toast.LENGTH_SHORT).show();
            }
            else if (TextUtils.isEmpty(AvailabilityDuration) || AvailabilityDuration.equalsIgnoreCase("0")) {
                Toast.makeText(getApplicationContext(), "Enter the duration of availability of product !", Toast.LENGTH_SHORT).show();
            }
            else if(TextUtils.isEmpty(Description)){
                Toast.makeText(getApplicationContext(), "Description unavailable", Toast.LENGTH_SHORT).show();
            }
            else {
                progressBar.setVisibility(View.VISIBLE);
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);


                final String CategoryName = getIntent().getExtras().get("Category").toString();
                final StorageReference myRef;
                final String imageID = UUID.randomUUID().toString();
                myRef = storageReference.child("images/" + imageID);
                myRef.putFile(filePath)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                myRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {

                                        DocumentReference documentReference = fStore.collection("products").document();

                                        Map<String, Object> product = new HashMap<>();
                                        product.put("productName", ProductName);
                                        product.put("image", uri.toString());
                                        product.put("rentalPrice", RentalPrice);
                                        product.put("securityCost", SecurityCost);
                                        product.put("availabilityDuration", AvailabilityDuration);
                                        product.put("description", Description);
                                        product.put("userID", FirebaseAuth.getInstance().getCurrentUser().getUid());
                                        product.put("category", CategoryName);
                                        documentReference.set(product).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                //if the upload is successfull
                                                //hiding the progress dialog
                                                //progressDialog.dismiss();

                                                progressBar.setVisibility(View.GONE);
                                                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                                                //and displaying a success toast
                                                Toast.makeText(getApplicationContext(), "File Uploaded ", Toast.LENGTH_LONG).show();
                                            }
                                        });
                                    }

                                })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception exception) {
                                                //if the upload is not successfull
                                                //hiding the progress dialog
                                                //progressDialog.dismiss();

                                                //and displaying error message
                                                Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                                            }
                                        });
                            }
                        });


            }
        }
    }