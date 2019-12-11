    package com.android.example.rentalapp;

    import androidx.appcompat.app.AppCompatActivity;

    import android.content.Intent;
    import android.nfc.Tag;
    import android.os.Bundle;
    import android.text.TextUtils;
    import android.util.Log;
    import android.view.View;
    import android.widget.Button;
    import android.widget.EditText;
    import android.widget.TextView;
    import android.widget.Toast;

    import androidx.annotation.NonNull;

    import com.google.android.gms.tasks.OnCompleteListener;
    import com.google.android.gms.tasks.OnSuccessListener;
    import com.google.android.gms.tasks.Task;
    import com.google.firebase.auth.AuthResult;
    import com.google.firebase.auth.FirebaseAuth;
    import com.google.firebase.firestore.DocumentReference;
    import com.google.firebase.firestore.FirebaseFirestore;

    import java.util.HashMap;
    import java.util.Map;

    public class SignupActivity extends AppCompatActivity {

        private FirebaseAuth auth;
        private  EditText username, email, password, city, contactNo;
        private Button signup;
        FirebaseFirestore fStore;
        String userID;


         @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_signup);

            auth = FirebaseAuth.getInstance();
            fStore = FirebaseFirestore.getInstance();
            username = (EditText) findViewById(R.id.username_et);
            email = (EditText) findViewById(R.id.email_et);
            password = (EditText) findViewById(R.id.password_et);
            city = (EditText) findViewById(R.id.city_et);
            contactNo = (EditText) findViewById(R.id.phone_et);


            signup = (Button) findViewById(R.id.signup_bn);

            signup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                         final String Username = username.getText().toString().trim();
                         final String Email = email.getText().toString().trim();
                         String Password = password.getText().toString().trim();
                         final String City = city.getText().toString().trim();
                         final String Phone = contactNo.getText().toString().trim();

                     if (TextUtils.isEmpty(Username)) {
                        Toast.makeText(getApplicationContext(), "Enter Username!", Toast.LENGTH_SHORT).show();
                        username.requestFocus();
                        return;
                    }

                    if (TextUtils.isEmpty(Email)) {
                        Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                        email.requestFocus();
                        return;
                    }

                    if (TextUtils.isEmpty(Password)) {
                        Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                        password.requestFocus();
                        return;
                    }

                    if (password.length() < 6) {
                        Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    }

                    if (TextUtils.isEmpty(City)) {
                        Toast.makeText(getApplicationContext(), "Enter city!", Toast.LENGTH_SHORT).show();
                        city.requestFocus();
                        return;
                    }

                    if (TextUtils.isEmpty(Phone)) {
                        Toast.makeText(getApplicationContext(), "Enter contact no.!", Toast.LENGTH_SHORT).show();
                        contactNo.requestFocus();
                        return;
                    }


                    //create user
                    auth.createUserWithEmailAndPassword(Email, Password)
                      .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {

                                        if (task.isSuccessful()) {
                                            Toast.makeText(SignupActivity.this, "Account created successfully", Toast.LENGTH_SHORT).show();
                                            DocumentReference documentReference = fStore.collection("users").document(userID);
                                            userID = auth.getCurrentUser().getUid();
                                            Map<String, Object> user = new HashMap<>();
                                            user.put("Name", Username);
                                            user.put("Email", Email);
                                            user.put("City", City);
                                            user.put("Contact No.", Phone);
                                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {

                                                }
                                            });
                                            startActivity(new Intent(SignupActivity.this, MainActivity .class));
                                        }
                                        else {
                                            Toast.makeText(SignupActivity.this, "Authentication failed." + task.getException(),
                                                    Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                });
                    }
                });
            }

        }



