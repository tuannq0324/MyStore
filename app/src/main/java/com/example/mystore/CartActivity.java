package com.example.mystore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mystore.model.Cart;
import com.example.mystore.utils.Utils;
import com.example.mystore.viewholder.CartViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class CartActivity extends AppCompatActivity {
    private RecyclerView rv;
    private Button btn_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        rv = findViewById(R.id.cart_rv);
        btn_next = findViewById(R.id.btn_next);
        rv.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(layoutManager);
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CartActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final DatabaseReference cart_ref = FirebaseDatabase.getInstance("https://fir-4d080-default-rtdb.asia-southeast1.firebasedatabase.app")
                .getReference().child("Cart List");
        assert user != null;
        FirebaseRecyclerOptions<Cart> options =
                new FirebaseRecyclerOptions.Builder<Cart>()
                .setQuery(cart_ref.child("User")
                .child(user.getUid()).child("Products"),Cart.class)
                .build();

        FirebaseRecyclerAdapter<Cart, CartViewHolder> adapter
                = new FirebaseRecyclerAdapter<Cart, CartViewHolder>(options) {
            @SuppressLint("SetTextI18n")
            @Override
            protected void onBindViewHolder(@NonNull CartViewHolder cartViewHolder, int i, @NonNull Cart cart) {
                String name = cart.getProductName();
                int price = Integer.parseInt(cart.getPriceProduct());
                int quantity = Integer.parseInt(cart.getQuantity());
                String price1 = Utils.formatNumberToMoney((long) ((long) price * quantity)) + " đ";

                cartViewHolder.tvProductName.setText(name);
                cartViewHolder.tvProductPrice.setText("Giá: "+price1);
                cartViewHolder.tvProductQuantity.setText("Số lượng = "+quantity);
                HashMap<String, String> hashMap = cart.getImgThumnal();
                Map.Entry<String, String> entry = hashMap.entrySet().iterator().next();
                String value = entry.getValue();
                Glide.with(CartActivity.this).load(value).into(cartViewHolder.ivProduct);

                cartViewHolder.ivDelete.setOnClickListener(v -> cart_ref.child("User")
                        .child(user.getUid())
                        .child("Products")
                        .child(cart.getProductID())
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                                    snapshot.getRef().removeValue();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Log.e("TAG","Error",error.toException());
                            }
                        }));
            }

            @NonNull
            @Override
            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_items_layout,parent,false);

                return new CartViewHolder(view);
            }
        };

        rv.setAdapter(adapter);
        adapter.startListening();
    }
}