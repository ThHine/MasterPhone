package com.example.masterphone.QuanLy.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.masterphone.Customer.Login;
import com.example.masterphone.HomeDashboard.Fragment.UserFragment;
import com.example.masterphone.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;


public class donhangFragment extends Fragment {

    public donhangFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    Button mbtnLogout;
    View v;
    FirebaseAuth firebaseAuth;
    String userID;
    FirebaseFirestore firestore;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_quanly_donhang, container, false);
        mbtnLogout = v.findViewById(R.id.btnLogout);
        firestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        userID = firebaseAuth.getCurrentUser().getUid();
        mbtnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                Intent intentSignOut = new Intent(donhangFragment.this.getActivity(), Login.class);
                startActivity(intentSignOut);
            }
        });
        return v;
    }
}