package bsuir.diplom.mercury.fragments.offerCreation;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

import bsuir.diplom.mercury.R;
import bsuir.diplom.mercury.entities.Item;
import bsuir.diplom.mercury.interfaces.ViewPagerFragmentLifecycle;
import bsuir.diplom.mercury.utils.Constants;

public class AddNewItemFragment extends Fragment implements ViewPagerFragmentLifecycle {
    private final ArrayList<Item> currentItemsList = new ArrayList<>();
    private static AddNewItemFragment instance = null;

    private TextInputLayout nameTextInput;
    private TextInputLayout lengthTextInput;
    private TextInputLayout widthTextInput;
    private TextInputLayout heightTextInput;
    private TextInputLayout weightTextInput;

    public static AddNewItemFragment getInstance() {
        if (instance == null) {
            instance = new AddNewItemFragment();
        }
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_new_item, container, false);

        nameTextInput = view.findViewById(R.id.name_input);
        lengthTextInput = view.findViewById(R.id.length_input);
        widthTextInput = view.findViewById(R.id.width_input);
        heightTextInput = view.findViewById(R.id.height_input);
        weightTextInput = view.findViewById(R.id.weight_input);

        Button addItemButton = view.findViewById(R.id.add_item);
        addItemButton.setOnClickListener(view1 -> {
            String currentName = nameTextInput.getEditText().getText().toString();
            String currentLength = lengthTextInput.getEditText().getText().toString();
            String currentWidth = widthTextInput.getEditText().getText().toString();
            String currentHeight = heightTextInput.getEditText().getText().toString();
            String currentWeight = weightTextInput.getEditText().getText().toString();

            Item item = new Item(currentName, Double.valueOf(currentLength), Double.valueOf(currentWidth), Double.valueOf(currentHeight), Double.valueOf(currentWeight));
            currentItemsList.add(item);

            Bundle result = new Bundle();
            result.putBoolean(Constants.IS_ALLOWED_NEXT.getMessage(), true);
            getParentFragmentManager().setFragmentResult(Constants.BUTTON_SWITCH_BEHAVIOR_REQUEST_KEY.getMessage(), result);

            Log.d("Current items list: ", currentItemsList.toString());
        });

        return view;
    }

    @Override
    public void onPauseFragment(FragmentManager parentFragmentManager) {
        Log.d("lifecycle", "onPauseFragment for AddNewItemFragment");
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(Constants.CURRENT_ITEMS_LIST.getMessage(), currentItemsList);
        parentFragmentManager.setFragmentResult(Constants.VIEWPAGER_FRAGMENTS_DATA_TRANSFER_REQUEST_KEY.getMessage(), bundle);
    }

    @Override
    public void onResumeFragment(FragmentManager parentFragmentManager) {
        Log.d("lifecycle", "onResumeFragment for AddNewItemFragment");
        Bundle result = new Bundle();
        result.putBoolean(Constants.IS_ALLOWED_NEXT.getMessage(), currentItemsList.size() > 0);
        result.putBoolean(Constants.IS_ALLOWED_PREV.getMessage(), false);
        parentFragmentManager.setFragmentResult(Constants.BUTTON_SWITCH_BEHAVIOR_REQUEST_KEY.getMessage(), result);
    }
}