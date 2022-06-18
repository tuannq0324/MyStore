package com.example.mystore.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mystore.listener.HomeListener;
import com.example.mystore.model.Banner;
import com.example.mystore.model.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeServices {
    DatabaseReference ref =FirebaseDatabase
            .getInstance("https://fir-4d080-default-rtdb.asia-southeast1.firebasedatabase.app")
            .getReference();

    public void getAllProductInFirebase(final HomeListener listener) {
        final ArrayList<Product> list = new ArrayList<>();
        ref.child("Product")
                .orderByChild("productName")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        //Parse dataSnapshot
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            Product products = ds.getValue(Product.class);
                            assert products != null;
                            products.setProductID(ds.getKey());
                            if (products.getStatus() == null) {
                                list.add(products);
                            }
                        }
                        listener.getAllProductSuccess(list);

                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        listener.getProductFailure(databaseError.toException());
                    }
                });

    }


}
