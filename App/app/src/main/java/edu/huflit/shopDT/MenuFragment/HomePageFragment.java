package edu.huflit.shopDT.MenuFragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.huflit.shopDT.Adapter.ShopAdapter;
import edu.huflit.shopDT.Phones;
import edu.huflit.shopDT.R;

public class HomePageFragment extends Fragment {
    private ShopAdapter shopAdapter;
    private DatabaseReference database;
    RecyclerView recyclerView1,recyclerView2;
    ArrayList<Phones> phoneList;
    public HomePageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_page, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        database = FirebaseDatabase.getInstance().getReference("List_Phone");

        recyclerView1 = view.findViewById(R.id.categoryRecyclerView);
        recyclerView2 = view.findViewById(R.id.PhonesRecyclerView2);

        ImageSlider imageSlider = view.findViewById(R.id.banner);
        ArrayList<SlideModel> sliderModels = new ArrayList<SlideModel>();
        sliderModels.add(new SlideModel("https://cdn2.cellphones.com.vn/insecure/rs:fill:690:300/q:90/plain/https://dashboard.cellphones.com.vn/storage/oppo-reno12-banner-sliding-5-7-2024.jpg", ScaleTypes.FIT));
        sliderModels.add(new SlideModel("https://cdn2.cellphones.com.vn/insecure/rs:fill:690:300/q:90/plain/https://dashboard.cellphones.com.vn/storage/mo-ban-galaxyz6-series-sliding.png", ScaleTypes.FIT));
        sliderModels.add(new SlideModel("https://cdn2.cellphones.com.vn/insecure/rs:fill:690:300/q:90/plain/https://dashboard.cellphones.com.vn/storage/nang-cap-iphone-15-compatibility.jpg", ScaleTypes.FIT));
        imageSlider.setImageList(sliderModels,ScaleTypes.FIT);
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
                shopAdapter= new ShopAdapter(phoneList, getContext(), itemIdList);

                recyclerView1.setAdapter(shopAdapter);
                recyclerView2.setAdapter(shopAdapter);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        recyclerView1.setLayoutManager(linearLayoutManager1);

        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        recyclerView2.setLayoutManager(linearLayoutManager2);
    }

    public  static void addtoFAV(Context context, String phoneID){
        long timeStamp = System.currentTimeMillis();
        String time = String.valueOf(timeStamp);
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("phoneID", ""+phoneID);
//        hashMap.put("timestamp", ""+timeStamp);

        //save to db
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("User");
        ref.child(FirebaseAuth.getInstance().getUid()).child("Favorites").child(phoneID)
                .setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(context, "Added to your favorited list",Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Failed to add to your favorited list",Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public static void removedFromFAV(Context context,String phoneID){
        long timeStamp = System.currentTimeMillis();
        String time = String.valueOf(timeStamp);
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("phoneID", ""+phoneID);
//        hashMap.put("timestamp", ""+timeStamp);

        //remove from db
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("User");
        ref.child(FirebaseAuth.getInstance().getUid()).child("Favorites").child(phoneID)
                .removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        //Toast.makeText(context, "Removed",Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //Toast.makeText(context, "Failed to add to your favorited list",Toast.LENGTH_SHORT).show();
                    }
                });
    }
}