package bsuir.diplom.mercury.fragments.offerCreation;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import bsuir.diplom.mercury.R;
import bsuir.diplom.mercury.interfaces.ViewPagerFragmentLifecycle;

public class ChangeAddedItemFragment extends Fragment implements ViewPagerFragmentLifecycle {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_added_item, container, false);

        return view;
    }

    @Override
    public void onPauseFragment() {
        Log.d("lifecycle", "onPause for ChangeAddedItemFragment");
    }

    @Override
    public void onResumeFragment() {
        Log.d("lifecycle", "onResumeFragment for ChangeAddedItemFragment");
    }
}