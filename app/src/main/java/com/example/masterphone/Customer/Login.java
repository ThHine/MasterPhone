package com.example.masterphone.Customer;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

//import com.example.cherrycake.Home.HomeActivity;
//import com.example.cherrycake.QuanLy.HomeAdmin;
//import com.example.cherrycake.R;

import com.example.masterphone.HomeDashboard.HomeActivity;
import com.example.masterphone.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    private TextView layoutdk;
    private EditText medtmail, medtpass;
    private Button mbtndangnhap;
    private FirebaseAuth auth;
    private ImageButton mmenu;
    private TextView mtvquenmk;
    private boolean isResendClicked = false;
    private CountDownTimer countDownTimer;
    FirebaseUser mUser;
    Boolean verification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        layoutdk = findViewById(R.id.tvdk);
//        mmenu = findViewById(R.id.menu);
        mtvquenmk = findViewById(R.id.tvquenmk);
        auth = FirebaseAuth.getInstance();
        mUser = auth.getCurrentUser();
        quenmatkhau();
        dangkiListener();
        dangnhaplistener();

        //
//        mbtndangnhap.performClick();


//        menu();
//        onStart();

    }

    private void quenmatkhau() {
        mtvquenmk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                View dialogView = getLayoutInflater().inflate(R.layout.activity_quenmatkhau, null);
                EditText emailbox = dialogView.findViewById(R.id.edtemailquenpass);

                builder.setView(dialogView);
                AlertDialog dialog = builder.create();

                dialogView.findViewById(R.id.btnthoatquenpass).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder d = new AlertDialog.Builder(Login.this);
                        // thiết lập tiêu đề, nội dung, nút button
                        d.setTitle("QUAY LẠI");
                        d.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(Login.this, Login.class);
                                startActivity(intent);
                            }
                        });
                        d.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {


                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();

                            }
                        });
                        d.create().show();

                    }
                });
                dialogView.findViewById(R.id.btnreset).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String userEmail = emailbox.getText().toString();
                        if (TextUtils.isEmpty(userEmail) && !Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
                            Toast.makeText(Login.this, "Vui lòng nhập email đã đăng kí", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        FirebaseAuth auth = FirebaseAuth.getInstance();
//                        String emailAddress = "meomeo2483@gmail.com";

                        auth.sendPasswordResetEmail(userEmail)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                        }
                                        Toast.makeText(Login.this, "Vui lòng kiểm tra gmail của bạn!", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                });
                dialog.show();
            }
        });
    }

    // này là kiểu tài khoản đã đăng nhập rồi thì vô app không cần đăng nhập lại nữa á
    // nhưng mà chưa có được nên từ từ
//    @Override
//    public void onStart() {
//        super.onStart();
//        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = auth.getCurrentUser();
//        if(currentUser != null){
//            Intent intent = new Intent(MainActivity.this, otpactivity.class);
//            startActivity(intent);
//        }
//    }

    private void dangkiListener() {
        layoutdk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
            }
        });

    }

    //    private void menu() {
//        mmenu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Login.this, vechungtoiactivity.class);
//                startActivity(intent);
//            }
//        });
//    }
    private void dangnhaplistener() {
        medtmail = findViewById(R.id.edtemaillogin);
        medtpass = findViewById(R.id.edtpass);
        mbtndangnhap = findViewById(R.id.btndangnhap);
        medtmail.setText("generaltaikhoan@gmail.com");
        medtpass.setText("hehe321");
        mbtndangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth = FirebaseAuth.getInstance();
                String stremail = medtmail.getText().toString();
                String strpass = medtpass.getText().toString();
                if (TextUtils.isEmpty(stremail)) {
                    Toast.makeText(Login.this, "Vui lòng nhập gmail", Toast.LENGTH_SHORT).show();
                    medtmail.requestFocus();
                } else if (TextUtils.isEmpty(strpass)) {
                    Toast.makeText(Login.this, "Vui lòng nhập mật khẩu", Toast.LENGTH_SHORT).show();
                    medtpass.requestFocus();
                } else {
                    auth.signInWithEmailAndPassword(stremail, strpass)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        if ((medtmail.getText().toString()).equals("cherrycake6868@gmail.com") && (medtpass.getText().toString()).equals("hehe321")) {
//                                            Intent intent = new Intent(Login.this, HomeAdmin.class);
//                                            startActivity(intent);
                                        } else {
                                            Boolean verification = auth.getCurrentUser().isEmailVerified();
                                            if (verification == true) {
                                                Intent intent = new Intent(Login.this, HomeActivity.class);
                                                startActivity(intent);
                                            } else {
                                                AlertDialog.Builder d = new AlertDialog.Builder(Login.this);
                                                // thiết lập tiêu đề, nội dung, nút button
                                                d.setTitle("Xác nhận gmail");
                                                d.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        if (!isResendClicked) {   // Nếu button resend chưa được nhấn trong lần nào trước đó
                                                            mUser.sendEmailVerification()
                                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                        @Override
                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                            if (task.isSuccessful()) {
                                                                                Toast.makeText(Login.this, "Vui lòng xác nhận gmail " + mUser.getEmail(), Toast.LENGTH_SHORT).show();
                                                                            } else {
                                                                                Toast.makeText(getApplicationContext(), "Nhận OTP thất bại", Toast.LENGTH_SHORT).show();
                                                                            }
                                                                        }
                                                                    });
                                                        } else {
                                                            Toast.makeText(getApplicationContext(), "Vui lòng đợi trong giây lát", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
                                                d.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        dialog.cancel();
                                                    }
                                                });
                                                d.create().show();
                                            }
                                        }
                                    } else {
                                        Toast.makeText(Login.this, "Email hoặc mật khẩu của bạn không đúng.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
    }
}