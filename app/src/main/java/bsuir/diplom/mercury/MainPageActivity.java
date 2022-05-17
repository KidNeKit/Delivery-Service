package bsuir.diplom.mercury;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import bsuir.diplom.mercury.adapters.ViewPagerAdapter;
import bsuir.diplom.mercury.entities.Car;

public class MainPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        Car.initCarList();

        ViewPager viewPager = findViewById(R.id.main_view_pager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setCurrentItem(0);

        TabLayout tabLayout = findViewById(R.id.view_pager_tabs);
        tabLayout.setupWithViewPager(viewPager);
    }
}