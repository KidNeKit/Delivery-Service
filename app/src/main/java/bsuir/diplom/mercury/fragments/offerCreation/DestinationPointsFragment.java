package bsuir.diplom.mercury.fragments.offerCreation;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;

import bsuir.diplom.mercury.R;
import bsuir.diplom.mercury.adapters.AddressSuggestAdapter;
import bsuir.diplom.mercury.interfaces.ViewPagerFragmentLifecycle;
import bsuir.diplom.mercury.utils.Constants;

public class DestinationPointsFragment extends Fragment implements ViewPagerFragmentLifecycle {
    private static DestinationPointsFragment instance;

    public static DestinationPointsFragment getInstance() {
        if (instance == null) {
            instance = new DestinationPointsFragment();
        }
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_destination_points, container, false);

        AutoCompleteTextView fromEditText = view.findViewById(R.id.from_edit_text);
        AutoCompleteTextView toEditText = view.findViewById(R.id.to_edit_text);

        AddressSuggestAdapter addressSuggestAdapter = new AddressSuggestAdapter(getContext(), R.layout.single_address_suggest, new ArrayList<>());
        fromEditText.setAdapter(addressSuggestAdapter);
        toEditText.setAdapter(addressSuggestAdapter);

        return view;
    }

    @Override
    public void onPauseFragment(FragmentManager parentFragmentManager) {
        Log.d("lifecycle", "onPause for DestinationPointsFragment");
    }

    @Override
    public void onResumeFragment(FragmentManager parentFragmentManager) {
        Log.d("lifecycle", "onResumeFragment for DestinationPointsFragment");
        Bundle result = new Bundle();
        result.putBoolean(Constants.IS_ALLOWED_NEXT.getMessage(), false);
        result.putBoolean(Constants.IS_ALLOWED_PREV.getMessage(), true);
        parentFragmentManager.setFragmentResult(Constants.BUTTON_SWITCH_BEHAVIOR_REQUEST_KEY.getMessage(), result);
    }
}