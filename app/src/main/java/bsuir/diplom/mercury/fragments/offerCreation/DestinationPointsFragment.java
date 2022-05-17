package bsuir.diplom.mercury.fragments.offerCreation;

import android.location.Address;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import bsuir.diplom.mercury.R;
import bsuir.diplom.mercury.adapters.AddressSuggestAdapter;
import bsuir.diplom.mercury.entities.dto.AddressDTO;
import bsuir.diplom.mercury.entities.Car;
import bsuir.diplom.mercury.entities.Item;
import bsuir.diplom.mercury.entities.Offer;
import bsuir.diplom.mercury.entities.enums.OfferStatus;
import bsuir.diplom.mercury.interfaces.ViewPagerFragmentLifecycle;
import bsuir.diplom.mercury.utils.Constants;

public class DestinationPointsFragment extends Fragment implements ViewPagerFragmentLifecycle {
    private static DestinationPointsFragment instance;
    private final ArrayList<Item> currentItemsList = new ArrayList<>();
    private Address fromAddress;
    private Address toAddress;

    private DatabaseReference offersReference = FirebaseDatabase.getInstance().getReference("Offers");

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

        setFragmentDataTransferResultListener();

        AddressSuggestAdapter addressSuggestAdapter = new AddressSuggestAdapter(getContext(), R.layout.single_address_suggest, new ArrayList<>());
        fromEditText.setAdapter(addressSuggestAdapter);
        toEditText.setAdapter(addressSuggestAdapter);

        fromEditText.setOnItemClickListener((adapterView, view1, i, l) -> fromAddress = (Address) adapterView.getItemAtPosition(i));

        toEditText.setOnItemClickListener((adapterView, view1, i, l) -> toAddress = (Address) adapterView.getItemAtPosition(i));

        createOfferButton.setOnClickListener(view1 -> {
            if (fromAddress == null || toAddress == null) {
                Toast.makeText(getContext(), "Обе точки доставки должны быть проставлены", Toast.LENGTH_SHORT).show();
                return;
            }

            Offer offer = new Offer("", OfferStatus.IN_PROCESSING, Car.carList.get(0), new AddressDTO(fromAddress), new AddressDTO(toAddress), currentItemsList);
            offersReference.push().setValue(offer);
            Log.d("Create Offer", "Offer created successfully: " + offer.toString());
        });

        return view;
    }

    private void setFragmentDataTransferResultListener() {
        getParentFragmentManager().setFragmentResultListener(Constants.CURRENT_ITEM_LIST_REQUEST_KEY.getMessage(), this, (requestKey, bundle) -> {
            currentItemsList.clear();
            currentItemsList.addAll(bundle.getParcelableArrayList(Constants.CURRENT_ITEMS_LIST.getMessage()));
            Log.d(Constants.CURRENT_ITEMS_LIST.getMessage(), currentItemsList.toString());
        });
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