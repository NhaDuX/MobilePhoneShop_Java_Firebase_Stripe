package edu.huflit.shopDT.Activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.checkerframework.checker.units.qual.C;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import edu.huflit.shopDT.Adapter.CartAdapter;
import edu.huflit.shopDT.Database.model.Cart;
import edu.huflit.shopDT.Database.model.Order;
import edu.huflit.shopDT.Database.model.Phone;
import edu.huflit.shopDT.Phones;
import edu.huflit.shopDT.R;
import edu.huflit.shopDT.interfaces.ClickListener;

public class CartActivity extends AppCompatActivity{

    TextView btnCancel, deleteAllCart;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference cartRef, historyRef;
    String currentUserId;
    Cart cart;
    List<Cart> cartList;

    RecyclerView rvCartView;
    CartAdapter cartAdapter;

    int fullprice;
    ArrayList<String> arrayList = null;

    TextView txtNoCart, cartQuantity, cartTotalPrice, txtQuantity;

    RelativeLayout btnCheckout;
    String itemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        txtNoCart = findViewById(R.id.txt);
        btnCheckout = findViewById(R.id.btnCheckOut);
        cartQuantity = findViewById(R.id.cartQuantity);
        cartTotalPrice = findViewById(R.id.cartTotalPrice);
        txtQuantity = findViewById(R.id.txtQuantity);
        btnCancel = findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        currentUserId = user.getUid();

        firebaseDatabase = FirebaseDatabase.getInstance();
        cartRef = firebaseDatabase.getReference("Shopping Cart");
        historyRef = firebaseDatabase.getReference("Order History");

        itemId = getIntent().getStringExtra("itemId");

        rvCartView = findViewById(R.id.rvCartView);

        rvCartView.setLayoutManager(new LinearLayoutManager(CartActivity.this,LinearLayoutManager.VERTICAL,false));
        deleteAllCart = findViewById(R.id.deleteAllCart);
        deleteAllCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cartAdapter.deleteAllCart();
                getCartQuantity();
            }
        });

        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View customLayout = LayoutInflater.from(CartActivity.this).inflate(R.layout.payment_method_view, null);

                RadioGroup radioGroup = customLayout.findViewById(R.id.paymentGroup);

                // Build the AlertDialog
                AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
                builder.setView(customLayout);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ProgressDialog progressDialog = new ProgressDialog(CartActivity.this);
                        progressDialog.setTitle("Chotto mate");
                        progressDialog.setCanceledOnTouchOutside(false);
                        progressDialog.setMessage("Đang tạo đơn");
                        progressDialog.show();
                        UUID orderID = UUID.randomUUID();

                        int selectedRadioButtonId = radioGroup.getCheckedRadioButtonId();
                        RadioButton selectedRadioButton = radioGroup.findViewById(selectedRadioButtonId);
                        EditText edtAddress = customLayout.findViewById(R.id.edtAddress);

                        int quantity=0;
                        double orderPrice = 0.0;
                        for (int i=0;i<cartList.size();i++){
                            quantity += Integer.parseInt(cartList.get(i).getItemCartQuantity());
                            orderPrice += Double.parseDouble(cartList.get(i).getItemCartTotalPrice());
                        }

                        Calendar calendar = Calendar.getInstance();
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
                        String currentDate = simpleDateFormat.format(calendar.getTime());

                        // Get the text value of the selected payment method
                        String selectedPaymentMethod = selectedRadioButton.getText().toString();

                        if (edtAddress.getText().toString().equals(null)){
                            Toast.makeText(CartActivity.this, "Địa chỉ không được trống", Toast.LENGTH_SHORT).show();
                        } else if (selectedPaymentMethod.equals("Pay with card")) {
                            payWithCard(orderID, selectedPaymentMethod, progressDialog, edtAddress.getText().toString(), currentDate, quantity, orderPrice);
                        }else {
                            cashOnDelivery(orderID, selectedPaymentMethod, progressDialog, edtAddress.getText().toString(), currentUserId, currentDate, quantity, orderPrice);
                        }
                    }
                });  // Add positive button or customize as needed

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                // Show the AlertDialog
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }
        });
    }

    private void payWithCard(UUID orderID, String selectedPaymentMethod, ProgressDialog progressDialog, String address
            ,String currentDate, int quantity, double orderPrice){
        Intent intent = new Intent(CartActivity.this, PaymentActivity.class);

        Order order = new Order(String.valueOf(orderID), String.valueOf(quantity), String.format("%.0f",orderPrice),
                currentDate, address,selectedPaymentMethod, "Progressing");

        intent.putExtra("order", order);
        progressDialog.dismiss();
        startActivity(intent);
    }

    private void cashOnDelivery(UUID orderID, String selectedPaymentMethod, ProgressDialog progressDialog, String address
                , String currentUserId, String currentDate, int quantity, double orderPrice){


        Order order = new Order(String.valueOf(orderID), String.valueOf(quantity), String.format("%.0f",orderPrice),
                currentDate, address,selectedPaymentMethod, "Progressing");

        historyRef.child(currentUserId).child(String.valueOf(orderID)).setValue(order).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                //delete cart when create order
                cartRef.child(currentUserId).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(CartActivity.this,MainActivity.class);
                                    intent.putExtra("flag", "history");
                                    startActivity(intent);
                                    progressDialog.dismiss();
                                }
                            },3000);
                        }
                    }
                });
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        loadCartView();
        getCartQuantity();
        getCartTotalPrice();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadCartView();
        getCartQuantity();
        getCartTotalPrice();
    }
    private void getCartQuantity() {
        cartRef.child(currentUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    arrayList.add(dataSnapshot.getKey());
                }
                String countItemInCart= Integer.toString(arrayList.size());

                int itemInCart = Integer.parseInt(countItemInCart);

                cartQuantity.setText(Integer.toString(itemInCart));
                if (itemInCart>1){
                    txtQuantity.setText("Items");
                }

                if (itemInCart==0){
                    deleteAllCart.setVisibility(View.GONE);
                    txtNoCart.setText("Your cart is empty");
                    btnCheckout.setVisibility(View.GONE);

                }
                else {
                    deleteAllCart.setVisibility(View.VISIBLE);
                    txtNoCart.setText("Your Cart");
                    btnCheckout.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getCartTotalPrice(){
        cartRef.child(currentUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String priceTotal2 = null;
                int totalCartPrice = 0;
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    priceTotal2 = dataSnapshot.child("itemCartTotalPrice").getValue().toString();
                    totalCartPrice = totalCartPrice + Integer.parseInt(priceTotal2);

                }
                if (priceTotal2 != null){
                    cartTotalPrice.setText(Integer.toString(totalCartPrice));
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void loadCartView(){
        cartRef.child(currentUserId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                cartList = new ArrayList<>();
                 for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    cart = dataSnapshot.getValue(Cart.class);
                    cart.setCartId(dataSnapshot.getKey());
                    cartList.add(cart);
                }
                cartAdapter = new CartAdapter(CartActivity.this, cartList);
                rvCartView.setAdapter(cartAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}