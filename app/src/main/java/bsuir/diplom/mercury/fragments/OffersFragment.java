package bsuir.diplom.mercury.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import bsuir.diplom.mercury.R;
import bsuir.diplom.mercury.adapters.OfferListAdapter;
import bsuir.diplom.mercury.entities.Offer;
import bsuir.diplom.mercury.entities.enums.OfferStatus;
import bsuir.diplom.mercury.utils.Constants;

public class OffersFragment extends Fragment {
    private final DatabaseReference currentOffersRef = FirebaseDatabase.getInstance().getReference(Constants.CURRENT_OFFERS_DB.getMessage());

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_offers, container, false);

        ListView inProgressListView = view.findViewById(R.id.in_progress_list_view);
        ListView inProcessingListView = view.findViewById(R.id.in_processing_list_view);
        ListView completedListView = view.findViewById(R.id.completed_list_view);

        ArrayList<Offer> inProgressList = new ArrayList<>();
        ArrayList<Offer> inProcessingList = new ArrayList<>();
        ArrayList<Offer> completedList = new ArrayList<>();

        OfferListAdapter inProgressListAdapter = new OfferListAdapter(requireContext(), R.layout.single_offer_layout, inProgressList, OfferStatus.IN_PROGRESS);
        inProgressListView.setAdapter(inProgressListAdapter);
        OfferListAdapter inProcessingListAdapter = new OfferListAdapter(requireContext(), R.layout.single_offer_layout, inProcessingList, OfferStatus.IN_PROCESSING);
        inProcessingListView.setAdapter(inProcessingListAdapter);
        OfferListAdapter completedListAdapter = new OfferListAdapter(requireContext(), R.layout.single_offer_layout, completedList, OfferStatus.COMPLETED);
        completedListView.setAdapter(completedListAdapter);

        currentOffersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                inProgressList.clear();
                inProcessingList.clear();
                completedList.clear();

                for (DataSnapshot snap : snapshot.getChildren()) {
                    Offer offer = snap.getValue(Offer.class);
                    if (offer == null) {
                        continue;
                    }
                    switch (offer.getOfferStatus()) {
                        case IN_PROGRESS:
                            inProgressList.add(offer);
                            break;
                        case IN_PROCESSING:
                            inProcessingList.add(offer);
                            break;
                        case COMPLETED:
                            completedList.add(offer);
                            break;
                        default:
                            Log.e("Unexpected offer status: ", offer.getOfferStatus().toString());
                            break;
                    }
                }
                inProgressListAdapter.notifyDataSetChanged();
                inProcessingListAdapter.notifyDataSetChanged();
                completedListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Error due getting data from database: ", error.getMessage());
            }
        });

        return view;
    }
}