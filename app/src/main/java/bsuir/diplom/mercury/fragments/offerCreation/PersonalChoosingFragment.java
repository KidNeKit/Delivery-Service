package bsuir.diplom.mercury.fragments.offerCreation;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import bsuir.diplom.mercury.R;
import bsuir.diplom.mercury.adapters.StaffRecyclerViewAdapter;
import bsuir.diplom.mercury.entities.Car;
import bsuir.diplom.mercury.entities.Staff;
import bsuir.diplom.mercury.entities.enums.CarType;
import bsuir.diplom.mercury.entities.enums.Role;
import bsuir.diplom.mercury.interfaces.ViewPagerFragmentLifecycle;

public class PersonalChoosingFragment extends Fragment implements ViewPagerFragmentLifecycle {
    private static PersonalChoosingFragment instance;
    private RadioButton lightWeightCarButton;
    private final List<Staff> staffList = new ArrayList<>();
    private SeekBar loaderSeekBar;
    private StaffRecyclerViewAdapter adapter;
    private CarType selectedCar;

    public static PersonalChoosingFragment getInstance() {
        if (instance == null) {
            instance = new PersonalChoosingFragment();
        }
        return instance;
    }

    private final SeekBar.OnSeekBarChangeListener onSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (fromUser) {
                staffList.removeAll(staffList.stream()
                        .filter(staff -> Role.LOADER.equals(staff.getProfession()))
                        .collect(Collectors.toList()));
                if (selectedCar != null) {
                    loadLoadersForSelectedCar();
                }
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal_choosing, container, false);

        RadioGroup carSelectionRadioGroup = view.findViewById(R.id.car_selection_radio_group);
        lightWeightCarButton = view.findViewById(R.id.light_weight_car_button);
        RecyclerView staffRecyclerView = view.findViewById(R.id.staff_recycler_view);
        loaderSeekBar = view.findViewById(R.id.loader_seek_bar);

        loaderSeekBar.setOnSeekBarChangeListener(onSeekBarChangeListener);
        carSelectionRadioGroup.setOnCheckedChangeListener((radioGroup, checkedId) -> {
            selectedCar = lightWeightCarButton.getId() == checkedId ? CarType.LIGHT_WEIGHT : CarType.MEDIUM_WEIGHT;

            int loadersCount = (int) Car.carList.stream()
                    .filter(car -> car.getCarId().equals(selectedCar.getCarId()))
                    .map(car -> car.getStaffList().stream()
                            .filter(staff -> staff.getProfession().equals(Role.LOADER))
                            .collect(Collectors.toList()))
                    .findFirst().get().size();

            loaderSeekBar.setMax(loadersCount);

            staffList.clear();
            loadDriverForSelectedCar();
            loadLoadersForSelectedCar();
        });

        adapter = new StaffRecyclerViewAdapter(getContext(), staffList);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        staffRecyclerView.setLayoutManager(gridLayoutManager);
        staffRecyclerView.setAdapter(adapter);

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

    private void loadDriverForSelectedCar() {
        Car.carList.stream()
                .filter(car -> car.getCarId().equals(selectedCar.getCarId()))
                .map(car -> car.getStaffList().stream()
                        .filter(staff -> staff.getProfession().equals(Role.DRIVER))
                        .collect(Collectors.toList()))
                .forEach(staffList::addAll);
        adapter.notifyDataSetChanged();
    }

    private void loadLoadersForSelectedCar() {
        Car.carList.stream()
                .filter(car -> car.getCarId().equals(selectedCar.getCarId()))
                .map(car -> car.getStaffList().stream()
                        .filter(staff -> staff.getProfession().equals(Role.LOADER))
                        .sorted(Comparator.comparingDouble(Staff::getRating).reversed())
                        .limit(loaderSeekBar.getProgress())
                        .collect(Collectors.toList()))
                .forEach(staffList::addAll);
        adapter.notifyDataSetChanged();
    }
}