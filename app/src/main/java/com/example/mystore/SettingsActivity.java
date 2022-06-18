package com.example.mystore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;
import java.util.Set;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingsActivity extends AppCompatActivity {
    private CircleImageView profileImageView;
    private TextView tvEmail,tvName,tvClose,tvSave,tvProfilechange;

    FirebaseAuth auth;
    FirebaseDatabase firebaseDatabase;

    private Uri imgUri;
    private final String myUrl = "";
    private StorageTask storageTask;
    private StorageReference storageReference;
    private final String checker = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        profileImageView = findViewById(R.id.profile_image);
        tvEmail = findViewById(R.id.settings_email);
        tvName = findViewById(R.id.settings_full_name);
        tvClose = findViewById(R.id.btn_close_settings);
        tvSave = findViewById(R.id.btn_update_settings);
        tvProfilechange = findViewById(R.id.profile_image_change_btn);

        tvClose.setOnClickListener(v -> {
            Intent intent = new Intent(SettingsActivity.this,HomeActivity.class);
            startActivity(intent);
        });

        auth = FirebaseAuth.getInstance();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null) {
            String currentUser = user.getUid();
            DatabaseReference mRef = FirebaseDatabase.getInstance("https://fir-4d080-default-rtdb.asia-southeast1.firebasedatabase.app/")
                    .getReference().child("Users");
            DatabaseReference ref = mRef.child(currentUser);
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String name = snapshot.child("fullName").getValue() + "";
                    String email = snapshot.child("email").getValue() + "";

                    tvName.setText(name);
                    tvEmail.setText(email);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

    }
}