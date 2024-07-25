package edu.huflit.shopDT.Activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import edu.huflit.shopDT.Adapter.ShopAdapter;
import edu.huflit.shopDT.Phones;
import edu.huflit.shopDT.R;

public class HomeActivity extends AppCompatActivity {
    BottomNavigationView bnv;
    private ViewPager bannerViewPager;
    private ShopAdapter shopAdapter;
    private DatabaseReference database;
    RecyclerView recyclerView1,recyclerView2;
    ArrayList<Phones> phoneList;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_ac);

        ImageSlider imageSlider = findViewById(R.id.banner);
        ArrayList<SlideModel> sliderModels = new ArrayList<SlideModel>();
        sliderModels.add(new SlideModel("C:/Users/phucu/Downloads/Demo_ShopDT/Demo_ShopDT/Demo/app/src/main/Assets/images/mo-ban-galaxyz6-series-sliding.webp","", ScaleTypes.FIT));
        sliderModels.add(new SlideModel("https://cdn2.cellphones.com.vn/insecure/rs:fill:690:300/q:90/plain/https://dashboard.cellphones.com.vn/storage/mo-ban-galaxyz6-series-sliding.png","", ScaleTypes.FIT));
        sliderModels.add(new SlideModel("https://cdn2.cellphones.com.vn/insecure/rs:fill:690:300/q:90/plain/https://dashboard.cellphones.com.vn/storage/oppo-reno12-banner-sliding-5-7-2024.jpg","", ScaleTypes.FIT));
        imageSlider.setImageList(sliderModels,ScaleTypes.FIT);
        bnv = findViewById(R.id.bottom_menu);
        bnv.setSelectedItemId(R.id.mnhome);
        bnv.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.mnhome:
                    return true;
                case R.id.mnliked:
                    startActivity(new Intent(getApplicationContext(),FavoriteActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;
                case R.id.mnsearch:
                    startActivity(new Intent(getApplicationContext(), SearchActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;
                case R.id.logout:
                    startActivity(new Intent(getApplicationContext(), HistoryActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;
            }
            return false;
        });

        database = FirebaseDatabase.getInstance().getReference("List_Phone");

        recyclerView1 = findViewById(R.id.categoryRecyclerView);
        recyclerView2 = findViewById(R.id.PhonesRecyclerView2);

        phoneList = new ArrayList<>();

        shopAdapter= new ShopAdapter(phoneList, HomeActivity.this);

        recyclerView1.setAdapter(shopAdapter);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerView1.setLayoutManager(linearLayoutManager1);

        recyclerView2.setAdapter(shopAdapter);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerView2.setLayoutManager(linearLayoutManager2);
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){

                    Phones phones = dataSnapshot.getValue(Phones.class);
                    phoneList.add(phones);


                }
                shopAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logoutmenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout:
                logoutUser();
                break;
        }
        return super.onOptionsItemSelected(item);
    }




    private void logoutUser() {
        Intent intent=new Intent(HomeActivity.this,LoginActivity.class);
        startActivity(intent);
        Toast.makeText(this, "Bạn đã đăng xuất thành công", Toast.LENGTH_SHORT).show();
    }
    public  static void addtoFAV(Context context, String phoneID){
        long timeStamp = System.currentTimeMillis();
        String time = String.valueOf(timeStamp);
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("phoneID", ""+phoneID);
//        hashMap.put("timestamp", ""+timeStamp);

        //save to db
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("User");
        ref.child(FirebaseAuth.getInstance().getUid()).child("Favorites").child(phoneID)
                .setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(context, "Added to your favorited list",Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Failed to add to your favorited list",Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public static void removedFromFAV(Context context,String phoneID){
        long timeStamp = System.currentTimeMillis();
        String time = String.valueOf(timeStamp);
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("phoneID", ""+phoneID);
//        hashMap.put("timestamp", ""+timeStamp);

        //remove from db
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("User");
        ref.child(FirebaseAuth.getInstance().getUid()).child("Favorites").child(phoneID)
                .removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        //Toast.makeText(context, "Removed",Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //Toast.makeText(context, "Failed to add to your favorited list",Toast.LENGTH_SHORT).show();
                    }
                });
    }
}