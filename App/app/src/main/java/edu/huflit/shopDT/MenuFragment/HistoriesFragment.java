package edu.huflit.shopDT.MenuFragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import edu.huflit.shopDT.Activity.CartActivity;
import edu.huflit.shopDT.Activity.LoginActivity;
import edu.huflit.shopDT.Adapter.CartAdapter;
import edu.huflit.shopDT.Adapter.HistoryAdapter;
import edu.huflit.shopDT.Database.model.Cart;
import edu.huflit.shopDT.Database.model.Order;
import edu.huflit.shopDT.Phones;
import edu.huflit.shopDT.R;
import edu.huflit.shopDT.Adapter.ShopAdapter;

public class HistoriesFragment extends Fragment {

    ImageView imageView;
    RecyclerView rvHistory;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference historyRef;
    FirebaseAuth firebaseAuth;

    List<Order> historyList;
    Order order;
    HistoryAdapter historyAdapter;
    String userID;

    public HistoriesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.histories_ac, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        imageView = view.findViewById(R.id.deleteHistory);
        rvHistory = view.findViewById(R.id.rvViewed);

//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(getContext(), LoginActivity.class);
//                startActivity(i);
//            }
//        });

        firebaseDatabase = FirebaseDatabase.getInstance();
        historyRef = firebaseDatabase.getReference("Order History");
        firebaseAuth = FirebaseAuth.getInstance();
        userID = firebaseAuth.getUid();

        loadCartView();
        rvHistory.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));

        historyList = new ArrayList<>();

    }

    @Override
    public void onStart() {
//         Saving state of our app
//         using SharedPreferences

        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void loadCartView(){
        historyRef.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                historyList = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    order = dataSnapshot.getValue(Order.class);
                    historyList.add(order);
                }
                historyAdapter = new HistoryAdapter(getContext(), historyList);
                rvHistory.setAdapter(historyAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}