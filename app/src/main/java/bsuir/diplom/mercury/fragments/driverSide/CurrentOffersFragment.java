package bsuir.diplom.mercury.fragments.driverSide;

import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import bsuir.diplom.mercury.R;

public class CurrentOffersFragment extends Fragment {
    private final DatabaseReference offersReference = FirebaseDatabase.getInstance().getReference("Offers");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_current_offers, container, false);

        return view;
    }
}