package edu.huflit.shopDT;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import edu.huflit.shopDT.Activity.CreateOrUpdateItemActivity;
import edu.huflit.shopDT.Adapter.QuanLySanPhamAdapter;
import edu.huflit.shopDT.Adapter.ShopAdapter;

public class QuanLySanPhamActivity extends AppCompatActivity implements QuanLySanPhamAdapter.Listener {

    private QuanLySanPhamAdapter shopAdapter;
    private DatabaseReference database;
    RecyclerView rvItem;
    ArrayList<Phones> phoneList;

    ImageView btnBack, btnAdd;
    boolean isCreate = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_san_pham);

        rvItem = findViewById(R.id.rvItem);
        btnBack = findViewById(R.id.backprevious);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isCreate = true;
                Intent intent = new Intent(QuanLySanPhamActivity.this, CreateOrUpdateItemActivity.class );
                intent.putExtra("isCreate", isCreate);
                startActivity(intent);
            }
        });

        database = FirebaseDatabase.getInstance().getReference("List_Phone");
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                phoneList = new ArrayList<>();
                List<String> itemIdList = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Phones phones = dataSnapshot.getValue(Phones.class);
                    phoneList.add(phones);
                    itemIdList.add(dataSnapshot.getKey());

                }
//                shopAdapter.notifyDataSetChanged();
                shopAdapter= new QuanLySanPhamAdapter(phoneList, QuanLySanPhamActivity.this, itemIdList, QuanLySanPhamActivity.this);

                rvItem.setAdapter(shopAdapter);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(QuanLySanPhamActivity.this,LinearLayoutManager.VERTICAL,false);
        rvItem.setLayoutManager(linearLayoutManager1);
    }

    @Override
    public void clickRoute(Phones item) {
        isCreate = false;
        Intent intent = new Intent(QuanLySanPhamActivity.this, CreateOrUpdateItemActivity.class);
        intent.putExtra("key", String.valueOf(item.getId()));
        intent.putExtra("isCreate", isCreate);
        startActivity(intent);
    }
}