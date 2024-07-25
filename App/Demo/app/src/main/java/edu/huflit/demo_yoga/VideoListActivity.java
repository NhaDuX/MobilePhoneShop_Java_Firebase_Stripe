
package com.android.shopdt;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class VideoListActivity extends AppCompatActivity implements VideoListAdapter.VideoCallBack {
    //
    RecyclerView recyclerView;
    ArrayList<VideoList> videolists;
    VideoListAdapter videolistAdapter;
    VideoDBHelper videoDBHelper;

//
////    int index;
//    VideoDBHelper videoDB;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_list);

        videoDBHelper = new VideoDBHelper(VideoListActivity.this);

        recyclerView = findViewById(R.id.RViewVList);
        videoDBHelper.copyDatabase();
        videolists = videoDBHelper.getVideos();


        videolistAdapter= new VideoListAdapter(videolists);

        recyclerView.setAdapter(videolistAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void onItemClick(String id) {
        Intent i = new Intent(VideoListActivity.this,VideoplayActivity.class);
        i.putExtra("VideoID@#",videoDBHelper.getID(id));
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }
}