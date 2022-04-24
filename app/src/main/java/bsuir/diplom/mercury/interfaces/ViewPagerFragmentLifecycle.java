package bsuir.diplom.mercury.interfaces;

import androidx.fragment.app.FragmentManager;

public interface ViewPagerFragmentLifecycle {
    void onPauseFragment(FragmentManager parentFragmentManager);

    void onResumeFragment(FragmentManager parentFragmentManager);
}