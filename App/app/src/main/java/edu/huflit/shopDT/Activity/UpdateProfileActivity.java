package edu.huflit.shopDT.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import edu.huflit.shopDT.R;
import edu.huflit.shopDT.databinding.ActivityDangKyBinding;
import edu.huflit.shopDT.databinding.ActivityUpdateProfileBinding;

public class UpdateProfileActivity extends AppCompatActivity {

    private ActivityUpdateProfileBinding binding;

    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        binding = ActivityUpdateProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Chotto mate");
        progressDialog.setCanceledOnTouchOutside(false);

        getData();

        binding.Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateData();

            }
        });
    }
    private void getData(){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("User");
        ref.child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                binding.Email1.setText(snapshot.child("email").getValue().toString());
                binding.SoDienThoai.setText(snapshot.child("sdt").getValue().toString());
                binding.TenDayDu.setText(snapshot.child("ten").getValue().toString());
                binding.DiaChi.setText(snapshot.child("diachi").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private String Email1 = "", Password1 = "", TenDayDu = "", SoDienThoai = "", DiaChi = "";

    private void validateData() {
        Email1 = binding.Email1.getText().toString().trim();
        TenDayDu = binding.TenDayDu.getText().toString().trim();
        SoDienThoai = binding.SoDienThoai.getText().toString().trim();
        DiaChi = binding.DiaChi.getText().toString().trim();

        if(!Patterns.EMAIL_ADDRESS.matcher(Email1).matches()){
            Toast.makeText(this, "Invalid Email", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(TenDayDu)){
            Toast.makeText(this,"Nhập tên đầy đủ", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(SoDienThoai)){
            Toast.makeText(this,"Số điện thoại", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(DiaChi)) {
            Toast.makeText(this, "Địa chỉ", Toast.LENGTH_SHORT).show();
        }
        else {
            updateUserAccount();
        }

    }

    private void updateUserAccount() {
        progressDialog.setMessage("Chottooooo");
        progressDialog.show();
        // create user on fb
        progressDialog.setMessage("Cập nhật tin người dùng");
        long timestamp = System.currentTimeMillis();
        String uid = mAuth.getCurrentUser().getUid();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("email", Email1);
        hashMap.put("ten",TenDayDu );
        hashMap.put("sdt", SoDienThoai);
        hashMap.put("diachi", DiaChi);
        hashMap.put("userType", "user");
        hashMap.put("timestamp", timestamp);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("User");
        ref.child(uid)
                .updateChildren(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        progressDialog.dismiss();
                        Toast.makeText(UpdateProfileActivity.this, "Cập nhật tài khoản thành công", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(UpdateProfileActivity.this, MainActivity.class);
                        intent.putExtra("flag", "profile");
                        startActivity(intent);
                        finish();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure( Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(UpdateProfileActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
    }

}