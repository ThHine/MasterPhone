package com.example.masterphone.QuanLy.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.masterphone.R;
import com.google.android.material.tabs.TabLayout;

import com.example.masterphone.QuanLy.TabFragment.khohangViewPagerAdapter;
import com.example.masterphone.QuanLy.TabFragment.customViewPager;
public class khohangFragment extends Fragment {
    private TabLayout tabLayout;
    private customViewPager viewPager;
    private View v;
    public khohangFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_quanly_khohang, container, false);
        tabLayout = v.findViewById(R.id.tablayoutkhohang);
        viewPager = v.findViewById(R.id.viewpagerkhohang);

        khohangViewPagerAdapter adapter = new khohangViewPagerAdapter(getChildFragmentManager()
        , FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        return v;
    }
}