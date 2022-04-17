package bsuir.diplom.mercury.fragments.offerCreation;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import bsuir.diplom.mercury.R;

public class ChangeAddedItemFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_added_item, container, false);

        return view;
    }
}