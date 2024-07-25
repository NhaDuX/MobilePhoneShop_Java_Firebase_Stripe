
package edu.huflit.shopDT;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import edu.huflit.shopDT.Adapter.ShopAdapter;

public class PhoneListActivity extends AppCompatActivity  {
    RecyclerView recyclerView;
    ArrayList<Phones> phoneList;
    ShopAdapter videolistAdapter;

    DatabaseReference database;
    FloatingActionButton nosort,sortaz,sortza;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_list);
        database = FirebaseDatabase.getInstance().getReference("List_Phone");

        recyclerView = findViewById(R.id.RViewVList);

        phoneList = new ArrayList<>();

        videolistAdapter= new ShopAdapter(phoneList, PhoneListActivity.this);

        recyclerView.setAdapter(videolistAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){

                    Phones phones = dataSnapshot.getValue(Phones.class);
                    phoneList.add(phones);


                }
                videolistAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // Back imageview to intent form videolist to mainactivity
        ImageView back = findViewById(R.id.backIV);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        //sort
        nosort = findViewById(R.id.noSort);
        sortaz = findViewById(R.id.sortaz);
        sortza = findViewById(R.id.sortza);

//        nosort.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                videoDBHelper = new VideoDBHelper(PhoneListActivity.this);
//                recyclerView = findViewById(R.id.RViewVList);
//                phone = videoDBHelper.getVideos();
//                videolistAdapter= new ShopAdapter1(phoneList, PhoneListActivity.this);
//                recyclerView.setAdapter(videolistAdapter);
//                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(PhoneListActivity.this);
//                recyclerView.setLayoutManager(linearLayoutManager);
//            }
//        });
//        sortaz.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                phoneList = videoDBHelper.azSortbyNAME();
//                videolistAdapter = new ShopAdapter1(phoneList,PhoneListActivity.this);
//                LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(PhoneListActivity.this, LinearLayoutManager.VERTICAL, false);
//                recyclerView.setLayoutManager(linearLayoutManager2);
//                recyclerView.setAdapter(videolistAdapter);
//            }
//        });
//        sortza.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                phoneList = videoDBHelper.zaSortbyNAME();
//                videolistAdapter = new ShopAdapter1(phoneList,PhoneListActivity.this);
//                LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(PhoneListActivity.this, LinearLayoutManager.VERTICAL, false);
//                recyclerView.setLayoutManager(linearLayoutManager3);
//                recyclerView.setAdapter(videolistAdapter);
//            }
//        });
    }
}