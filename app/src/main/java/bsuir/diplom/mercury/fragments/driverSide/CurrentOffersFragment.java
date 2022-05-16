package bsuir.diplom.mercury.fragments.driverSide;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

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
import bsuir.diplom.mercury.utils.CarHelper;

public class CurrentOffersFragment extends Fragment {
    private final DatabaseReference offersReference = FirebaseDatabase.getInstance().getReference("Offers");
    private final FirebaseUser authUser = FirebaseAuth.getInstance().getCurrentUser();
    private final List<Offer> newOffersList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_current_offers, container, false);

        ListView newOffersListView = view.findViewById(R.id.new_offers_list_view);
        DriverOfferListAdapter newOfferListAdapter = new DriverOfferListAdapter(getContext(), R.layout.offer_short_description_layout, newOffersList);
        newOffersListView.setAdapter(newOfferListAdapter);

        Car car = CarHelper.getCarByDriverPhoneNumber(authUser.getPhoneNumber());

        offersReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                newOffersList.clear();
                for (DataSnapshot snap : snapshot.getChildren()) {
                    Offer offer = snap.getValue(Offer.class);
                    if (offer.getChosenCar() == null) {
                        newOffersList.add(offer);
                    }
                }
                newOfferListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("Offer list receiving", "An error due getting offer list");
            }
        });

        return view;
    }
}