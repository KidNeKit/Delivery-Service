package bsuir.diplom.mercury.entities;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import bsuir.diplom.mercury.R;
import bsuir.diplom.mercury.entities.enums.Car;
import bsuir.diplom.mercury.entities.enums.Profession;

public class Staff {
    private Integer id;
    private String name;
    private String surname;
    private Profession profession;
    private Car car;
    private Integer photoImageResource;
    private Integer offerCount;
    private Integer commentCount;
    private Double rating;

    public static List<Staff> staffInitList = Arrays.asList(
            new Staff(1, "Никита", "Меньшиков", Profession.LOADER, Car.MEDIUM_WEIGHT, R.mipmap.ic_launcher, 0, 0, 0.0),
            new Staff(2, "Алексей", "Дубаневич", Profession.LOADER, Car.MEDIUM_WEIGHT, R.mipmap.alexey, 0, 0, 0.0),
            new Staff(3, "Владислав", "Павлов", Profession.DRIVER, Car.LIGHT_WEIGHT, R.mipmap.ic_launcher, 0, 0, 0.0),
            new Staff(4, "Роман", "Мосевич", Profession.LOADER, Car.LIGHT_WEIGHT, R.mipmap.ic_launcher, 0, 0, 0.0),
            new Staff(5, "Иван", "Моисеенко", Profession.DRIVER, Car.MEDIUM_WEIGHT, R.mipmap.ic_launcher, 0, 0, 0.0)
    );

    public Staff(Integer id, String name, String surname, Profession profession, Car car, Integer photoImageResource, Integer offerCount, Integer commentCount, Double rating) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.profession = profession;
        this.car = car;
        this.photoImageResource = photoImageResource;
        this.offerCount = offerCount;
        this.commentCount = commentCount;
        this.rating = rating;
    }

    public Staff() {
    }

    public static void insertStaffData() {
        DatabaseReference staffReference = FirebaseDatabase.getInstance().getReference("Staff");
        staffReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Staff> createdStaffList = new ArrayList<>();
                for (DataSnapshot snap : snapshot.getChildren()) {
                    createdStaffList.add(snap.getValue(Staff.class));
                }
                List<Integer> createdStaffIdList = createdStaffList.stream().map(Staff::getId).collect(Collectors.toList());
                staffInitList.forEach(staff -> {
                    if (!createdStaffIdList.contains(staff.getId())) {
                        staffReference.child(staff.getId().toString()).setValue(staff);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Staff", "Cannot initialize basic staff");
            }
        });
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Profession getProfession() {
        return profession;
    }

    public void setProfession(Profession profession) {
        this.profession = profession;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Integer getPhotoImageResource() {
        return photoImageResource;
    }

    public void setPhotoImageResource(Integer photoImageResource) {
        this.photoImageResource = photoImageResource;
    }

    public Integer getOfferCount() {
        return offerCount;
    }

    public void setOfferCount(Integer offerCount) {
        this.offerCount = offerCount;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }
}
