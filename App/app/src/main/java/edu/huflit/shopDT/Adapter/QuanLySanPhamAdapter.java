package edu.huflit.shopDT.Adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import edu.huflit.shopDT.PhoneDetail_Activity;
import edu.huflit.shopDT.Phones;
import edu.huflit.shopDT.R;

public class QuanLySanPhamAdapter extends RecyclerView.Adapter<QuanLySanPhamAdapter.PhonelistViewHolder>{

    private ArrayList<Phones> phoneList;
    Context context;
    private List<String> itemIdList;

    Listener listener;
public static class PhonelistViewHolder extends RecyclerView.ViewHolder{
    TextView mName, mPrice , mDe, mvideoID;
    ImageView mIMGView,favoriteIV;

    public PhonelistViewHolder(@NonNull View itemView) {
        super(itemView);
        mName = itemView.findViewById(R.id.tvName);
        mPrice = itemView.findViewById(R.id.tvPrice);
        mDe = itemView.findViewById(R.id.tvDe);
        mIMGView = itemView.findViewById(R.id.imgItemView);
//            mvideoID = itemView.findViewById(R.id.videoID);
        favoriteIV = itemView.findViewById(R.id.favoriteIV);
    }
}
    public QuanLySanPhamAdapter(ArrayList<Phones> phoneList, Context context) {
        this.phoneList = phoneList;
        this.context = context;
    }

    public QuanLySanPhamAdapter(ArrayList<Phones> phoneList, Context context, List<String> itemIdList, Listener listener) {
        this.phoneList = phoneList;
        this.context = context;
        this.itemIdList= itemIdList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public QuanLySanPhamAdapter.PhonelistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View listView = inflater.inflate(R.layout.item_ql_san_pham, parent, false);
        QuanLySanPhamAdapter.PhonelistViewHolder viewHolder = new QuanLySanPhamAdapter.PhonelistViewHolder(listView) ;
        return viewHolder;
    }
    public void onBindViewHolder(@NonNull QuanLySanPhamAdapter.PhonelistViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Phones item = phoneList.get(position);
        Glide.with(this.context)
                .load(item.getImgURL())
                .into(holder.mIMGView);
        holder.mName.setText(item.getName());
        holder.mPrice.setText(item.getPrice());
        holder.mDe.setText(item.getDe());
        //khai báo thêm vào đây --------
        String itemID = itemIdList.get(position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.clickRoute(item);
            }
        });

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference itemRef = firebaseDatabase.getReference("List_Phone");

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Xóa sản phẩm");
                builder.setMessage("Bạn muốn sản phẩm này?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        itemRef.child(String.valueOf(item.getId())).removeValue();
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
    }

    @Override
    public int getItemCount() {
        return phoneList.size();
    }

    public interface Listener{
        void clickRoute(Phones item);
    }
}
