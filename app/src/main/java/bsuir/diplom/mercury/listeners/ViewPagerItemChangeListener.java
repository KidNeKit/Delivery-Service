package bsuir.diplom.mercury.listeners;

import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import bsuir.diplom.mercury.interfaces.ViewPagerFragmentLifecycle;

public class ViewPagerItemChangeListener implements ViewPager.OnPageChangeListener {
    private final FragmentPagerAdapter fragmentPagerAdapter;
    private int currentPosition = 0;

    public ViewPagerItemChangeListener(FragmentPagerAdapter fragmentPagerAdapter) {
        this.fragmentPagerAdapter = fragmentPagerAdapter;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int newPosition) {
        ViewPagerFragmentLifecycle fragmentToHide = (ViewPagerFragmentLifecycle) fragmentPagerAdapter.getItem(currentPosition);
        fragmentToHide.onPauseFragment();

        ViewPagerFragmentLifecycle fragmentToShow = (ViewPagerFragmentLifecycle) fragmentPagerAdapter.getItem(newPosition);
        fragmentToShow.onResumeFragment();

        currentPosition = newPosition;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
