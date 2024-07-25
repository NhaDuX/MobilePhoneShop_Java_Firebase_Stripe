package com.android.shopdt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    DatabaseReference ref;
    private RecyclerView recyclerView;
    private ArrayList<VideoList> videolists;
    SearchView searchView;
    private SearchAdapter searchAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        recyclerView = findViewById(R.id.SearchRycyclerView);
        searchView = findViewById(R.id.searchView);
        recyclerView.setLayoutManager(new LinearLayoutManager
                (SearchActivity.this,LinearLayoutManager.VERTICAL,false));
        recyclerView.addItemDecoration(new DividerItemDecoration
                (SearchActivity.this,DividerItemDecoration.VERTICAL));


        videolists = new ArrayList<>();


        ref = FirebaseDatabase.getInstance().getReference().child("Video");
//        ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
//                    VideoList videoList = new VideoList();
//                    videoList.setName(snapshot.child("NAME").getValue().toString());
//                    videoList.setImageurl(snapshot.child("imageURL").getValue().toString());
//                    videoList.setDe1(snapshot.child("DRIPTION1").getValue().toString());
//                    videoList.setDe2(snapshot.child("DRIPTION2").getValue().toString());
//                    videoList.setDetails(snapshot.child("DESCRIBE").getValue().toString());
//                    videoList.setId(snapshot.child("videoID").getValue().toString());
//                    videolists.add(videoList);
//                }
//                searchAdapter = new SearchAdapter(getApplicationContext(), videolists);
//                recyclerView.setAdapter(searchAdapter);
//                searchAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(SearchActivity.this,"Error: " + error.getMessage(),Toast.LENGTH_SHORT).show();
//            }
//        });
    }


    @Override
    public void onStart() {
        super.onStart();
        if(ref!= null){

        }
        if(searchView != null){
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    search(s);
                    return true;
                }
            });
        }
    }
    private  void search(String str){
        ArrayList<VideoList> vList = new ArrayList<>();
        for(VideoList object : videolists){
            if(object.getName().toLowerCase().contains(str.toLowerCase())){
                vList.add(object);
            }
        }
        searchAdapter = new SearchAdapter(getApplicationContext(), vList);
        recyclerView.setAdapter(searchAdapter);
    }

}