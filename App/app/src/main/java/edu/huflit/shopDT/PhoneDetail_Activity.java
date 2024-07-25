package edu.huflit.shopDT;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.andremion.counterfab.CounterFab;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.ArrayList;

import edu.huflit.shopDT.Activity.CartActivity;
import edu.huflit.shopDT.Activity.HomeActivity;
import edu.huflit.shopDT.Activity.MainActivity;
import edu.huflit.shopDT.Database.model.Cart;
import edu.huflit.shopDT.Database.model.Phone;

public class PhoneDetail_Activity extends AppCompatActivity {
    TextView  mvideoname, mtvdetails, mprice, quantity;
    ImageView back,mimg;
    ImageButton favorite;
    Button addToCart;

    Cart cart;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference cartRef;
    String currentUserId, countItemInCart;
    private CounterFab btnCart;
    ArrayList<String> arrayList = null;
    Phones phone;
    ImageButton btnPlus, btnMinus;
    int totalQuantity = 1;
    int priceint, fullprice;
    public String phoneID;
    Boolean isImMyFAV = false;
    String itemId;
    FirebaseAuth firebaseAuth;

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videoplay);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        currentUserId = user.getUid();

        firebaseDatabase = FirebaseDatabase.getInstance();
        cartRef = firebaseDatabase.getReference("Shopping Cart");
        btnCart = findViewById(R.id.itemFabCart);
        btnMinus = findViewById(R.id.btnMinus);
        btnPlus = findViewById(R.id.btnPlus);
        quantity = findViewById(R.id.quantity);
        itemId = getIntent().getStringExtra("itemId");

        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PhoneDetail_Activity.this, CartActivity.class);
                startActivity(intent);
            }
        });
        //ánh xạ chi tiết của video


        mvideoname = findViewById(R.id.phone_NAME);
        mtvdetails = findViewById(R.id.tvDetails);
        mprice = findViewById(R.id.phone_Price);
        back = findViewById(R.id.backIV);
        favorite = findViewById(R.id.favoriteIV);
        mimg = findViewById(R.id.img);
        addToCart = findViewById(R.id.addToCart);


        // nhận intent từ VideolistActivity và gán vào ID
        Intent getRetrivedIntent = getIntent();
//        Phone phone = getRetrivedIntent.getSerializableExtra("phone", Phone.class);
        phone = (Phones) getRetrivedIntent.getExtras().getSerializable("phone");
        mvideoname.setText(phone.getName());
        mtvdetails.setText(phone.getDe());
        mprice.setText(String.valueOf(phone.getPrice()));
        String numericValue = String.valueOf(phone.getPrice()).replaceAll("[^0-9]", ""); // Removes all non-numeric characters
        fullprice=Integer.parseInt(numericValue);
        priceint=Integer.parseInt(numericValue);
        String imgURL = phone.getImgURL();
//
        Intent getRetrivedIntent1 = getIntent();
        Phones phone1 = (Phones) getRetrivedIntent.getExtras().getSerializable("phone");
        phoneID = phone1.getId();
        checkIsFAV();




        Glide.with(this)
                .load(imgURL)
                .into(mimg);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick ( View view ) {
                addToCart();
            }
        });

        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isImMyFAV){
                    HomeActivity.removedFromFAV(PhoneDetail_Activity.this,phoneID);
                }
                else {
                    HomeActivity.addtoFAV(PhoneDetail_Activity.this,phoneID);
                }
            }
        });
        getItemQuantity();
    }
    public void getItemQuantity() {
        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                totalQuantity++;
                quantity.setText(String.valueOf(totalQuantity));
                fullprice = (int) (priceint*(Integer.parseInt(quantity.getText().toString())));
            }
        });

        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (totalQuantity>1){
                    totalQuantity--;
                    quantity.setText(String.valueOf(totalQuantity));
                    fullprice = (int) (priceint*(Integer.parseInt(quantity.getText().toString())));
                }
            }
        });

        btnMinus.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                totalQuantity=1;
                quantity.setText(String.valueOf(totalQuantity));
                return false;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        getCartQuantity();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getCartQuantity();
    }

    public void getCartQuantity() {
        cartRef.child(currentUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    arrayList.add(dataSnapshot.getKey());
                }
                countItemInCart = Integer.toString(arrayList.size());
                int itemInCart = Integer.parseInt(countItemInCart);
                btnCart.setCount(itemInCart);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void addToCart() {
        cart = new Cart(phone.getImgURL(),phone.getName(),phone.getPrice(),quantity.getText().toString(),String.valueOf(fullprice));

        cartRef.child(currentUserId).child(itemId).setValue(cart).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getApplicationContext(),"Added to cart",Toast.LENGTH_SHORT).show();
                getCartQuantity();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"Can't add to cart",Toast.LENGTH_SHORT).show();
                getCartQuantity();
            }
        });

    }
    public void checkIsFAV(){
        firebaseAuth = FirebaseAuth.getInstance();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("User");
        databaseReference.child(firebaseAuth.getUid()).child("Favorites").child(phoneID)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        isImMyFAV = snapshot.exists();
                        if (isImMyFAV){
                            favorite.setBackground(getDrawable(R.drawable.baseline_favorite_24));

                        }
                        else
                        {
                            favorite.setBackground(getDrawable(R.drawable.baseline_favorite_border_24));
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}