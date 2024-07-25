package edu.huflit.shopDT.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import edu.huflit.shopDT.R;

public class HistoryActivity extends AppCompatActivity {
    BottomNavigationView bnv;
    ImageView imageView;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.histories_ac);
        bnv = findViewById(R.id.bottom_menu);
        bnv.setSelectedItemId(R.id.logout);
        bnv.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.mnhome:
                    startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
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
                    return true;
            }
            return false;
        });

//        imageView = findViewById(R.id.deleteHistory);
//
//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(context, LoginActivity.class);
//                context.startActivity(i);
//            }
//        });
    }
}