package com.example.masterphone.DonHang;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.masterphone.GioHang.ThanhToan.ThanhToanAdapter;
import com.example.masterphone.GioHang.ThanhToan.ThanhToanModel;
import com.example.masterphone.HomeDashboard.HomeActivity;
import com.example.masterphone.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HistoryOrderDetailActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<HistoryOrderDetailModel> historyOrderDetailModelList;
    HistoryOrderDetailAdapter historyOrderDetailAdapter;
    ImageView btnBack;

    private FirebaseFirestore firestore;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.donhang_activity_historyorderdetail);
        firestore = FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.rcvHistoryOrderDetail);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        historyOrderDetailModelList = new ArrayList<>();

        btnBack = findViewById(R.id.btnBackOfLSDH);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HistoryOrderDetailActivity.this, HistoryOrderActivity.class);
                startActivity(i);
            }
        });

        historyOrderDetailAdapter = new HistoryOrderDetailAdapter(this, historyOrderDetailModelList);
        recyclerView.setAdapter(historyOrderDetailAdapter);
        String madonhang = getIntent().getStringExtra("madonhang");
        firestore.collection("ORDERS").document(madonhang)
                .collection("ORDERINFO").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot doc : task.getResult().getDocuments()) {
                                HistoryOrderDetailModel historyOrderDetailModel = doc.toObject(HistoryOrderDetailModel.class);
                                historyOrderDetailModelList.add(historyOrderDetailModel);
                                historyOrderDetailAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                });
    }
}
