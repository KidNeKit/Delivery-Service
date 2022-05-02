package bsuir.diplom.mercury.fragments.offerCreation;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import bsuir.diplom.mercury.R;
import bsuir.diplom.mercury.interfaces.ViewPagerFragmentLifecycle;

public class PersonalChoosingFragment extends Fragment implements ViewPagerFragmentLifecycle {
    private static ChangeAddedItemFragment instance;

    public static ChangeAddedItemFragment getInstance() {
        if (instance == null) {
            instance = new ChangeAddedItemFragment();
        }
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal_choosing, container, false);

        return view;
    }

    @Override
    public void onPauseFragment(FragmentManager parentFragmentManager) {
        Log.d("lifecycle", "onPause for ChangeAddedItemFragment");
    }

    @Override
    public void onResumeFragment(FragmentManager parentFragmentManager) {
        Log.d("lifecycle", "onResumeFragment for ChangeAddedItemFragment");
    }
}