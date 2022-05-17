package bsuir.diplom.mercury.adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import bsuir.diplom.mercury.fragments.MapFragment;
import bsuir.diplom.mercury.fragments.driverSide.CurrentOffersFragment;

public class DriverViewPagerAdapter extends FragmentPagerAdapter {

    public DriverViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new MapFragment();
            case 1:
                return new CurrentOffersFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        super.getPageTitle(position);
        switch (position) {
            case 0:
                return "Карта";
            case 1:
                return "Текущие заказы";
        }
        return null;
    }
}

