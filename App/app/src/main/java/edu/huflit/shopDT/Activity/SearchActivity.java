package edu.huflit.shopDT.Activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import edu.huflit.shopDT.Adapter.SearchAdapter;
import edu.huflit.shopDT.Phones;
import edu.huflit.shopDT.R;

public class SearchActivity extends AppCompatActivity {
    private ArrayList<Phones> phoneList;
    SearchView searchView;
    private SearchAdapter shopAdapter;
    ValueEventListener eventListener;
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    BottomNavigationView bnv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_ac);
        bnv = findViewById(R.id.bottom_menu);
        bnv.setSelectedItemId(R.id.mnsearch);
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
                    return true;
                case R.id.logout:
                    startActivity(new Intent(getApplicationContext(), HistoryActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;
            }
            return false;
        });


        recyclerView = findViewById(R.id.SearchRycyclerView);
        searchView = findViewById(R.id.searchView);
        searchView.clearFocus();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(SearchActivity.this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        AlertDialog.Builder builder = new AlertDialog.Builder(SearchActivity.this);
        builder.setCancelable(false);
        AlertDialog dialog = builder.create();
        dialog.show();
        phoneList = new ArrayList<>();
        shopAdapter = new SearchAdapter( phoneList,SearchActivity.this);
        recyclerView.setAdapter(shopAdapter);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager1);
        databaseReference = FirebaseDatabase.getInstance().getReference("List_Phone");
        dialog.show();
        eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                phoneList.clear();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Phones phones = dataSnapshot.getValue(Phones.class);
                    phoneList.add(phones);
                }
                shopAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                dialog.dismiss();
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                searchList(newText);
                return true;
            }
        });
    }
    public void searchList(String text){
        ArrayList<Phones> searchList = new ArrayList<>();
        for (Phones dataClass: phoneList){
            if (dataClass.getName().toLowerCase().contains(text.toLowerCase())){
                searchList.add(dataClass);
            }
        }
        shopAdapter.searchDataList(searchList);
    }
}