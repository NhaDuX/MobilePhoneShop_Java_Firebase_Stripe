package edu.huflit.shopDT.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.io.Console;
import java.util.List;

import edu.huflit.shopDT.Activity.CartActivity;
import edu.huflit.shopDT.Database.model.Cart;
import edu.huflit.shopDT.Database.model.Phone;
import edu.huflit.shopDT.Phones;
import edu.huflit.shopDT.R;
import edu.huflit.shopDT.interfaces.ClickListener;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartItemViewHolder>{

    private Context context;
    private List<Cart> cartList;
    Cart cart;
    String itemCartId;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference cartRef;
    String currentUserId;
    int fullprice, price;

    public CartAdapter(Context context, List<Cart> cartList) {
        this.context = context;
        this.cartList = cartList;
    }

    @NonNull
    @Override
    public CartItemViewHolder onCreateViewHolder ( @NonNull ViewGroup parent ,int viewType ) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_row,
                parent, false);
        return new CartItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder ( @NonNull CartItemViewHolder holder ,@SuppressLint("RecyclerView") int position ) {
        cart = cartList.get(position);

        Glide.with(this.context)
                .load(cart.getItemCartImg())
                .into(holder.phoneImg);
        holder.quantity.setText(cart.getItemCartQuantity());
        holder.phoneName.setText(cart.getItemCartName());
        holder.phonePrice.setText(cart.getItemCartTotalPrice());

        String numericValue = cart.getItemCartPrice().replaceAll("[^0-9]", ""); // Removes all non-numeric characters
        price = Integer.parseInt(numericValue);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        currentUserId = user.getUid();

        firebaseDatabase = FirebaseDatabase.getInstance();
        cartRef = firebaseDatabase.getReference("Shopping Cart");


        holder.decreaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cart =  cartList.get(position);
                int totalQuantity = Integer.parseInt(cart.getItemCartQuantity());

                itemCartId = cart.getCartId();
                if (totalQuantity >0){
                    totalQuantity--;
                    cart.setItemCartQuantity(String.valueOf(totalQuantity));
                    holder.quantity.setText(String.valueOf(totalQuantity));
                    fullprice = price*(totalQuantity);
                    cart.setItemCartTotalPrice(String.valueOf(fullprice));
                    holder.phonePrice.setText(String.valueOf(fullprice));
                    updateDate(itemCartId, holder);
                    notifyDataSetChanged();
                }
                if (totalQuantity == 0){
                    cartRef.child(currentUserId).child(itemCartId).removeValue();
                    cartList.remove(cart);

                    notifyDataSetChanged();

                }


            }
        });


        holder.increaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cart =  cartList.get(position);
                int totalQuantity = Integer.parseInt(cart.getItemCartQuantity());

                itemCartId = cart.getCartId();
                totalQuantity++;
                cart.setItemCartQuantity(String.valueOf(totalQuantity));
                holder.quantity.setText(String.valueOf(totalQuantity));
                fullprice = price*(totalQuantity);
                cart.setItemCartTotalPrice(String.valueOf(fullprice));
                holder.phonePrice.setText(String.valueOf(fullprice));

                updateDate(itemCartId, holder);
                notifyDataSetChanged();
            }
        });

    }

    private void updateDate(String itemCartId, CartItemViewHolder holder){
        cartRef.child(currentUserId).child(itemCartId).child("itemCartQuantity").setValue(holder.quantity.getText().toString());
        cartRef.child(currentUserId).child(itemCartId).child("itemCartTotalPrice").setValue(holder.phonePrice.getText().toString());

    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public void deleteAllCart(){
        cartRef.child(currentUserId).removeValue();
        cartList.clear();

        notifyDataSetChanged();
    }

    public class CartItemViewHolder extends RecyclerView.ViewHolder{
        private ImageView phoneImg;
        private TextView phoneName, phonePrice, quantity;
        private ImageButton decreaseBtn, increaseBtn;

        public CartItemViewHolder ( @NonNull View itemView ) {
            super(itemView);
            phoneImg = itemView.findViewById(R.id.itemCartImg);
            phoneName = itemView.findViewById(R.id.itemCartName);
            phonePrice = itemView.findViewById(R.id.itemCartPrice);
            quantity = itemView.findViewById(R.id.itemCartQuantity);
            decreaseBtn = itemView.findViewById(R.id.btnCartMinus);
            increaseBtn = itemView.findViewById(R.id.btnCartPlus);
        }
    }

}
