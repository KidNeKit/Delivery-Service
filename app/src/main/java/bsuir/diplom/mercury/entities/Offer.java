package bsuir.diplom.mercury.entities;

import java.util.List;

import bsuir.diplom.mercury.entities.enums.CarType;
import bsuir.diplom.mercury.entities.enums.OfferStatus;

public class Offer {
    private String offerId;
    private OfferStatus offerStatus;
    private CarType chosenCarType;
    private Car chosenCar;
    private String addressFrom;
    private String addressTo;
    private List<Item> itemList;
    //private User user;
    //private Long date;

    public Offer(String offerId, OfferStatus offerStatus, CarType chosenCarType, Car chosenCar, String addressFrom, String addressTo, List<Item> itemList) {
        this.offerId = offerId;
        this.offerStatus = offerStatus;
        this.chosenCarType = chosenCarType;
        this.chosenCar = chosenCar;
        this.addressFrom = addressFrom;
        this.addressTo = addressTo;
        this.itemList = itemList;
    }

    public Offer() {
    }

    public String getOfferId() {
        return offerId;
    }

    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }

    public OfferStatus getOfferStatus() {
        return offerStatus;
    }

    public void setOfferStatus(OfferStatus offerStatus) {
        this.offerStatus = offerStatus;
    }

    public String getAddressFrom() {
        return addressFrom;
    }

    public void setAddressFrom(String addressFrom) {
        this.addressFrom = addressFrom;
    }

    public String getAddressTo() {
        return addressTo;
    }

    public void setAddressTo(String addressTo) {
        this.addressTo = addressTo;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }

    public CarType getChosenCarType() {
        return chosenCarType;
    }

    public void setChosenCarType(CarType chosenCarType) {
        this.chosenCarType = chosenCarType;
    }

    public Car getChosenCar() {
        return chosenCar;
    }

    public void setChosenCar(Car chosenCar) {
        this.chosenCar = chosenCar;
    }

    @Override
    public String toString() {
        return "Offer{" +
                "offerId='" + offerId + '\'' +
                ", offerStatus=" + offerStatus +
                ", chosenCarType=" + chosenCarType +
                ", chosenCar=" + chosenCar +
                ", addressFrom='" + addressFrom + '\'' +
                ", addressTo='" + addressTo + '\'' +
                ", itemList=" + itemList +
                '}';
    }
}