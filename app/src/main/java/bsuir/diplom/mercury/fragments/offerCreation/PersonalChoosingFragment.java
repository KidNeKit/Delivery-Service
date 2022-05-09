package bsuir.diplom.mercury.fragments.offerCreation;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import bsuir.diplom.mercury.R;
import bsuir.diplom.mercury.interfaces.ViewPagerFragmentLifecycle;

public class PersonalChoosingFragment extends Fragment implements ViewPagerFragmentLifecycle {
    private static PersonalChoosingFragment instance;
    private RadioGroup carSelectionRadioGroup;
    private RadioButton mediumWeightCarButton;
    private RadioButton lightWeightCarButton;

    public static PersonalChoosingFragment getInstance() {
        if (instance == null) {
            instance = new PersonalChoosingFragment();
        }
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal_choosing, container, false);

        carSelectionRadioGroup = view.findViewById(R.id.car_selection_radio_group);
        mediumWeightCarButton = view.findViewById(R.id.medium_weight_car_button);
        lightWeightCarButton = view.findViewById(R.id.light_weight_car_button);

        mediumWeightCarButton.setOnClickListener(onCarSelectedListener);
        lightWeightCarButton.setOnClickListener(onCarSelectedListener);

        SeekBar loaderSeekBar = view.findViewById(R.id.loader_seek_bar);

        loaderSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        return view;
    }

    @Override
    public void onPauseFragment(FragmentManager parentFragmentManager) {
        Log.d("lifecycle", "onPause for PersonalChoosingFragment");
    }

    @Override
    public void onResumeFragment(FragmentManager parentFragmentManager) {
        Log.d("lifecycle", "onResumeFragment for PersonalChoosingFragment");
    }

    private final View.OnClickListener onCarSelectedListener = view -> {
        switch (carSelectionRadioGroup.getCheckedRadioButtonId()) {
            case R.id.medium_weight_car_button:
                Toast.makeText(getContext(), "Medium weight", Toast.LENGTH_SHORT);
                break;
            case R.id.light_weight_car_button:
                Toast.makeText(getContext(), "Light weight", Toast.LENGTH_SHORT);
                break;
        }
    };
}