package com.android.shopdt;

import android.content.Intent;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

    private ArrayList<VideoList> videolists;
    private Context context_search;
    public SearchAdapter(Context context_search, ArrayList<VideoList> videolists) {
        this.videolists = videolists;
        this.context_search = context_search;
    }


    @NonNull
    @Override
    public SearchAdapter.SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_holder,parent,false);
        return new SearchViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int i) {
        VideoList videoList = videolists.get(i);
        holder.mName.setText(videoList.getName());
        holder.mPrice.setText(videoList.getDe1());
        holder.mDe2.setText(videoList.getDe2());
        Glide.with(context_search)
                .load(videolists.get(i).getImageurl())
                .dontAnimate()
                .into(holder.mIMGView);



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                listener.onItemClickListener(videoList);
                Intent y =new Intent(context_search,VideoplayActivity.class);
                y.putExtra("VideoID@#",videolists.get(i).getId());
                y.putExtra("NAME@#",videolists.get(i).getName());
                y.putExtra("Details#@",videolists.get(i).getDetails());
                y.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context_search.startActivity(y);
            }
        });
    }

    @Override
    public int getItemCount() {
        return videolists.size();
    }

    class SearchViewHolder extends RecyclerView.ViewHolder{
        TextView mName, mPrice , mDe2, mvideoID;
        ImageView mIMGView;
        LinearLayout mitem_search_layout;
        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            mName = itemView.findViewById(R.id.tvName1);
            mPrice = itemView.findViewById(R.id.tvDe12);
            mDe2 = itemView.findViewById(R.id.tvDe22);
            mitem_search_layout = itemView.findViewById(R.id.Search_list_layout);
            mIMGView = itemView.findViewById(R.id.imgItemView1);
            mvideoID = itemView.findViewById(R.id.videoID1);
        }
    }
}
