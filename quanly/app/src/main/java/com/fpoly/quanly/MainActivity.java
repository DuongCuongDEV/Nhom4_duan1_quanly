package com.fpoly.quanly;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    Button btnLogin;
    EditText txtEmail, txtpass;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();


        btnLogin = findViewById(R.id.btn_login);
        txtEmail = findViewById(R.id.ed_userName);
        txtpass = findViewById(R.id.ed_pasWord);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });


    }
    private void login(){

        if(validate() > 0) {
            String email, pass;
            email = txtEmail.getText().toString();
            pass = txtpass.getText().toString();

            if (TextUtils.isEmpty(email)){
                Toast.makeText(this, "Vui long nhap email", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(pass)){
                Toast.makeText(this, "Vui long nhap password", Toast.LENGTH_SHORT).show();
                return;
            }

            mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(getApplicationContext(), "Dang nhap thanh cong", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this,Home.class));
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Dang nhap khong thanh cong", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

    public int validate() {
        int check = 1;
        String checkemail = "[a-zA-Z0-9]+(?:\\.[a-zA-Z0-9]+)*@[a-zA-Z0-9]+(?:\\.[a-zA-Z0-9]+)";
        if (txtEmail.getText().length() == 0) {
//            dialog.dismiss();
            Toast.makeText(MainActivity.this, "Email không được để trống.",
                    Toast.LENGTH_LONG).show();
            return check - 1;
        } else if (!txtEmail.getText().toString().matches(checkemail)) {
//            dialog.dismiss();
            Toast.makeText(MainActivity.this, "Email không đúng định dạng.",
                    Toast.LENGTH_LONG).show();
            return check - 1;
        } else if (txtpass.getText().length() == 0) {
//            dialog.dismiss();
            Toast.makeText(MainActivity.this, "Mật khẩu không được để trống.",
                    Toast.LENGTH_LONG).show();
            return check - 1;
        } else if (txtpass.getText().length() < 6) {
//            dialog.dismiss();
            Toast.makeText(MainActivity.this, "Mật khẩu phải lớn hơn 6 kí tự.",
                    Toast.LENGTH_LONG).show();
            return check - 1;
        }
        return check;
    }
}