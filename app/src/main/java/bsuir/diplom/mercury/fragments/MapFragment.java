package bsuir.diplom.mercury.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import bsuir.diplom.mercury.R;
import bsuir.diplom.mercury.adapters.OfferCreationViewPager;
import bsuir.diplom.mercury.listeners.ViewPagerItemChangeListener;
import bsuir.diplom.mercury.utils.Constants;

public class MapFragment extends Fragment {
    public final static int NEXT_FRAGMENT_BUTTON_ID = R.id.next_button;
    public final static int PREVIOUS_FRAGMENT_BUTTON_ID = R.id.previous_button;

    private boolean isAllowedNext;
    private boolean isAllowedPrev;

    private ImageButton nextFragmentButton;
    private ImageButton previousFragmentButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        ViewPager viewPager = view.findViewById(R.id.offer_creation_view_pager);
        OfferCreationViewPager offerCreationViewPagerAdapter = new OfferCreationViewPager(getParentFragmentManager());
        viewPager.setAdapter(offerCreationViewPagerAdapter);
        viewPager.setCurrentItem(0);
        viewPager.addOnPageChangeListener(new ViewPagerItemChangeListener(offerCreationViewPagerAdapter));
        //todo disable view pager swiping (maybe by using ViewPager2)
        //todo implementing dots

        nextFragmentButton = view.findViewById(R.id.next_button);
        previousFragmentButton = view.findViewById(R.id.previous_button);
        disableAllButtons();

        getParentFragmentManager().setFragmentResultListener(Constants.FRAGMENT_DATA_TRANSFER_REQUEST_KEY.getMessage(), this, (requestKey, bundle) -> {
            if (bundle.containsKey(Constants.IS_ALLOWED_NEXT.getMessage())) {
                isAllowedNext = bundle.getBoolean(Constants.IS_ALLOWED_NEXT.getMessage());
                if (isAllowedNext) {
                    enableButton(NEXT_FRAGMENT_BUTTON_ID);
                } else {
                    disableButton(NEXT_FRAGMENT_BUTTON_ID);
                }
            }
            if (bundle.containsKey(Constants.IS_ALLOWED_PREV.getMessage())) {
                isAllowedPrev = bundle.getBoolean(Constants.IS_ALLOWED_PREV.getMessage());
                if (isAllowedPrev) {
                    enableButton(PREVIOUS_FRAGMENT_BUTTON_ID);
                } else {
                    disableButton(PREVIOUS_FRAGMENT_BUTTON_ID);
                }
            }
        });

        //todo remake rendering when swiping
        nextFragmentButton.setOnClickListener(nextListener -> {
            int currentItem = viewPager.getCurrentItem();
            if (currentItem != viewPager.getChildCount()) {
                viewPager.setCurrentItem(++currentItem);
            } else {
                Log.d("offer view pager", "it is last fragment");
            }
        });

        previousFragmentButton.setOnClickListener(prev -> {
            int currentItem = viewPager.getCurrentItem();
            if (currentItem != 0) {
                viewPager.setCurrentItem(--currentItem);
            } else {
                Log.d("offer view pager", "it is first fragment");
            }
        });
        return view;
    }

    public void enableButton(int buttonId) {
        switch (buttonId) {
            case PREVIOUS_FRAGMENT_BUTTON_ID:
                previousFragmentButton.setImageResource(R.drawable.ic_baseline_arrow_back_24);
                previousFragmentButton.setEnabled(true);
                break;
            case NEXT_FRAGMENT_BUTTON_ID:
                nextFragmentButton.setImageResource(R.drawable.ic_baseline_arrow_forward_24);
                nextFragmentButton.setEnabled(true);
                break;
        }
    }

    public void disableButton(int buttonId) {
        switch (buttonId) {
            case PREVIOUS_FRAGMENT_BUTTON_ID:
                previousFragmentButton.setImageResource(R.drawable.ic_baseline_arrow_back_inactive_24);
                previousFragmentButton.setEnabled(false);
                break;
            case NEXT_FRAGMENT_BUTTON_ID:
                nextFragmentButton.setImageResource(R.drawable.ic_baseline_arrow_forward_inactive_24);
                nextFragmentButton.setEnabled(false);
                break;
        }
    }

    public void disableAllButtons() {
        disableButton(PREVIOUS_FRAGMENT_BUTTON_ID);
        disableButton(NEXT_FRAGMENT_BUTTON_ID);
    }
}