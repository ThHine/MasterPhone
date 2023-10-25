package com.example.masterphone.Customer;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.masterphone.R;
import com.example.masterphone.HomeDashboard.SanPham.ChiTietSPActivity;
import com.example.masterphone.HomeDashboard.SanPham.ProductGridAdapter;
import com.example.masterphone.HomeDashboard.SanPham.ProductModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Test extends AppCompatActivity implements ProductGridAdapter.ProductCallback{
    ArrayList<ProductModel> lstProductModel;
    ProductGridAdapter productGridAdapter;
    RecyclerView rvListC;
    GridLayoutManager gridLayoutManager= new GridLayoutManager(Test.this,2);
    FirebaseFirestore firestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);

        rvListC = findViewById(R.id.rvGrid);
        firestore = FirebaseFirestore.getInstance();
        firestore.collection("PRODUCTS");
        CollectionReference collectionReference = firestore.collection("PRODUCTS");
        LoadDataMenuBanhKem();

    }
    void LoadDataMenuBanhKem(){
        lstProductModel = new ArrayList<>();
        FirebaseFirestore.getInstance()
                .collection("PRODUCTS").whereEqualTo("category","Bánh kem")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> dsList = queryDocumentSnapshots.getDocuments();
                        for(DocumentSnapshot ds:dsList) {
                            ProductModel productModel = ds.toObject(ProductModel.class);
                            productGridAdapter.add(productModel);
                        }
                    }
                });

        productGridAdapter = new ProductGridAdapter(lstProductModel, this);
        rvListC.setAdapter(productGridAdapter);
        rvListC.setLayoutManager(gridLayoutManager);
    }

    @Override
    public void onItemClick(String ten, int gia, String mota, String anh) {
        Intent i = new Intent(this, ChiTietSPActivity.class);
        i.putExtra("ten",ten);
        i.putExtra("mota",mota);
        i.putExtra("gia",gia);
        i.putExtra("anh",anh);
        startActivity(i);
    }
}
