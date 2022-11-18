package com.fpoly.quanly;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Home extends AppCompatActivity {

    TextView btnHoaDon,btnThongKe,btnSanPham,btnExit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        btnHoaDon = findViewById(R.id.btnHoaDon);
        btnThongKe = findViewById(R.id.btnThongKe);
        btnSanPham = findViewById(R.id.btnSanPham);
        btnExit = findViewById(R.id.btnExit);

        btnThongKe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this,ThongKe.class);
                startActivity(intent);
            }
        });

        btnSanPham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this,QuanLySanPham.class);
                startActivity(intent);
            }
        });
    }
}