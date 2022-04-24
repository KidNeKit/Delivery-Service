package bsuir.diplom.mercury.listeners;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import bsuir.diplom.mercury.interfaces.ViewPagerFragmentLifecycle;

public class ViewPagerItemChangeListener implements ViewPager.OnPageChangeListener {
    private final FragmentPagerAdapter fragmentPagerAdapter;
    private final FragmentManager parentFragmentManager;
    private int currentPosition = 0;

    public ViewPagerItemChangeListener(FragmentPagerAdapter fragmentPagerAdapter, FragmentManager parentFragmentManager) {
        this.fragmentPagerAdapter = fragmentPagerAdapter;
        this.parentFragmentManager = parentFragmentManager;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int newPosition) {
        ViewPagerFragmentLifecycle fragmentToHide = (ViewPagerFragmentLifecycle) fragmentPagerAdapter.getItem(currentPosition);
        fragmentToHide.onPauseFragment(parentFragmentManager);

        ViewPagerFragmentLifecycle fragmentToShow = (ViewPagerFragmentLifecycle) fragmentPagerAdapter.getItem(newPosition);
        fragmentToShow.onResumeFragment(parentFragmentManager);

        currentPosition = newPosition;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
