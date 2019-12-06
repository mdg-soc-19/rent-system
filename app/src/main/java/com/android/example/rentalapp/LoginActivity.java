    package com.android.example.rentalapp;

    import androidx.appcompat.app.AppCompatActivity;

    import com.google.firebase.auth.FirebaseAuth;
    import com.google.firebase.auth.AuthResult;
    import com.google.android.gms.tasks.Task;
    import com.google.android.gms.tasks.OnCompleteListener;
    import androidx.annotation.NonNull;

    import android.content.Intent;
    import android.os.Bundle;
    import android.text.TextUtils;
    import android.view.View;
    import android.widget.Button;
    import android.widget.EditText;
    import android.widget.TextView;
    import android.widget.Toast;

    public class LoginActivity extends AppCompatActivity {

        private FirebaseAuth auth;
        private EditText email, password;
        private TextView signup, forgotPassword;
        private Button login;

            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);


                    auth = FirebaseAuth.getInstance();

                    if (auth.getCurrentUser() != null) {
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    }

                    setContentView(R.layout.activity_login);

                    login = (Button) findViewById(R.id.login_bn);
                    signup = (TextView) findViewById(R.id.signup_tv);
                    forgotPassword = (TextView) findViewById(R.id.forgotPassword_tv);
                    email = (EditText) findViewById(R.id.email_et);
                    password = (EditText) findViewById(R.id.password_et);

                    auth = FirebaseAuth.getInstance();

                    signup.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(LoginActivity.this, SignupActivity.class));
                    }

                     });

                    forgotPassword.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
                        }
                    });

                    login.setOnClickListener(new View.OnClickListener(){
                        public void onClick(View v) {
                            String Email = email.getText().toString();
                            final String Password = password.getText().toString();

                            if (TextUtils.isEmpty(Email)) {
                                Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            if (TextUtils.isEmpty(Password)) {
                                Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            //user authentication

                            auth.signInWithEmailAndPassword(Email, Password)
                                    .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>(){
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            // If sign in fails, display a message to the user. If sign in succeeds
                                            // the auth state listener will be notified and logic to handle the
                                            // signed in user can be handled in the listener.
                                            if (!task.isSuccessful()) {
                                                // there was an error
                                                if (password.length() < 6) {
                                                    password.setError(getString(R.string.minimum_password));
                                                } else {
                                                    Toast.makeText(LoginActivity.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                                                }
                                            } else {
                                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                        }
                                    });
                            }
                    });

          }
        }

