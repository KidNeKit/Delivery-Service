package bsuir.diplom.mercury.fragments.offerCreation;

import android.location.Address;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.yandex.mapkit.geometry.Point;

import java.util.ArrayList;

import bsuir.diplom.mercury.R;
import bsuir.diplom.mercury.adapters.AddressSuggestAdapter;
import bsuir.diplom.mercury.interfaces.ViewPagerFragmentLifecycle;
import bsuir.diplom.mercury.utils.Constants;

public class DestinationPointsFragment extends Fragment implements ViewPagerFragmentLifecycle {
    private static DestinationPointsFragment instance;
    private Point fromPoint;
    private Point toPoint;

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
        Button createOfferButton = view.findViewById(R.id.create_offer);

        AddressSuggestAdapter addressSuggestAdapter = new AddressSuggestAdapter(getContext(), R.layout.single_address_suggest, new ArrayList<>());
        fromEditText.setAdapter(addressSuggestAdapter);
        toEditText.setAdapter(addressSuggestAdapter);

        fromEditText.setOnItemClickListener((adapterView, view1, i, l) -> {
            Address address = (Address) adapterView.getItemAtPosition(i);
            fromPoint = new Point(address.getLatitude(), address.getLongitude());
        });

        toEditText.setOnItemClickListener((adapterView, view1, i, l) -> {
            Address address = (Address) adapterView.getItemAtPosition(i);
            toPoint = new Point(address.getLatitude(), address.getLongitude());
        });

        createOfferButton.setOnClickListener(view1 -> {
            if (fromPoint == null || toPoint == null) {
                return;
            }
            Log.d("Create Offer", "offer creation");
        });

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