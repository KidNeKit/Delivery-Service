package bsuir.diplom.mercury.fragments.offerCreation;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;
import java.util.List;

import bsuir.diplom.mercury.R;
import bsuir.diplom.mercury.adapters.ItemListAdapter;
import bsuir.diplom.mercury.entities.Item;
import bsuir.diplom.mercury.interfaces.ViewPagerFragmentLifecycle;
import bsuir.diplom.mercury.utils.Constants;

public class ChangeAddedItemFragment extends Fragment implements ViewPagerFragmentLifecycle {
    private final List<Item> currentItemsList = new ArrayList<>();
    private static ChangeAddedItemFragment instance;
    private ItemListAdapter itemListAdapter;

    public static ChangeAddedItemFragment getInstance() {
        if (instance == null) {
            instance = new ChangeAddedItemFragment();
        }
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_added_item, container, false);

        setFragmentDataTransferResultListener();

        ListView currentItemsListView = view.findViewById(R.id.current_items_list_view);
        itemListAdapter = new ItemListAdapter(requireContext(), R.layout.single_offer_item, currentItemsList);
        currentItemsListView.setAdapter(itemListAdapter);

        return view;
    }


    @Override
    public void onPauseFragment(FragmentManager parentFragmentManager) {
        Log.d("lifecycle", "onPause for ChangeAddedItemFragment");
    }

    @Override
    public void onResumeFragment(FragmentManager parentFragmentManager) {
        Log.d("lifecycle", "onResumeFragment for ChangeAddedItemFragment");
        Bundle result = new Bundle();
        result.putBoolean(Constants.IS_ALLOWED_NEXT.getMessage(), currentItemsList.size() > 0);
        result.putBoolean(Constants.IS_ALLOWED_PREV.getMessage(), true);
        parentFragmentManager.setFragmentResult(Constants.BUTTON_SWITCH_BEHAVIOR_REQUEST_KEY.getMessage(), result);

        itemListAdapter.notifyDataSetChanged();
    }

    private void setFragmentDataTransferResultListener() {
        getParentFragmentManager().setFragmentResultListener(Constants.VIEWPAGER_FRAGMENTS_DATA_TRANSFER_REQUEST_KEY.getMessage(), this, (requestKey, bundle) -> {
            currentItemsList.clear();
            currentItemsList.addAll(bundle.getParcelableArrayList(Constants.CURRENT_ITEMS_LIST.getMessage()));
        });
    }
}