package bsuir.diplom.mercury.utils;

import bsuir.diplom.mercury.entities.Car;

public class CarHelper {

    public static Car getCarByDriverPhoneNumber(String phoneNumber) {
        return Car.carList.stream()
                .filter(car -> car.getStaffList().stream()
                        .anyMatch(staff -> phoneNumber.equals(staff.getPhoneNumber())))
                .findFirst().orElse(null);
    }
}
