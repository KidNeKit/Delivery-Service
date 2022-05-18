package bsuir.diplom.mercury.fragments.driverSide;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import bsuir.diplom.mercury.R;
import bsuir.diplom.mercury.adapters.DriverOfferListAdapter;
import bsuir.diplom.mercury.entities.Car;
import bsuir.diplom.mercury.entities.Offer;
import bsuir.diplom.mercury.entities.enums.OfferStatus;
import bsuir.diplom.mercury.utils.CarHelper;

public class CurrentOffersFragment extends Fragment {
    private final DatabaseReference offersReference = FirebaseDatabase.getInstance().getReference("Offers");
    private final FirebaseUser authUser = FirebaseAuth.getInstance().getCurrentUser();
    private final List<Offer> newOffersList = new ArrayList<>();
    private final List<Offer> currentOffersList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_current_offers, container, false);

        ListView newOffersListView = view.findViewById(R.id.new_offers_list_view);
        ListView currentOffersListView = view.findViewById(R.id.current_offers_list_view);
        TextView newOffersTextView = view.findViewById(R.id.new_offers_title);
        TextView currentOffersTextView = view.findViewById(R.id.current_offers_title);

        DriverOfferListAdapter newOfferListAdapter = new DriverOfferListAdapter(getContext(), R.layout.offer_short_description_layout, newOffersList);
        newOffersListView.setAdapter(newOfferListAdapter);

        DriverOfferListAdapter currentOfferListAdapter = new DriverOfferListAdapter(getContext(), R.layout.offer_short_description_layout, currentOffersList);
        currentOffersListView.setAdapter(currentOfferListAdapter);

        Car car = CarHelper.getCarByDriverPhoneNumber(authUser.getPhoneNumber());

        offersReference.orderByChild("offerStatus").equalTo(String.valueOf(OfferStatus.IN_PROCESSING)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                newOffersList.clear();
                for (DataSnapshot snap : snapshot.getChildren()) {
                    Offer offer = snap.getValue(Offer.class);
                    if (car.getCarId().equals(offer.getChosenCar().getCarId())) {
                        offer.setOfferId(snap.getKey());
                        newOffersList.add(offer);
                    }
                }
                if (newOffersList.isEmpty()) {
                    newOffersTextView.setVisibility(View.GONE);
                } else newOffersTextView.setVisibility(View.VISIBLE);
                newOfferListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("Offer list receiving", "An error due getting offer list");
            }
        });

        offersReference.orderByChild("offerStatus").equalTo(String.valueOf(OfferStatus.IN_PROGRESS)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                currentOffersList.clear();
                for (DataSnapshot snap : snapshot.getChildren()) {
                    Offer offer = snap.getValue(Offer.class);
                    if (car.getCarId().equals(offer.getChosenCar().getCarId())) {
                        offer.setOfferId(snap.getKey());
                        currentOffersList.add(offer);
                    }
                }
                if (currentOffersList.isEmpty())
                    currentOffersTextView.setVisibility(View.GONE);
                else currentOffersTextView.setVisibility(View.VISIBLE);
                currentOfferListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("Offer list receiving", "An error due getting offer list");
            }
        });

        return view;
    }
}