package com.example.mystore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mystore.model.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {
    EditText inputFullname,inputEmail, inputPassword;
    private TextView btnSignIn, btnSignUp, btnResetPassword;
    private ProgressBar progressBar;
    private FirebaseDatabase firebaseDatabase;
    DatabaseReference user_ref;
    private FirebaseAuth auth;
    private final String USER = "user";
    private Users users;
    private final String TAG = "SignUpActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        auth = FirebaseAuth.getInstance();

        btnSignIn = findViewById(R.id.sign_in_button);
        btnSignUp = findViewById(R.id.sign_up_button);
        inputFullname = findViewById(R.id.fullname);
        inputEmail = findViewById(R.id.email);
        inputPassword = findViewById(R.id.password);
        progressBar = findViewById(R.id.progressBar);

        auth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance("https://fir-4d080-default-rtdb.asia-southeast1.firebasedatabase.app");
        user_ref = firebaseDatabase.getReference().child("Users");

        btnSignUp.setOnClickListener(v -> {
            String full_name = inputFullname.getText().toString().trim();
            String email = inputEmail.getText().toString().trim();
            String password = inputPassword.getText().toString().trim();

            if (TextUtils.isEmpty(full_name)) {
                Toast.makeText(getApplicationContext(), "Name Không được để trống!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(email)) {
                Toast.makeText(getApplicationContext(), "Email Không được để trống!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(password)) {
                Toast.makeText(getApplicationContext(), "Password Không được để trống!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (password.length() < 6) {
                Toast.makeText(getApplicationContext(), "Password quá ngắn yêu cầu nhiều 6 ký tự!", Toast.LENGTH_SHORT).show();
            }
            else {
                progressBar.setVisibility(View.VISIBLE);
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignUpActivity.this, task -> {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success");
                                Toast.makeText(SignUpActivity.this, "Đăng ký thành công",
                                        Toast.LENGTH_LONG).show();
                                FirebaseUser user = auth.getCurrentUser();
                                updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user
                                Log.w(TAG, "createUserWithEmail:failure"+ task.getException());
                                Toast.makeText(SignUpActivity.this, "Authentication failed: "+task.getException().getMessage(),
                                        Toast.LENGTH_LONG).show();
                                updateUI(null);
                            }
                        });
            }
        });

        btnSignIn.setOnClickListener(v -> {
            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
            startActivity(intent);
        });

    }

    private void updateUI(FirebaseUser user) {
//        String keyID = user_ref.push().getKey();
        if(user != null){
            users = new Users(user.getUid(),inputEmail.getText().toString().trim(),inputPassword.getText().toString().trim(),inputFullname.getText().toString().trim());
            user_ref.child(user.getUid()).setValue(users);

            Intent intent = new Intent(SignUpActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }
}