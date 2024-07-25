package com.android.shopdt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

public class VideoplayActivity extends AppCompatActivity {
    TextView mvideoid, mvideoname, mtvdetails;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videoplay);

        mvideoid = findViewById(R.id.tvvideoid);
        mvideoname = findViewById(R.id.Video_NAME);
        mtvdetails = findViewById(R.id.tvDetails);

        mvideoname.setText(getIntent().getStringExtra("NAME@#"));
        mtvdetails.setText(getIntent().getStringExtra("Details#@"));

        String ID = getIntent().getStringExtra("VideoID@#");

        YouTubePlayerView youTubePlayerView = findViewById(R.id.youtube_player_view);
        getLifecycle().addObserver(youTubePlayerView);
        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                String videoId = ID;
                youTubePlayer.loadVideo(videoId, 0);
            }
        });
    }
}