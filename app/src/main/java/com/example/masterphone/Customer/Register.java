package com.example.masterphone.Customer;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.masterphone.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import java.util.regex.Pattern;

public class Register extends AppCompatActivity {
    private EditText edtmail, edtmk, edtmkdk2;
    private Button btntt;
    private FirebaseAuth auth;
    private TextView layoutdn;
    FirebaseFirestore firestore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edtmail = findViewById(R.id.edtemail);
        edtmk = findViewById(R.id.edtmkdangki);
        edtmkdk2 = findViewById(R.id.edtmkdangki2);
        btntt = findViewById(R.id.bnttieptheo);
        layoutdn = findViewById(R.id.tvdntronggmail);

        firestore = FirebaseFirestore.getInstance();

        dangkiListener();

        btntt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth = FirebaseAuth.getInstance();
                String stremail = edtmail.getText().toString();
                String strpass = edtmk.getText().toString();
                String strpass2 = edtmkdk2.getText().toString();


                if (TextUtils.isEmpty(stremail)) {
                    Toast.makeText(Register.this, "Vui lòng nhập gmail", Toast.LENGTH_SHORT).show();
                    edtmail.requestFocus();
                } else if (TextUtils.isEmpty(strpass)) {
                    Toast.makeText(Register.this, "Vui lòng nhập mật khẩu", Toast.LENGTH_SHORT).show();
                    edtmk.requestFocus();
                } else if (TextUtils.isEmpty(strpass2)) {
                    Toast.makeText(Register.this, "Vui lòng nhập lại mật khẩu", Toast.LENGTH_SHORT).show();
                    edtmkdk2.requestFocus();
                } else if (!strpass.equals(strpass2)) {
                    Toast.makeText(Register.this, "Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
                    edtmkdk2.requestFocus();
                } else if (!isValidEmail(stremail)) {
                    Toast.makeText(Register.this, "Email không hợp lệ", Toast.LENGTH_SHORT).show();
                    edtmail.requestFocus();
                } else {
                    auth.createUserWithEmailAndPassword(stremail, strpass)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {

                                        userID = auth.getCurrentUser().getUid();
                                        DocumentReference documentReference = firestore.collection("USERS").document(userID);
                                        //.collection("Info").document();
//                                        CollectionReference InfocollectionReference = firestore.collection("USERS").document(userID).collection("Info");
                                        Map<String, Object> userinfo = new HashMap<>();
                                        userinfo.put("idUser", userID);
                                        userinfo.put("Fullname", null);
                                        userinfo.put("Mail", stremail);
                                        userinfo.put("Phone", null);
                                        documentReference.set(userinfo);
//                                        InfocollectionReference.add(userinfo);

                                        Intent intent = new Intent(Register.this, VerifyMail.class);
                                        startActivity(intent);

                                    } else {
                                        Toast.makeText(Register.this, "Tài khoản đã có trên hệ thống",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
//                auth.createUserWithEmailAndPassword(stremail,strpass).addOnCompleteListener(this,)
                }
            }
        });
    }

    // Hàm kiểm tra địa chỉ email có hợp lệ
    private boolean isValidEmail(String email) {
        String emailPattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return Pattern.matches(emailPattern, email);
    }

    private void dangkiListener() {
        layoutdn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register.this, Login.class);
                startActivity(intent);
            }
        });
    }
}