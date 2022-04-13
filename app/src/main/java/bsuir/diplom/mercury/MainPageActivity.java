package bsuir.diplom.mercury;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

import bsuir.diplom.mercury.adapters.ItemListAdapter;
import bsuir.diplom.mercury.entities.Item;

public class MainPageActivity extends AppCompatActivity {
    private static final ArrayList<Item> currentItemsList = new ArrayList<>();

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

            ItemListAdapter adapter = new ItemListAdapter(this, R.layout.single_offer_item, currentItemsList);
            itemListView.setAdapter(adapter);

            Log.d("Current items list: ", currentItemsList.toString());
        });
    }
}