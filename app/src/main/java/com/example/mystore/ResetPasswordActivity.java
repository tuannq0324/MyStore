package com.example.mystore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class ResetPasswordActivity extends AppCompatActivity {
    private EditText oldPassword, newPassword, rePassword;
    private TextView ok;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest_password);
        oldPassword = findViewById(R.id.oldPassword);
        newPassword = findViewById(R.id.newPassword);
        rePassword = findViewById(R.id.rePassword);
        ok = findViewById(R.id.btn_ok);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickChangPassWord();
            }
        });

    }

    private void onClickChangPassWord() {
        String oldPassWord = oldPassword.getText().toString().trim();
        String newPassWord = newPassword.getText().toString().trim();
        String rePassWord = rePassword.getText().toString().trim();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        assert user != null;
        String currentUser = user.getUid();
        DatabaseReference mRef = FirebaseDatabase.getInstance("https://fir-4d080-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference().child("Users");
        DatabaseReference ref = mRef.child(currentUser);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                password = snapshot.child("password").getValue() + "";
                Log.d("password", password);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        if (TextUtils.isEmpty(oldPassWord)) {
            Toast.makeText(getApplicationContext(), "Old-password Không được để trống!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(newPassWord)) {
            Toast.makeText(getApplicationContext(), "New-password Không được để trống!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(rePassWord)) {
            Toast.makeText(getApplicationContext(), "Re-password Không được để trống!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!oldPassWord.equalsIgnoreCase(password)) {
            Toast.makeText(getApplicationContext(), "Old-password không trùng khớp", Toast.LENGTH_SHORT).show();
            return;
        }
        if (newPassWord.length() < 6) {
            Toast.makeText(getApplicationContext(), "New-password quá ngắn yêu cầu nhiều 6 ký tự!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (newPassWord.equalsIgnoreCase(password)) {
            Toast.makeText(getApplicationContext(), "New-password không được giống với old-password", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!newPassWord.equalsIgnoreCase(rePassWord)) {
            Toast.makeText(getApplicationContext(), "Re-password không trùng với new-password", Toast.LENGTH_SHORT).show();
        } else {
            user.updatePassword(newPassWord)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                ref.child("password").setValue(newPassWord);
                                Toast.makeText(getApplicationContext(), "User password updated", Toast.LENGTH_SHORT).show();
                                oldPassword.setText("");
                                newPassword.setText("");
                                rePassword.setText("");
                            } else {
                                Toast.makeText(getApplicationContext(), "Change password failed " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
    }

}