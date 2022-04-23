package bsuir.diplom.mercury.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

import bsuir.diplom.mercury.R;
import bsuir.diplom.mercury.adapters.OfferCreationViewPager;
import bsuir.diplom.mercury.adapters.ViewPagerAdapter;
import bsuir.diplom.mercury.entities.Item;

public class MapFragment extends Fragment {

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
        //todo disable view pager swiping (maybe by using ViewPager2)
        //todo implementing dots

        nextFragmentButton = view.findViewById(R.id.next_button);
        previousFragmentButton = view.findViewById(R.id.previous_button);

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
}