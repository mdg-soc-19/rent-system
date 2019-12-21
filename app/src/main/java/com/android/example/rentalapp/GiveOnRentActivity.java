    package com.android.example.rentalapp;

    import android.app.ProgressDialog;
    import android.content.Intent;
    import android.graphics.Bitmap;
    import android.net.Uri;
    import android.os.Bundle;
    import android.provider.MediaStore;
    import android.text.TextUtils;
    import android.view.View;
    import android.widget.Button;
    import android.widget.EditText;
    import android.widget.ImageView;
    import android.widget.Toast;

    import androidx.annotation.NonNull;
    import androidx.appcompat.app.AppCompatActivity;

    import com.google.android.gms.tasks.OnFailureListener;
    import com.google.android.gms.tasks.OnSuccessListener;
    import com.google.firebase.auth.FirebaseAuth;
    import com.google.firebase.firestore.DocumentReference;
    import com.google.firebase.firestore.FirebaseFirestore;
    import com.google.firebase.firestore.FirebaseFirestoreSettings;
    import com.google.firebase.storage.FirebaseStorage;
    import com.google.firebase.storage.StorageReference;
    import com.google.firebase.storage.UploadTask;

    import java.io.IOException;
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

    fStore = FirebaseFirestore.getInstance();

    FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
            .setTimestampsInSnapshotsEnabled(true)
            .build();
    fStore.setFirestoreSettings(settings);

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
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
            productImage.setImageBitmap(bitmap);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    }

    private void uploadFile() {
    //if there is a file to upload
    if (filePath != null) {
        //displaying a progress dialog while upload is going on
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading");
        progressDialog.show();


        final String ProductName = productName.getText().toString().trim();
        final String RentalPrice = rentalPrice.getText().toString().trim();
        final String SecurityCost = securityCost.getText().toString().trim();
        final String AvailabilityDuration = availabilityDuration.getText().toString().trim();
        final String Description = description.getText().toString().trim();

        if (TextUtils.isEmpty(ProductName)) {
            Toast.makeText(getApplicationContext(), "Enter the name of product!", Toast.LENGTH_SHORT).show();
            productName.requestFocus();
        }

        if (TextUtils.isEmpty(RentalPrice)) {
            Toast.makeText(getApplicationContext(), "Enter Rental Price!", Toast.LENGTH_SHORT).show();
            rentalPrice.requestFocus();
        }

        if (TextUtils.isEmpty(SecurityCost)) {
            Toast.makeText(getApplicationContext(), "Enter Security Cost!", Toast.LENGTH_SHORT).show();
            securityCost.requestFocus();
        }

        if (TextUtils.isEmpty(AvailabilityDuration)) {
            Toast.makeText(getApplicationContext(), "Enter the duration of availability of product !", Toast.LENGTH_SHORT).show();
            availabilityDuration.requestFocus();
        }

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
                                Uri imageurl = uri;

                                DocumentReference documentReference = fStore.collection("products").document();

                                Map<String, Object> product = new HashMap<>();
                                product.put("Product Name", ProductName);
                                product.put("Image", imageurl.toString());
                                product.put("Rental Price", RentalPrice);
                                product.put("Security Cost", SecurityCost);
                                product.put("Availability duration (days)", AvailabilityDuration);
                                product.put("Description", Description);
                                product.put("User ID", FirebaseAuth.getInstance().getCurrentUser().getUid());
                                product.put("Category", CategoryName);
                                documentReference.set(product).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        //if the upload is successfull
                                        //hiding the progress dialog
                                        progressDialog.dismiss();

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
                                        progressDialog.dismiss();

                                        //and displaying error message
                                        Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                });
                        }
                });
    }
    //if there is not any file
    else {
        Toast.makeText(getApplicationContext(), "File not found", Toast.LENGTH_SHORT).show();
    }

    }
    }