package edu.huflit.shopDT.Adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import edu.huflit.shopDT.Database.model.Cart;
import edu.huflit.shopDT.Database.model.Order;
import edu.huflit.shopDT.R;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryItemViewHolder> {
    Context context;

    List<Order> historyList;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference historyRef;
    FirebaseAuth firebaseAuth;

    String userID;

    boolean isAdmin=false;
    Order order;

    public HistoryAdapter(Context context, List<Order> historyList) {
        this.context = context;
        this.historyList = historyList;
    }

    public HistoryAdapter(Context context, List<Order> historyList, boolean isAdmin) {
        this.context = context;
        this.historyList = historyList;
        this.isAdmin = isAdmin;
    }

    @NonNull
    @Override
    public HistoryAdapter.HistoryItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_history,parent,false);
        return new HistoryAdapter.HistoryItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.HistoryItemViewHolder holder, @SuppressLint("RecyclerView") int position) {
        firebaseDatabase = FirebaseDatabase.getInstance();
        historyRef = firebaseDatabase.getReference("Order History");
        firebaseAuth = FirebaseAuth.getInstance();
        userID = firebaseAuth.getUid();

        order = historyList.get(position);

        holder.orderQuantity.setText(order.getOrderItemQuantity());
        holder.orderId.setText(order.getOrderID());
        holder.orderAddress.setText(order.getOrderAddress());
        holder.orderDate.setText(order.getOrderCreateDate());
        holder.orderPayment.setText(order.getOrderPayment());
        holder.orderStatus.setText(order.getOrderStatus());

        String numericValue = order.getOrderPrice().replaceAll("[^0-9]", "");
        holder.orderPrice.setText(numericValue);

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Hủy đơn hàng");
                builder.setMessage("Bạn muốn hủy đơn này?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        historyList.remove(order);
                        historyRef.child(userID).child(order.getOrderID()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(context, "Delete order "+ order.getOrderID(), Toast.LENGTH_SHORT).show();
                            }
                        });
                        notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
                return true;
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                order = historyList.get(position);
                if (isAdmin==true&& !order.getOrderStatus().equals("Done")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Hoàn thành đơn hàng");
                    builder.setMessage("Bạn muốn thay đổi trạng thái đơn hàng thành hoàn thành?");
                    builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            historyRef.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for (DataSnapshot dynamicKey : snapshot.getChildren()) {
                                        for (DataSnapshot orderItemSnapshot : dynamicKey.getChildren()) {
                                            if (orderItemSnapshot.child("orderID").getValue(String.class).equals(order.getOrderID())) {
                                                orderItemSnapshot.child("orderStatus").getRef().setValue("Done");
                                                order.setOrderStatus("Done");
                                                notifyDataSetChanged();
                                                return;
                                            }
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                    });
                    builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    public class HistoryItemViewHolder extends RecyclerView.ViewHolder{
        private TextView orderId, orderPrice, orderQuantity, orderAddress, orderPayment, orderStatus, orderDate;

        public HistoryItemViewHolder ( @NonNull View itemView ) {
            super(itemView);
            orderId = itemView.findViewById(R.id.orderName);
            orderPrice = itemView.findViewById(R.id.orderPrice);
            orderQuantity = itemView.findViewById(R.id.orderQuantity);
            orderAddress = itemView.findViewById(R.id.orderAddress);
            orderPayment = itemView.findViewById(R.id.orderPayment);
            orderStatus = itemView.findViewById(R.id.orderStatus);
            orderDate = itemView.findViewById(R.id.orderDate);
        }
    }
}
