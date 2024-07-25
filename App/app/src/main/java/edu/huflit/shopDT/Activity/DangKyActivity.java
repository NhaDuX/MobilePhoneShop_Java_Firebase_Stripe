package edu.huflit.shopDT.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.regex.Pattern;

import edu.huflit.shopDT.databinding.ActivityDangKyBinding;


public class DangKyActivity extends AppCompatActivity {
    private ActivityDangKyBinding binding;

    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDangKyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Processing..");
        progressDialog.setCanceledOnTouchOutside(false);

        binding.QuayLaiDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        binding.DangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateData();

            }
        });



    }
    private String Email1 = "", Password1 = "", TenDayDu = "", SoDienThoai = "", DiaChi = "";

    private void validateData() {
        Email1 = binding.Email1.getText().toString().trim();
        Password1 = binding.Password1.getText().toString().trim();
        TenDayDu = binding.TenDayDu.getText().toString().trim();
        SoDienThoai = binding.SoDienThoai.getText().toString().trim();
        DiaChi = binding.DiaChi.getText().toString().trim();
        String cPassword1 = binding.cPassword1.getText().toString().trim();
        if(SoDienThoai.toString().length()!=10){
            Toast.makeText(this,"Số điện thoại phải = 10 số", Toast.LENGTH_SHORT).show();
        }
        if(Password1.toString().length()<6){
            Toast.makeText(this,"Password phải trên 6 ký tự", Toast.LENGTH_SHORT).show();
        }
        if(!EMAIL_ADDRESS_PATTERN.matcher(Email1).matches()){
            Toast.makeText(this, "Invalid Email", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Password1)){
            Toast.makeText(this,"Nhập Password", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(TenDayDu)){
            Toast.makeText(this,"Nhập tên đầy đủ", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(SoDienThoai)){
            Toast.makeText(this,"Số điện thoại", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(DiaChi)){
            Toast.makeText(this,"Địa chỉ", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(cPassword1)){
            Toast.makeText(this,"Nhập password", Toast.LENGTH_SHORT).show();
        }
        else if (!Password1.equals(cPassword1)){
            Toast.makeText(this,"Enter correct password", Toast.LENGTH_SHORT).show();
        }
        else {
            createUserAccount();
        }

    }
    public static final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    );

    private void createUserAccount() {
        progressDialog.setMessage("Processing..");
        progressDialog.show();
        // create user on fb
        mAuth.createUserWithEmailAndPassword(Email1, Password1)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        updateUserInfo();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(DangKyActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
    }

    private void updateUserInfo() {
        progressDialog.setMessage("Lưu thông tin người dùng");
        long timestamp = System.currentTimeMillis();
        String uid = mAuth.getUid();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("uid", uid);
        hashMap.put("email", Email1);
        hashMap.put("ten",TenDayDu );
        hashMap.put("sdt", SoDienThoai);
        hashMap.put("diachi", DiaChi);
        hashMap.put("userType", "user");
        hashMap.put("timestamp", timestamp);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("User");
        ref.child(uid)
                .setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        progressDialog.dismiss();
                        Toast.makeText(DangKyActivity.this, "Tạo Tài khoản thành công", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(DangKyActivity.this, MainActivity.class));
                        finish();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure( Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(DangKyActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });


    }


}