package edu.huflit.shopDT.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import edu.huflit.shopDT.Activity.HomeActivity;
import edu.huflit.shopDT.PhoneDetail_Activity;
import edu.huflit.shopDT.Phones;
import edu.huflit.shopDT.R;

public class FAVAdapter extends RecyclerView.Adapter<FAVAdapter.PhonelistViewHolder> {

    private ArrayList<Phones> phoneList;
    Context context;


    public FAVAdapter(ArrayList<Phones> phoneList, Context context) {
        this.phoneList = phoneList;
        this.context = context;
    }
    @NonNull
    @Override
    public PhonelistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View listView = inflater.inflate(R.layout.item_fav, parent, false);
        PhonelistViewHolder viewHolder = new PhonelistViewHolder(listView) ;
          return viewHolder;
    }
    public void onBindViewHolder(@NonNull PhonelistViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Phones phones = phoneList.get(position);
        String phoneID = phones.getId();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("List_Phone");
        ref.child(phoneID)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String name = ""+snapshot.child("name").getValue();
                        String price = ""+snapshot.child("price").getValue();
                        String de = ""+snapshot.child("de").getValue();
                        String imgURL = ""+snapshot.child("imgURL").getValue();
                        String id = ""+snapshot.child("id").getValue();

                        phones.setFav(true);
                        phones.setName(name);
                        phones.setId(id);
                        phones.setPrice(price);
                        phones.setImgURL(imgURL);
                        phones.setDe(de);

                        holder.mName.setText(name);
                        holder.mPrice.setText(price);
                        Glide.with(context)
                                .load(imgURL)
                                .into(holder.mIMGView);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, PhoneDetail_Activity.class);
//                String phoneName = phoneList.get(position).getName();
//                String phonePrice = phoneList.get(position).getPrice();
//                String phoneDetails = phoneList.get(position).getDe();
//                String phoneImgURL = phoneList.get(position).getImgURL();
//                String phoneID = phoneList.get(position).getId();
//                Phone phone = new Phone(phoneName, phonePrice, phoneDetails, phoneImgURL,phoneID);
                i.putExtra("phone", phones);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });
        holder.mfav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeActivity.removedFromFAV(context, phones.getId());
            }
        });
    }
    public static class PhonelistViewHolder extends RecyclerView.ViewHolder{
        TextView mName, mPrice;
        ImageView mIMGView,mfav;
        public PhonelistViewHolder(@NonNull View itemView) {
            super(itemView);
            mName = itemView.findViewById(R.id.favName);
            mPrice = itemView.findViewById(R.id.favPrice);
            mIMGView = itemView.findViewById(R.id.imgIV);
            mfav= itemView.findViewById(R.id.imFAV);
        }
    }

    private void loadphoneDetails(Phones phones, PhonelistViewHolder holder) {
        String phoneID = phones.getId();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("List_Phone");
        ref.child(phoneID)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String name = ""+snapshot.child("name").getValue();
                        String price = ""+snapshot.child("price").getValue();
                        String de = ""+snapshot.child("de").getValue();
                        String imgURL = ""+snapshot.child("imgURL").getValue();
                        String id = ""+snapshot.child("id").getValue();

                        phones.setFav(true);
                        phones.setName(name);
                        phones.setId(id);
                        phones.setPrice(price);
                        phones.setImgURL(imgURL);
                        phones.setDe(de);

                        holder.mName.setText(name);
                        holder.mPrice.setText(price);
                        Glide.with(context)
                                .load(imgURL)
                                .into(holder.mIMGView);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }


    @Override
    public int getItemCount() {
        return phoneList.size();
    }
}
