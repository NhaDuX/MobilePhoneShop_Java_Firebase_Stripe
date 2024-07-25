package edu.huflit.shopDT.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.andremion.counterfab.CounterFab;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import edu.huflit.shopDT.MenuFragment.FavoriteFragment;
import edu.huflit.shopDT.MenuFragment.HistoriesFragment;
import edu.huflit.shopDT.MenuFragment.HomePageFragment;
import edu.huflit.shopDT.MenuFragment.ProfileFragment;
import edu.huflit.shopDT.MenuFragment.SearchFragment;
import edu.huflit.shopDT.PhoneDetail_Activity;
import edu.huflit.shopDT.R;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bnv;
    FrameLayout content;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference cartRef;
    private CounterFab btnCart;
    ArrayList<String> arrayList = null;
    private String currentUserId,countItemInCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bnv = findViewById(R.id.bottom_menu);
        content = findViewById(R.id.container);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        currentUserId = user.getUid();

        firebaseDatabase = FirebaseDatabase.getInstance();
        cartRef = firebaseDatabase.getReference("Shopping Cart");
        btnCart = findViewById(R.id.itemFabCart);

        getCartQuantity();
        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });

        String flag = getIntent().getStringExtra("flag");
        if (flag==null){
            display(R.id.mnhome);
            bnv.setSelectedItemId(R.id.mnhome);
        }else if (flag.equals("home")){
            display(R.id.mnhome);
            bnv.setSelectedItemId(R.id.mnhome);
        } else if (flag.equals("history")) {
            display(R.id.logout);
            bnv.setSelectedItemId(R.id.logout);
        } else if (flag.equals("profile")) {
            display(R.id.mnprofile);
            bnv.setSelectedItemId(R.id.mnprofile);
        }

        bnv.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                display(item.getItemId());
                return true;
            }
        });

//        getSupportFragmentManager().beginTransaction().replace(R.id.container,homeFragment).commit();
        /*bnv.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected( MenuItem item) {
                switch (item.getItemId()){
                    case R.id.mnhome:

                        return true;
                    case R.id.mnliked:
                        startActivity(new Intent(getApplicationContext(), FavoriteActivity.class));
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
            }
        });*/
    }
    void display(int id) {
        Fragment fragment = null;
        switch (id) {
            case R.id.mnhome:
                fragment = new HomePageFragment();
                break;
            case R.id.mnliked:
                fragment = new FavoriteFragment();
                break;
            case R.id.mnsearch:
                fragment = new SearchFragment();
                break;
            case R.id.logout:
                fragment = new HistoriesFragment();
                break;
            case R.id.mnprofile:
                fragment = new ProfileFragment();
                break;
        }
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container, fragment);
        ft.commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getCartQuantity();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getCartQuantity();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private void getCartQuantity(){
        cartRef.child(currentUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    arrayList.add(dataSnapshot.getKey());
                }
                countItemInCart= Integer.toString(arrayList.size());
                int itemInCart = Integer.parseInt(countItemInCart);
                btnCart.setCount(itemInCart);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}