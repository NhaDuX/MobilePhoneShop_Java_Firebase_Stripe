package edu.huflit.shopDT.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import edu.huflit.shopDT.Adapter.HistoryAdapter;
import edu.huflit.shopDT.Database.model.Order;
import edu.huflit.shopDT.R;

public class QuanLyDonHangActivity extends AppCompatActivity {

    RecyclerView rvOrder;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference orderRef;

    List<Order> historyList;
    HistoryAdapter historyAdapter;
    Order order;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_don_hang);

        back = findViewById(R.id.backprevious);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        firebaseDatabase = FirebaseDatabase.getInstance();
        orderRef = firebaseDatabase.getReference("Order History");

        rvOrder = findViewById(R.id.rvOrderAdmin);

        loadCartView();
        rvOrder.setLayoutManager(new LinearLayoutManager(QuanLyDonHangActivity.this,RecyclerView.VERTICAL,false));
    }

    private void loadCartView(){
        orderRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                historyList = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                        order = dataSnapshot1.getValue(Order.class);
                        historyList.add(order);
                    }
                }
                historyAdapter = new HistoryAdapter(QuanLyDonHangActivity.this, historyList, true);
                rvOrder.setAdapter(historyAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}