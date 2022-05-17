package bsuir.diplom.mercury.utils;

import bsuir.diplom.mercury.entities.Car;
import bsuir.diplom.mercury.entities.enums.CarType;

public class CarHelper {

    public static Car getCarByDriverPhoneNumber(String phoneNumber) {
        return Car.carList.stream()
                .filter(car -> car.getStaffList().stream()
                        .anyMatch(staff -> phoneNumber.equals(staff.getPhoneNumber())))
                .findFirst().orElse(null);
    }

    public static Car getCarByCarType(CarType carType) {
        return Car.carList.stream()
                .filter(car -> carType.equals(car.getCarType()))
                .findFirst().orElse(null);
    }
}
