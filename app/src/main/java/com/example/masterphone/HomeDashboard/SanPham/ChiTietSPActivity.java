package com.example.masterphone.HomeDashboard.SanPham;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import com.example.masterphone.HomeDashboard.HomeActivity;
import com.example.masterphone.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.NumberFormat;
import java.util.Locale;

public class ChiTietSPActivity extends AppCompatActivity {

    // Khai báo //
    TextView tvNameDetailC, tvPriceDetailC, tvDescriptionC, tvQuantityC;
    ImageView ivImageDetailC;
    int count = 1;

    // tong gia
    int totalPrice = 0;

    Button btnOrder;

    // firebase
    FirebaseAuth auth;
    private FirebaseFirestore firestore;
    ImageButton imageButtonBack, imageButtonShare;
    CheckBox checkBoxFavourite;
    RadioButton radioButtonSizeS, radioButtonSizeM, radioButtonSizeL, radioButtonSizeXL;
    private CollectionReference favouriteList = FirebaseFirestore.getInstance().collection("FAVOURITES");
    ProductModel productModel = new ProductModel();
    //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homedashboard_sanpham_activity_chitiet);


        // firebase
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

//        getSupportActionBar().hide(); // Ẩn ActionBar //

        // Ánh xạ //
        tvNameDetailC = findViewById(R.id.tvNameDetail);
        tvPriceDetailC = findViewById(R.id.tvPriceDetail);
        tvDescriptionC = findViewById(R.id.tvDetail);

        ivImageDetailC = findViewById(R.id.ivImageDetail);

        tvQuantityC = (TextView) findViewById(R.id.tvQuantity);

        imageButtonBack = findViewById(R.id.ibBack);
        imageButtonShare = findViewById(R.id.ibShare);

        btnOrder = findViewById(R.id.btnOrder);

        checkBoxFavourite = findViewById(R.id.btnFavourite);


        // Load dữ liệu //

        //load user
        String user = FirebaseAuth.getInstance().getCurrentUser().getUid();
        //load tên
        String ten = getIntent().getStringExtra("ten");
        tvNameDetailC.setText(ten);
        productModel.setName(ten);

        //load giá ban đầu (giá trị mặc định là 1)
        changePrice(1);
        int price = getIntent().getIntExtra("gia", 0);

        //load mô tả
        String mota = getIntent().getStringExtra("mota");
        tvDescriptionC.setText(mota);
        productModel.setDescription(mota);

        //load phân loại
        String loai = getIntent().getStringExtra("loai");

        //load ảnh
        String anh = getIntent().getStringExtra("anh");

        Glide.with(this).load(anh).into(ivImageDetailC);
        productModel.setImage(anh);
        //

        // khi nhấn nút thêm vào giỏ hàng
        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });


        //nút back
        imageButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChiTietSPActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
        //

        //nút share
        imageButtonShare.setOnClickListener(view -> {
            ShareApp(ChiTietSPActivity.this, ten);
        });


        // Favourite //

        // Check PRODUCTS có trùng với FAVOURITES bằng cách so sánh Image //
        // Nếu có dữ liệu trong đây thì tiến hành checkbox                //
        // Không có thì tiếp tục xuống dưới                               //
        favouriteList.whereEqualTo("image", anh).whereEqualTo("user", user).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    checkBoxFavourite.setBackgroundResource(R.drawable.ic_favourite2);
                    checkBoxFavourite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                            if (b) {
                                Toast.makeText(ChiTietSPActivity.this, "Xóa khỏi danh sách yêu thích", Toast.LENGTH_SHORT).show();
                                checkBoxFavourite.setBackgroundResource(R.drawable.ic_favourite);
                                removeFavourite(anh);
                            } else {
                                Toast.makeText(ChiTietSPActivity.this, "Thêm vào danh sách yêu thích", Toast.LENGTH_SHORT).show();
                                checkBoxFavourite.setBackgroundResource(R.drawable.ic_favourite2);
                                addFavourite(user, ten, price, mota, loai, anh);
                            }
                        }
                    });
                }

            }
        });
        //


        //nút favourite

        checkBoxFavourite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    Toast.makeText(ChiTietSPActivity.this, "Thêm vào danh sách yêu thích", Toast.LENGTH_SHORT).show();
                    checkBoxFavourite.setBackgroundResource(R.drawable.ic_favourite2);
                    addFavourite(user, ten, price, mota, loai, anh);
                    //
                } else {
                    Toast.makeText(ChiTietSPActivity.this, "Xóa khỏi danh sách yêu thích", Toast.LENGTH_SHORT).show();
                    checkBoxFavourite.setBackgroundResource(R.drawable.ic_favourite);
                    removeFavourite(anh);
                    //
                }
            }
        });

    }



    // Thêm vào danh sách yêu thích //
    public void addFavourite(String nguoidung, String ten, int gia, String mota, String loai, String anh) {
        productModel = new ProductModel(nguoidung, ten, mota, loai, anh, gia);
        favouriteList.add(productModel);
    }

    // Xóa khỏi danh sách yêu thích //
    public void removeFavourite(String anh) {
        favouriteList.whereEqualTo("image", anh).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                String documentId = "";

                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    documentId = documentSnapshot.getId();
                }
                favouriteList.document(documentId).delete();
            }
        });

    }

    // Thay đổi đơn vị tiền tệ và update giá
    public void changePrice(int quantity) {
        int gia = quantity * getIntent().getIntExtra("gia", 0);
        String giatien = NumberFormat.getNumberInstance(Locale.US).format(gia);
        tvPriceDetailC.setText(giatien + " VNĐ");
        productModel.setPrice(getIntent().getIntExtra("gia", 0));
    }

    // Tăng số lượng //
    public void increment(View v) {
        count++;
        changePrice(1);
        tvQuantityC.setText("" + count);
    }

    // Giảm số lượng //
    public void decrement(View v) {
        // Nếu nhập số lượng nhỏ hơn bằng 1 thì set mặt định số lượng bằng 1
        if (count <= 1) {
            count = 1;
            changePrice(1);
        } else {
            count--;
            changePrice(1);
        }
        tvQuantityC.setText("" + count);
    }

    // Share app
    private void ShareApp(Context context, String ten) {
        Intent i = new Intent();
        i.setAction(Intent.ACTION_SEND);
        i.putExtra(Intent.EXTRA_TEXT, ten);
        i.setType("text/plain");
        context.startActivity(i);
    }

    // hàm dùng để lấy totalprice
    public int getTotalPrice() {
        totalPrice = count * getIntent().getIntExtra("gia", 0);
        return totalPrice;
    }
}
