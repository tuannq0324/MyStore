package com.example.mystore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.mystore.model.Product;
import com.example.mystore.utils.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class ProductDetailActivity extends AppCompatActivity {
    ImageView productImageView;
    CollapsingToolbarLayout collapsingToolbar;
    TextView tvProductName;
    TextView tvProductPrice;
    ElegantNumberButton quantityButton;
    TextView tvProductDesc;

    FirebaseDatabase database;
    DatabaseReference product_ref;
    DatabaseReference ratingTbl;
    Product product;
    FloatingActionButton ratingButtonFab,cartButtonFab;
    RatingBar ratingBar;
    Button showCommentsButton;
    NestedScrollView nestedScrollView;
    BottomNavigationView bottomNavigationView;
    String productID;
    HashMap<String, String> hashMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_product_detail);

        productImageView = findViewById(R.id.product_image_view);
        collapsingToolbar = findViewById(R.id.collapsing_toolbar);
        tvProductName = findViewById(R.id.tv_productName);
        tvProductPrice = findViewById(R.id.tv_productPrice);
        tvProductDesc = findViewById(R.id.tv_productDesc);
        cartButtonFab = findViewById(R.id.cart_button_fab);
        quantityButton = findViewById(R.id.quantity_button);
        bottomNavigationView = findViewById(R.id.nav_bottom);

        productID = getIntent().getStringExtra("ID");
        String quantity = quantityButton.getNumber();

        getProductDetails(productID);
        productImageView.setLayoutParams(new CollapsingToolbarLayout.LayoutParams(600, 600));

        cartButtonFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCart();
            }
        });

        bottomNavigationView = findViewById(R.id.nav_bottom);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                Intent intent = new Intent(ProductDetailActivity.this, HomeActivity.class);
                startActivity(intent);
                return true;
            }
            if (id == R.id.nav_cart) {
                Intent intent = new Intent(ProductDetailActivity.this, CartActivity.class);
                startActivity(intent);
                return true;
            } else if (id == R.id.nav_settings) {

                Intent intent = new Intent(ProductDetailActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
            if (id == R.id.nav_change_pass) {
                Intent intent = new Intent(ProductDetailActivity.this, ResetPasswordActivity.class);
                startActivity(intent);
                return true;
            }
            return false;
        });
    }

    private void addToCart() {
        FirebaseUser auth = FirebaseAuth.getInstance().getCurrentUser();
        final DatabaseReference ref = FirebaseDatabase.getInstance("https://fir-4d080-default-rtdb.asia-southeast1.firebasedatabase.app")
                .getReference().child("Cart List");

        final HashMap<String,Object> cartMap = new HashMap<>();
        cartMap.put("ProductID",productID);
        cartMap.put("imgThumnal",hashMap);
        cartMap.put("productName",tvProductName.getText().toString());
        cartMap.put("priceProduct",tvProductPrice.getText().toString());
        cartMap.put("quantity",quantityButton.getNumber());

        ref.child("User").child(auth.getUid()).child("Products").child(productID)
                .updateChildren(cartMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"Added to Cart!",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ProductDetailActivity.this,CartActivity.class);
                            startActivity(intent);
                        }
                    }
                });
    }

    private void getProductDetails(String productID){
        DatabaseReference ref = FirebaseDatabase.getInstance("https://fir-4d080-default-rtdb.asia-southeast1.firebasedatabase.app")
                .getReference().child("Product");

        ref.child(productID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    Product product = snapshot.getValue(Product.class);

                    assert product != null;
                    tvProductName.setText(product.getProductName());
                    tvProductPrice.setText(product.getPriceProduct());
                    tvProductDesc.setText(product.getDescription());
                    hashMap = product.getImgThumnal();
                    Map.Entry<String, String> entry = hashMap.entrySet().iterator().next();
                    String value = entry.getValue();
                    Glide.with(ProductDetailActivity.this).load(value).into(productImageView);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}