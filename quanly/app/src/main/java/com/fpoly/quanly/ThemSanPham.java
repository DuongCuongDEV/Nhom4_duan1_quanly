package com.fpoly.quanly;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class ThemSanPham extends AppCompatActivity {
    Button btnupload,btnshow;
    EditText txtname, txtMoTa, txtGiaCu, txtGiaMoi;
    ImageButton imageButton;
    FirebaseDatabase mdatabase;
    DatabaseReference databaseReference;
    FirebaseStorage mstorage;
    private static final int Gallery_code = 1;
    Uri imgurl = null;
    ProgressDialog dialog;

    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_san_pham);
        imageButton = findViewById(R.id.imageview);
        txtname = findViewById(R.id.txtName);
        txtMoTa = findViewById(R.id.txtMoTa);
        txtGiaCu = findViewById(R.id.txtGiaCu);
        txtGiaMoi = findViewById(R.id.txtGiaMoi);


        btnupload = findViewById(R.id.btnupload);
        btnshow = findViewById(R.id.show);
        mdatabase = FirebaseDatabase.getInstance();
        databaseReference = mdatabase.getReference().child("SanPham");
        mstorage = FirebaseStorage.getInstance();
        dialog = new ProgressDialog(this);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, Gallery_code);
            }
        });
//        btnshow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(MainActivity.this,Showimge.class));
//            }
//        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Gallery_code && resultCode == RESULT_OK) {
            imgurl = data.getData();
            imageButton.setImageURI(imgurl);
        }
        btnupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = txtname.getText().toString().trim();
                String moTa = txtMoTa.getText().toString().trim();
                String giaCu = txtGiaCu.getText().toString().trim();
                String giaMoi = txtGiaMoi.getText().toString().trim();

                if (!(name.isEmpty() && imgurl != null && moTa.isEmpty() && giaCu.isEmpty() && giaMoi.isEmpty())) {
                    dialog.setTitle("Upload.....");
                    dialog.show();
                    StorageReference reference = mstorage.getReference().child("imagepost").child(imgurl.getLastPathSegment());
                    reference.putFile(imgurl).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> task = taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    String t = task.getResult().toString();
                                    DatabaseReference newpost = databaseReference.push();
                                    newpost.child("name").setValue(name);
                                    newpost.child("moTa").setValue(moTa);
                                    newpost.child("khuyenmai").setValue(giaCu);
                                    newpost.child("Gia").setValue(giaMoi);
                                    newpost.child("image").setValue(task.getResult().toString());
                                    dialog.dismiss();
                                }
                            });
                        }
                    });
                }
            }
        });
    }
}