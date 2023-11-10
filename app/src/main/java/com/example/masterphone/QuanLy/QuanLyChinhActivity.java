package com.example.masterphone.QuanLy;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.masterphone.QuanLy.Fragment.ViewPagerAdapter;
import com.example.masterphone.QuanLy.Fragment.donhangFragment;
import com.example.masterphone.QuanLy.TabFragment.SanPhamFragment;
import com.example.masterphone.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.example.masterphone.QuanLy.TabFragment.customViewPager;


import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class QuanLyChinhActivity extends AppCompatActivity {
    private customViewPager customViewPager;
    private BottomNavigationView bottomNavigationView;
//    MeowBottomNavigation btnav;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quanly_quanlychinh);
//        btnav = findViewById(R.id.btnavadmin);
//        loadFragment(new SanPhamFragment());
//        btnav.show(1,true );
//        btnav.add(new MeowBottomNavigation.Model(1, R.drawable.btnavhome));
//        btnav.add(new MeowBottomNavigation.Model(2, R.drawable.btnavgrid));
////        btnav.add(new MeowBottomNavigation.Model(3, R.drawable.btnavuserinf));
////        btnav.add(new MeowBottomNavigation.Model(4, R.drawable.btnavuserinf));
//        btnav.setEnabled(false);
//
//        btnav.getId();
//        btnav.setOnClickMenuListener(new Function1<MeowBottomNavigation.Model, Unit>() {
//            @Override
//            public Unit invoke(MeowBottomNavigation.Model model) {
//
//                switch (model.getId()){
//                    case 1:
//                        loadFragment(new SanPhamFragment());
////                        loadFragment(new HomeFragmentActivity());
//
////                        Intent homeintent = new Intent(MainActivity.this,);
////                        startActivity(homeintent);
//                        break;
//                    case 2:
//                        loadFragment(new donhangFragment());
////                        Intent categoryintent = new Intent(HomeActivity.this,ProductActivity.class);
////                        startActivity(categoryintent);
//                        break;
//                    case 3:
////                        loadFragment(new userFragment());
////                        loadFragment(new UserFragmentActivity());
////                        Intent favouritelistintent = new Intent(MainActivity.this, );
////                        startActivity(favouritelistintent);
//                        break;
////                    case 4:
////
//////                        mdrawLo.openDrawer(GravityCompat.START);
////                        break;
//                }
//                return null;
//            }
//        });
//        btnav.setOnShowListener(new Function1<MeowBottomNavigation.Model, Unit>() {
//            @Override
//            public Unit invoke(MeowBottomNavigation.Model model) {
//
//                return null;
//            }
//        });

        customViewPager = findViewById(R.id.viewpagerquanly);
        bottomNavigationView = findViewById(R.id.bottomnavquanly);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),
                FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        customViewPager.setAdapter(viewPagerAdapter);
        customViewPager.setPagingEnabled(false);
        //Sự kiện khi vuốt viewPager
        customViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:     //Nếu không có setChecked(true) thì lúc lướt qua lướt lại thì cái thanh bottom nav sẽ không chuyển icon
                        bottomNavigationView.getMenu().findItem(R.id.menukho).setChecked(true);
                        break;
                    case 1:
                        bottomNavigationView.getMenu().findItem(R.id.menudonhang).setChecked(true);
                        break;
                    case 2:
                        bottomNavigationView.getMenu().findItem(R.id.menutaikhoan).setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menukho:
                        customViewPager.setCurrentItem(0);
                        break;
                    case R.id.menudonhang:
                        customViewPager.setCurrentItem(1);
                        break;
                    case R.id.menutaikhoan:
                        customViewPager.setCurrentItem(2);
                        break;
                }
                return true;
            }
        });

        int userId = 0;
        if(userId == 0){

        }
        if (userId == 0) {
            // Vô hiệu hóa chức năng chuyển đến Item 3

            bottomNavigationView.getMenu().findItem(R.id.menutaikhoan).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(@NonNull MenuItem menuItem) {
//                    System.out.println("aaa");
//                    Snackbar.make(viewPager, "Bạn không có quyền truy cập", Snackbar.LENGTH_SHORT).show();
                    Toast.makeText(QuanLyChinhActivity.this, "Bạn không có quyền truy cập", Toast.LENGTH_SHORT).show();
                    return true;
                }
            });

//            viewPager.setIdviewpagerapd(2).setEnabled(false);
//            bottomNavigationView.getMenu().findItem(R.id.menutaikhoan).setEnabled(false);

            // Hiển thị thông báo với Snackbar


            // Hoặc hiển thị thông báo với Toast
            // Toast.makeText(this, "Bạn không có quyền truy cập", Toast.LENGTH_SHORT).show();
        }

    }
    private void loadFragment(Fragment fragment){
        FragmentTransaction fmtrans = getSupportFragmentManager().beginTransaction();
        fmtrans.replace(R.id.homeadmin_fragment,fragment);
        fmtrans.addToBackStack(null);
        fmtrans.commit();
    }
}
