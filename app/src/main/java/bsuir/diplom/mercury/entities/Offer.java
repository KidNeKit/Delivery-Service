package bsuir.diplom.mercury.entities;

import java.util.List;

import bsuir.diplom.mercury.entities.dto.AddressDTO;
import bsuir.diplom.mercury.entities.enums.OfferStatus;

public class Offer {
    private String offerId;
    private OfferStatus offerStatus;
    private Car chosenCar;
    private AddressDTO addressFrom;
    private AddressDTO addressTo;
    private List<Staff> staffList;
    private List<Item> itemList;
    //private User user;
    //private Long date;

    public Offer(String offerId, OfferStatus offerStatus, Car chosenCar, AddressDTO addressFrom, AddressDTO addressTo, List<Staff> staffList, List<Item> itemList) {
        this.offerId = offerId;
        this.offerStatus = offerStatus;
        this.chosenCar = chosenCar;
        this.addressFrom = addressFrom;
        this.addressTo = addressTo;
        this.staffList = staffList;
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

    public AddressDTO getAddressFrom() {
        return addressFrom;
    }

    public void setAddressFrom(AddressDTO addressFrom) {
        this.addressFrom = addressFrom;
    }

    public AddressDTO getAddressTo() {
        return addressTo;
    }

    public void setAddressTo(AddressDTO addressTo) {
        this.addressTo = addressTo;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }

    public List<Staff> getStaffList() {
        return staffList;
    }

    public void setStaffList(List<Staff> staffList) {
        this.staffList = staffList;
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
                ", chosenCar=" + chosenCar +
                ", addressFrom='" + addressFrom + '\'' +
                ", addressTo='" + addressTo + '\'' +
                ", staffList='" + staffList + '\'' +
                ", itemList=" + itemList +
                '}';
    }
}