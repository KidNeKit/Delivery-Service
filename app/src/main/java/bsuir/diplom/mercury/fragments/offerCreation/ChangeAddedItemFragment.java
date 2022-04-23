package bsuir.diplom.mercury.fragments.offerCreation;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import bsuir.diplom.mercury.R;
import bsuir.diplom.mercury.fragments.MapFragment;
import bsuir.diplom.mercury.interfaces.ViewPagerFragmentLifecycle;

public class ChangeAddedItemFragment extends Fragment implements ViewPagerFragmentLifecycle {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_added_item, container, false);

        /*ListView currentOffersListView = findViewById(R.id.current_offers_list);
        currentOffersListView.setEnabled(false);
        OfferListAdapter offerAdapter = new OfferListAdapter(this, R.layout.single_offer_item, offerList);
        currentOffersListView.setAdapter(offerAdapter);

        ListView itemListView = findViewById(R.id.current_item_list);*/
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