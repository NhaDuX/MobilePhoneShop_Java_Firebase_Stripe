package com.android.shopdt;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
//
//import com.bumptech.glide.Glide;
//import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;

public class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.VideolistViewHolder> {

    private ArrayList<VideoList> videolists;
    VideoCallBack videoCallBack;
    public static class VideolistViewHolder extends RecyclerView.ViewHolder{
        TextView mName, mPrice , mDe2, mvideoID;
        ImageView mIMGView;
        public VideolistViewHolder(@NonNull View itemView) {
            super(itemView);
            mName = itemView.findViewById(R.id.tvName);
            mPrice = itemView.findViewById(R.id.tvDe1);
            mDe2 = itemView.findViewById(R.id.tvDe2);
            mIMGView = itemView.findViewById(R.id.imgItemView);
            mvideoID = itemView.findViewById(R.id.videoID);
        }
    }
    public void setCallback(VideoCallBack callback){this.videoCallBack=callback;}
    public VideoListAdapter(ArrayList<VideoList> videolists) {

        this.videolists = videolists;



    }
    @NonNull
    @Override
    public VideolistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View videolistView = inflater.inflate(R.layout.videolistitemlayout, parent, false);
        VideolistViewHolder viewHolder = new VideolistViewHolder(videolistView) ;
        return viewHolder ;
    }



    //    @Override
//    public void onBindViewHolder(@NonNull  VideolistViewHolder holder,int position) {
//        VideoList mvideoList = videolists.get(position);
//        if(mvideoList  == null){
//            return;}
//        else{
//                holder.mName.setText(mvideoList.getName());
//                holder.mPrice.setText(mvideoList.getDe1());
//                holder.mDe2.setText(mvideoList.getDe2());
//
//                Glide.with(holder.mIMGView.getContext())
//                        .load(mvideoList.getImageurl())
//                        .into(holder.mIMGView);
//
//                holder.itemView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Intent i =new Intent(context,VideoplayActivity.class);
//                        i.putExtra("VideoID@#",videolists.get(position).getId());
//                        i.putExtra("NAME@#",videolists.get(position).getName());
//                        i.putExtra("Details#@",videolists.get(position).getDetails());
//                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        context.startActivity(i);
//                    }
//                });
//        }
//    }
    public void onBindViewHolder(@NonNull VideolistViewHolder holder, int position) {
        VideoList item = videolists.get(position);
//        holder.mIMGView.setImageBitmap(Utils.convertToBitmapFromAssets(VideoListAdapter.this,item.getImageurl()));
        holder.mName.setText(item.getName());
        holder.mPrice.setText(item.getDe1());
        holder.mDe2.setText(item.getDe2());
        //khai báo thêm vào đây --------
        holder.itemView.setOnClickListener(view -> videoCallBack.onItemClick(item.getId()));
    }


    @Override
    public int getItemCount() {
        return videolists.size();
    }



    public  interface VideoCallBack{

        void onItemClick(String id);
    }
}
