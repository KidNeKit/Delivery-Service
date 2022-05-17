package bsuir.diplom.mercury.entities.enums;

public enum CarType {
    LIGHT_WEIGHT(1),
    MEDIUM_WEIGHT(2);

    private final Integer carId;

    CarType(int carId) {
        this.carId = carId;
    }

    public Integer getCarId() {
        return carId;
    }
}
