package com.example.masterphone.QuanLy.TabFragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.masterphone.HomeDashboard.SanPham.ChiTietSPActivity;
import com.example.masterphone.QuanLy.Fragment.addProductFragment;
import com.example.masterphone.QuanLy.Fragment.editProductFragment;

import com.example.masterphone.QuanLy.SanPhamAdapter;
import com.example.masterphone.QuanLy.SanPhamModel;
import com.example.masterphone.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SanPhamFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SanPhamFragment extends Fragment implements SanPhamAdapter.onClickItem{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SanPhamFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SanPhamFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SanPhamFragment newInstance(String param1, String param2) {
        SanPhamFragment fragment = new SanPhamFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    View v;
    FirebaseFirestore firestore;
    FirebaseAuth firebaseAuth;
    String userID;

    ImageView mimgSP;
    FloatingActionButton mbtnThem;
    RecyclerView recyclerView;
    List<SanPhamModel> SPModelList;
    SanPhamAdapter SPAdapter;
    //firestore
    FirebaseAuth auth;
    private Uri imageUri = null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.quanly_fragment_sanpham, container, false);

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
//        firebaseAuth = FirebaseAuth.getInstance();
//        userID = firebaseAuth.getCurrentUser().getUid();
//
        recyclerView = v.findViewById(R.id.rcvSP);
        recyclerView.setLayoutManager(new LinearLayoutManager(v.getContext()));
        SPModelList = new ArrayList<>();

        CollectionReference SPcollectionReference = firestore.collection("PRODUCTS");
        SPcollectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                for(DocumentSnapshot doc:list){
                    SanPhamModel spModel = doc.toObject(SanPhamModel.class);
                    SPAdapter.add(spModel);
                }
            }
        });
        SPAdapter = new SanPhamAdapter(/*v.getContext()*/getActivity(), this, SPModelList);
        SPAdapter = new SanPhamAdapter(getActivity(), SPModelList, this);
        recyclerView.setAdapter(SPAdapter);

        mbtnThem = v.findViewById(R.id.btnFloatingAddProduct);
        mbtnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View n) {
                //Tắt hiển thị khoFragment để addFragment hiện lên

                loadFragment(new addProductFragment());
            }
        });
        return v;
    }
    private void loadFragment(Fragment fragment){
        FragmentTransaction fmtrans = getParentFragmentManager().beginTransaction();
        fmtrans.replace(R.id.homeadmin_fragment, fragment);
        fmtrans.addToBackStack(null);
        fmtrans.commit();
    }

    @Override
    public void onClickGetInfo(String id, String name, String category, int price, String description, String image) {
        editProductFragment fragment = new editProductFragment();

        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        bundle.putString("name", name);
        bundle.putString("category", category);
        bundle.putInt("price", price);
        bundle.putString("description", description);
        bundle.putString("image", image);
//        Toast.makeText(v.getContext(), image, Toast.LENGTH_SHORT).show();
        fragment.setArguments(bundle);
        loadFragment(fragment);
    }


}