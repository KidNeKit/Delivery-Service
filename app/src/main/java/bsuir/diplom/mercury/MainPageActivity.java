package bsuir.diplom.mercury;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import bsuir.diplom.mercury.adapters.ItemListAdapter;
import bsuir.diplom.mercury.adapters.OfferListAdapter;
import bsuir.diplom.mercury.entities.Item;
import bsuir.diplom.mercury.entities.Offer;
import bsuir.diplom.mercury.utils.Constants;

public class MainPageActivity extends AppCompatActivity {
    private final ArrayList<Item> currentItemsList = new ArrayList<>();
    private final List<Offer> offerList = new ArrayList<>();
    private final DatabaseReference curOfferRef = FirebaseDatabase.getInstance().getReference(Constants.CURRENT_OFFERS_DB.getMessage());

    private TextInputLayout nameTextInput;
    private TextInputLayout lengthTextInput;
    private TextInputLayout widthTextInput;
    private TextInputLayout heightTextInput;
    private TextInputLayout weightTextInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        nameTextInput = findViewById(R.id.name_input);
        lengthTextInput = findViewById(R.id.length_input);
        widthTextInput = findViewById(R.id.width_input);
        heightTextInput = findViewById(R.id.height_input);
        weightTextInput = findViewById(R.id.weight_input);

        ListView currentOffersListView = findViewById(R.id.current_offers_list);
        currentOffersListView.setEnabled(false);
        OfferListAdapter offerAdapter = new OfferListAdapter(this, R.layout.single_offer_item, offerList);
        currentOffersListView.setAdapter(offerAdapter);

        curOfferRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap: snapshot.getChildren()) {
                    Offer offer = snap.getValue(Offer.class);
                    offerList.add(offer);
                }
                offerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        ListView itemListView = findViewById(R.id.current_item_list);

        Button addItemButton = findViewById(R.id.add_item);
        addItemButton.setOnClickListener(view -> {
            String currentName = nameTextInput.getEditText().getText().toString();
            String currentLength = lengthTextInput.getEditText().getText().toString();
            String currentWidth = widthTextInput.getEditText().getText().toString();
            String currentHeight = heightTextInput.getEditText().getText().toString();
            String currentWeight = weightTextInput.getEditText().getText().toString();

            Item item = new Item(currentName, Double.valueOf(currentLength), Double.valueOf(currentWidth), Double.valueOf(currentHeight), Double.valueOf(currentWeight));
            currentItemsList.add(item);

            //TODO change the location of adding offer to DB (after payment)
            curOfferRef.push().setValue(new Offer(item));

            ItemListAdapter adapter = new ItemListAdapter(this, R.layout.single_offer_item, currentItemsList);
            itemListView.setAdapter(adapter);

            Log.d("Current items list: ", currentItemsList.toString());
        });
    }
}