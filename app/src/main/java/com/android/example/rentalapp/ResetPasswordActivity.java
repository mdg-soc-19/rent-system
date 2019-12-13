    package com.android.example.rentalapp;

    import androidx.annotation.NonNull;
    import androidx.appcompat.app.AppCompatActivity;


    import android.os.Bundle;
    import android.text.TextUtils;

    import android.view.View;

    import android.widget.Button;
    import android.widget.EditText;
    import android.widget.Toast;

    import com.google.android.gms.tasks.OnCompleteListener;
    import com.google.android.gms.tasks.Task;
    import com.google.firebase.auth.FirebaseAuth;


    public class ResetPasswordActivity extends AppCompatActivity {

        private EditText email;
        private Button resetpassword, back;
        private FirebaseAuth auth;




        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_reset_password);


            email = (EditText) findViewById(R.id.email_et);
            resetpassword = (Button)findViewById(R.id.resetPassword_bn);
            back = (Button) findViewById(R.id.back_bn);

            auth = FirebaseAuth.getInstance();

            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

            resetpassword.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String Email = email.getText().toString().trim();

                    if (TextUtils.isEmpty(Email)) {
                        Toast.makeText(ResetPasswordActivity.this, "Enter your registered email id", Toast.LENGTH_SHORT).show();
                        return;
                    }


                    auth.sendPasswordResetEmail(Email)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(ResetPasswordActivity.this, "We have sent you instructions to reset your password!", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(ResetPasswordActivity.this, "Failed to send reset email!", Toast.LENGTH_SHORT).show();
                                    }


                                }
                            });
                }
            });
        }

    }

