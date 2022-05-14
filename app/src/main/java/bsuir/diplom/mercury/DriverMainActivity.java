package bsuir.diplom.mercury;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import bsuir.diplom.mercury.adapters.DriverViewPagerAdapter;

public class DriverMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_main);

        ViewPager viewPager = findViewById(R.id.driver_main_view_pager);
        DriverViewPagerAdapter viewPagerAdapter = new DriverViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setCurrentItem(0);

        TabLayout tabLayout = findViewById(R.id.driver_view_pager_tabs);
        tabLayout.setupWithViewPager(viewPager);
    }
}