package bsuir.diplom.mercury.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

import bsuir.diplom.mercury.R;
import bsuir.diplom.mercury.entities.Item;

public class MapFragment extends Fragment {
    private final ArrayList<Item> currentItemsList = new ArrayList<>();

    private TextInputLayout nameTextInput;
    private TextInputLayout lengthTextInput;
    private TextInputLayout widthTextInput;
    private TextInputLayout heightTextInput;
    private TextInputLayout weightTextInput;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        nameTextInput = view.findViewById(R.id.name_input);
        lengthTextInput = view.findViewById(R.id.length_input);
        widthTextInput = view.findViewById(R.id.width_input);
        heightTextInput = view.findViewById(R.id.height_input);
        weightTextInput = view.findViewById(R.id.weight_input);

        /*ListView currentOffersListView = findViewById(R.id.current_offers_list);
        currentOffersListView.setEnabled(false);
        OfferListAdapter offerAdapter = new OfferListAdapter(this, R.layout.single_offer_item, offerList);
        currentOffersListView.setAdapter(offerAdapter);


        ListView itemListView = findViewById(R.id.current_item_list);*/

        Button addItemButton = view.findViewById(R.id.add_item);
        addItemButton.setOnClickListener(view1 -> {
            String currentName = nameTextInput.getEditText().getText().toString();
            String currentLength = lengthTextInput.getEditText().getText().toString();
            String currentWidth = widthTextInput.getEditText().getText().toString();
            String currentHeight = heightTextInput.getEditText().getText().toString();
            String currentWeight = weightTextInput.getEditText().getText().toString();

            Item item = new Item(currentName, Double.valueOf(currentLength), Double.valueOf(currentWidth), Double.valueOf(currentHeight), Double.valueOf(currentWeight));
            currentItemsList.add(item);

            //TODO change the location of adding offer to DB (after payment)
            //curOfferRef.push().setValue(new Offer(item));

            //ItemListAdapter adapter = new ItemListAdapter(this, R.layout.single_offer_item, currentItemsList);
            //itemListView.setAdapter(adapter);

            Log.d("Current items list: ", currentItemsList.toString());
        });

        return view;
    }
}