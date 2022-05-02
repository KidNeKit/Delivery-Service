package bsuir.diplom.mercury.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import bsuir.diplom.mercury.fragments.offerCreation.AddNewItemFragment;
import bsuir.diplom.mercury.fragments.offerCreation.ChangeAddedItemFragment;
import bsuir.diplom.mercury.fragments.offerCreation.DestinationPointsFragment;
import bsuir.diplom.mercury.fragments.offerCreation.PersonalChoosingFragment;

public class OfferCreationViewPager extends FragmentPagerAdapter {

    public OfferCreationViewPager(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return AddNewItemFragment.getInstance();
            case 1:
                return ChangeAddedItemFragment.getInstance();
            case 2:
                return PersonalChoosingFragment.getInstance();
            case 3:
                return DestinationPointsFragment.getInstance();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }
}
