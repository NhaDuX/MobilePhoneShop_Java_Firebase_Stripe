package edu.huflit.shopDT.Activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import edu.huflit.shopDT.Adapter.FAVAdapter;
import edu.huflit.shopDT.Phones;
import edu.huflit.shopDT.R;

public class FavoriteActivity extends AppCompatActivity {
    BottomNavigationView bnv;
    FAVAdapter favAdapter;
    RecyclerView recyclerView;
    ArrayList<Phones> phoneList;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favorite_ac);

        bnv = findViewById(R.id.bottom_menu);
        bnv.setSelectedItemId(R.id.mnliked);
        bnv.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.mnhome:
                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;
                case R.id.mnliked:
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
        loadFavPhone();
        recyclerView = findViewById(R.id.rvFavorite);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager1);
        favAdapter = new FAVAdapter(phoneList, FavoriteActivity.this);
        recyclerView.setAdapter(favAdapter);
    }

    private void loadFavPhone() {
        phoneList = new ArrayList<>();
        firebaseAuth = FirebaseAuth.getInstance();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("User");
        ref.child(firebaseAuth.getUid()).child("Favorites")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        phoneList.clear();
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            //lay phoneID o day, may cai con lai lay o fav adapter
                            String phoneID = "" + ds.child("phoneID").getValue();

                            Phones phones = new Phones();
                            phones.setId(phoneID);

                            phoneList.add(phones);
                        }

                       // favAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}