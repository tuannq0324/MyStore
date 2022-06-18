package com.example.mystore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;

import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.example.mystore.adapter.ProductAdapter;
import com.example.mystore.listener.HomeListener;
import com.example.mystore.model.Banner;
import com.example.mystore.model.Product;
import com.example.mystore.services.HomeServices;
import com.example.mystore.viewholder.HomeViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.paperdb.Paper;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FirebaseDatabase database;
    FirebaseAuth auth;
    FirebaseAuth.AuthStateListener authListener;
    FirebaseRecyclerAdapter<Product, ProductAdapter.ProductViewHolder> adapter;
    DatabaseReference product;
    TextView tvName_header, tvMail_header;
    RecyclerView recyclerMenu;
    SwipeRefreshLayout swipeLayoutHome;
    String TAG = "HomeActivity";
    Toolbar toolbar;
    BottomNavigationView bottomNavigationView;

    HashMap<String, String> imageList;
    SliderLayout sliderLayout;

    HomeServices homeServices;
    ProductAdapter productAdapter;

    String name, email;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Home");

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);

        tvName_header = header.findViewById(R.id.tvName_header);
        tvMail_header = header.findViewById(R.id.tvMail_header);
        recyclerMenu = findViewById(R.id.recycler_menu);

        swipeLayoutHome = findViewById(R.id.swipe_layout_home);
        swipeLayoutHome.setOnRefreshListener(() -> new Handler().postDelayed(() -> {
            getAllProduct();
            swipeLayoutHome.setRefreshing(false);
            swipeLayoutHome.setColorSchemeColors(
                    getResources().getColor(android.R.color.holo_blue_bright),
                    getResources().getColor(android.R.color.holo_green_light),
                    getResources().getColor(android.R.color.holo_orange_light),
                    getResources().getColor(android.R.color.holo_red_light));
        }, 1500));

        bottomNavigationView = findViewById(R.id.nav_bottom);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                Intent intent = new Intent(HomeActivity.this, HomeActivity.class);
                startActivity(intent);
                return true;
            }
            if (id == R.id.nav_cart) {
                Intent intent = new Intent(HomeActivity.this, CartActivity.class);
                startActivity(intent);
                return true;
            } else if (id == R.id.nav_settings) {

                Intent intent = new Intent(HomeActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
            return false;
        });

//        sliderLayout = findViewById(R.id.slider_layout);

        ConfigRecyclerView(recyclerMenu);
        getAllProduct();

        Paper.init(this);



        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        auth = FirebaseAuth.getInstance();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String currentUser = user.getUid();
            DatabaseReference mRef = FirebaseDatabase.getInstance("https://fir-4d080-default-rtdb.asia-southeast1.firebasedatabase.app/")
                    .getReference().child("Users");
            DatabaseReference ref = mRef.child(currentUser);
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    name = snapshot.child("fullName").getValue() + "";
                    email = snapshot.child("email").getValue() + "";

                    tvName_header.setText(name);
                    tvMail_header.setText(email);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }



        authListener = firebaseAuth -> {
            if (user == null) {
                // user auth state is changed - user is null
                // launch login activity
                startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                finish();
            }
        };
    }

    //left_nav
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_cart) {
            Intent intent = new Intent(HomeActivity.this, CartActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_change_pass) {
            Intent intent = new Intent(HomeActivity.this, ResetPasswordActivity.class);
            startActivity(intent);


        } else if (id == R.id.nav_settings) {

            Intent intent = new Intent(HomeActivity.this, SettingsActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_sign_out) {
            signOut();
        }

        return false;
    }

    public void signOut() {
        auth.signOut();
        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    private void getAllProduct() {
        try {
            homeServices = new HomeServices();
            homeServices.getAllProductInFirebase(new HomeListener() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void getAllProductSuccess(ArrayList<Product> productList) {
                    productAdapter = new ProductAdapter(productList, HomeActivity.this);
                    recyclerMenu.setAdapter(productAdapter);
                    productAdapter.notifyDataSetChanged();
                }

                @Override
                public void getProductFailure(Exception ex) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ConfigRecyclerView(RecyclerView view) {
        RecyclerView.LayoutManager manager = new GridLayoutManager(this, 2);
        view.setHasFixedSize(false);
        view.setItemAnimator(new DefaultItemAnimator());
        view.setLayoutManager(manager);
    }

    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }
    }
}