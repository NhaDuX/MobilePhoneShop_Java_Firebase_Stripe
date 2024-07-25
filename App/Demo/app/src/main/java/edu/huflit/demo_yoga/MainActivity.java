package com.android.shopdt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import com.android.shopdt.MenuFragment.AccountFragment;
import com.android.shopdt.MenuFragment.HomeFragment;
import com.android.shopdt.MenuFragment.LikedFragment;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bnv;
    HomeFragment homeFragment = new HomeFragment();
    //SearchFragment searchFragment = new SearchFragment();
    LikedFragment likedFragment = new LikedFragment();
    AccountFragment accountFragment= new AccountFragment();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        setContentView(R.layout.activity_main);

        bnv = findViewById(R.id.bottom_menu);

        getSupportFragmentManager().beginTransaction().replace(R.id.container,homeFragment).commit();

        bnv.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected( MenuItem item) {
                switch (item.getItemId()){
                    case R.id.mnhome:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,homeFragment).commit();
                        return true;
                    case R.id.mnliked:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,likedFragment).commit();
                        return true;
                    case R.id.mnsearch:
                        startActivity(new Intent(getApplicationContext(),SearchActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.mnaccount:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,accountFragment).commit();
                        return true;
                }
                return false;
            }
        });

    }
}