package edu.huflit.shopDT.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import edu.huflit.shopDT.QuanLySanPhamActivity;
import edu.huflit.shopDT.R;

public class AdminActivity extends AppCompatActivity {
    Button qlSanPham, qlDonHang;
    Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        qlSanPham = findViewById(R.id.qlSanPham); //Tìm lại button
        qlSanPham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminActivity.this, QuanLySanPhamActivity.class));

            }
        });

        qlDonHang = findViewById(R.id.qlDonHang);
        qlDonHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminActivity.this, QuanLyDonHangActivity.class));
            }
        });
        btnLogout = findViewById(R.id.logout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });
    }
}