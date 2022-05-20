package bsuir.diplom.mercury.entities;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import bsuir.diplom.mercury.entities.enums.Role;

public class User {
    private String phoneNumber;
    private String surname;
    private String name;
    private Role role;

    public static List<User> driverUserInitList = Collections.singletonList(
            new User("+375336391457", "Меньшиков", "Никита", Role.DRIVER)
    );

    public User(String phoneNumber, String surname, String name) {
        this.phoneNumber = phoneNumber;
        this.surname = surname;
        this.name = name;
        this.role = Role.USER;
    }

    public User(String phoneNumber, String surname, String name, Role role) {
        this.phoneNumber = phoneNumber;
        this.surname = surname;
        this.name = name;
        this.role = role;
    }

    public User() {
    }

    public static void insertUserDriversAccounts() {
        DatabaseReference userReference = FirebaseDatabase.getInstance().getReference("Users");
        userReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<User> createdUserList = new ArrayList<>();
                for (DataSnapshot snap : snapshot.getChildren()) {
                    createdUserList.add(snap.getValue(User.class));
                }
                List<String> createdUserPhoneNumbersList = createdUserList.stream().map(User::getPhoneNumber).collect(Collectors.toList());
                driverUserInitList.forEach(driver -> {
                    if (!createdUserPhoneNumbersList.contains(driver.getPhoneNumber())) {
                        userReference.child(driver.getPhoneNumber()).setValue(driver);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Staff", "Cannot create user accounts for drivers");
            }
        });
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
