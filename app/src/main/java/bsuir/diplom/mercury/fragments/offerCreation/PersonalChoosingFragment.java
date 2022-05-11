package bsuir.diplom.mercury.fragments.offerCreation;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import bsuir.diplom.mercury.R;
import bsuir.diplom.mercury.adapters.StaffRecyclerViewAdapter;
import bsuir.diplom.mercury.entities.Staff;
import bsuir.diplom.mercury.entities.enums.Car;
import bsuir.diplom.mercury.entities.enums.Profession;
import bsuir.diplom.mercury.interfaces.ViewPagerFragmentLifecycle;

public class PersonalChoosingFragment extends Fragment implements ViewPagerFragmentLifecycle {
    private static PersonalChoosingFragment instance;
    private RadioButton lightWeightCarButton;
    private final List<Staff> staffList = new ArrayList<>();
    private SeekBar loaderSeekBar;
    private StaffRecyclerViewAdapter adapter;
    private Car selectedCar;

    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private final DatabaseReference referenceStaff = database.getReference("Staff");

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
                        .filter(staff -> Profession.LOADER.equals(staff.getProfession()))
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
            selectedCar = lightWeightCarButton.getId() == checkedId ? Car.LIGHT_WEIGHT : Car.MEDIUM_WEIGHT;

            int loadersCount = (int) Staff.staffInitList.stream()
                    .filter(staff -> selectedCar.equals(staff.getCar()) && Profession.LOADER.equals(staff.getProfession()))
                    .count();
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
        referenceStaff.orderByChild("profession").equalTo(Profession.DRIVER.toString()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()) {
                    Staff staff = snap.getValue(Staff.class);
                    if (selectedCar.equals(staff.getCar())) {
                        staffList.add(staff);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("PersonalChoosingFragment", error.getMessage());
            }
        });
    }

    private void loadLoadersForSelectedCar() {
        referenceStaff.orderByChild("profession").equalTo(Profession.LOADER.toString()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Staff> loadersList = new ArrayList<>();
                for (DataSnapshot snap : snapshot.getChildren()) {
                    Staff staff = snap.getValue(Staff.class);
                    loadersList.add(staff);
                }
                staffList.addAll(loadersList.stream()
                        .filter(staff -> selectedCar.equals(staff.getCar()))
                        .sorted(Comparator.comparingDouble(Staff::getRating).reversed())
                        .limit(loaderSeekBar.getProgress())
                        .collect(Collectors.toList()));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("PersonalChoosingFragment", error.getMessage());
            }
        });
    }
}