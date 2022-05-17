package bsuir.diplom.mercury.entities;

import android.os.Parcel;
import android.os.Parcelable;
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

import bsuir.diplom.mercury.BuildConfig;
import bsuir.diplom.mercury.R;
import bsuir.diplom.mercury.entities.enums.CarType;
import bsuir.diplom.mercury.entities.enums.Role;

public class Car implements Parcelable {
    private Integer carId;
    private String carName;
    private CarType carType;
    private List<Staff> staffList;

    public static List<Car> carList = Arrays.asList(
            new Car(1, "Lada Granta", CarType.LIGHT_WEIGHT, Arrays.asList(
                    new Staff(1, "Владислав", "Павлов", "", Role.DRIVER, R.mipmap.ic_launcher, 0, 0, 0.0),
                    new Staff(2, "Роман", "Мосевич", "", Role.LOADER, R.mipmap.ic_launcher, 0, 0, 0.0)
            )),
            new Car(2, "Hyundai Mighty", CarType.MEDIUM_WEIGHT, Arrays.asList(
                    new Staff(1, "Никита", "Меньшиков", BuildConfig.DRIVER_PHONE_NUMBER, Role.LOADER, R.mipmap.ic_launcher, 0, 0, 0.0),
                    new Staff(2, "Алексей", "Дубаневич", "", Role.LOADER, R.mipmap.alexey, 0, 0, 0.0),
                    new Staff(3, "Иван", "Моисеенко", "", Role.DRIVER, R.mipmap.ic_launcher, 0, 0, 0.0)
            ))
    );

    public Car(Integer carId, String carName, CarType carType, List<Staff> staffList) {
        this.carId = carId;
        this.carName = carName;
        this.carType = carType;
        this.staffList = staffList;
    }

    public Car() {
    }

    protected Car(Parcel in) {
        if (in.readByte() == 0) {
            carId = null;
        } else {
            carId = in.readInt();
        }
        carName = in.readString();
        staffList = in.createTypedArrayList(Staff.CREATOR);
    }

    public static final Creator<Car> CREATOR = new Creator<Car>() {
        @Override
        public Car createFromParcel(Parcel in) {
            return new Car(in);
        }

        @Override
        public Car[] newArray(int size) {
            return new Car[size];
        }
    };

    public static void initCarList() {
        DatabaseReference staffReference = FirebaseDatabase.getInstance().getReference("Cars");
        staffReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Car> existentCarList = new ArrayList<>();
                for (DataSnapshot snap : snapshot.getChildren()) {
                    existentCarList.add(snap.getValue(Car.class));
                }
                List<Integer> existentCarIdList = existentCarList.stream().map(Car::getCarId).collect(Collectors.toList());
                carList.forEach(car -> {
                    if (!existentCarIdList.contains(car.getCarId())) {
                        staffReference.child(car.getCarId().toString()).setValue(car);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Staff", "Cannot initialize basic staff");
            }
        });
    }

    public Integer getCarId() {
        return carId;
    }

    public void setCarId(Integer carId) {
        this.carId = carId;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public CarType getCarType() {
        return carType;
    }

    public void setCarType(CarType carType) {
        this.carType = carType;
    }

    public List<Staff> getStaffList() {
        return staffList;
    }

    public void setStaffList(List<Staff> staffList) {
        this.staffList = staffList;
    }

    @Override
    public String toString() {
        return "Car{" +
                "carId=" + carId +
                ", carName='" + carName + '\'' +
                ", carType=" + carType +
                ", staffList=" + staffList +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (carId == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(carId);
        }
        parcel.writeString(carName);
        parcel.writeTypedList(staffList);
    }
}
