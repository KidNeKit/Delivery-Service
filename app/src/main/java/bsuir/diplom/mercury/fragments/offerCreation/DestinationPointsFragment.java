package bsuir.diplom.mercury.fragments.offerCreation;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import bsuir.diplom.mercury.R;
import bsuir.diplom.mercury.interfaces.ViewPagerFragmentLifecycle;

public class DestinationPointsFragment extends Fragment implements ViewPagerFragmentLifecycle {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_destination_points, container, false);

        return view;
    }

    @Override
    public void onPauseFragment() {
        Log.d("lifecycle", "onPause for DestinationPointsFragment");
    }

    @Override
    public void onResumeFragment() {
        Log.d("lifecycle", "onResumeFragment for DestinationPointsFragment");
    }
}