package bsuir.diplom.mercury.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import bsuir.diplom.mercury.fragments.offerCreation.AddNewItemFragment;
import bsuir.diplom.mercury.fragments.offerCreation.ChangeAddedItemFragment;
import bsuir.diplom.mercury.fragments.offerCreation.DestinationPointsFragment;

@SuppressWarnings("ConstantConditions")
public class OfferCreationViewPager extends FragmentPagerAdapter {

    public OfferCreationViewPager(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new AddNewItemFragment();
            case 1:
                return new ChangeAddedItemFragment();
            case 2:
                return new DestinationPointsFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
