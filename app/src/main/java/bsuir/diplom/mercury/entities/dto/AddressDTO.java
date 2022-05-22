package bsuir.diplom.mercury.entities.dto;

import android.location.Address;

import com.mapbox.geojson.Point;

import bsuir.diplom.mercury.entities.enums.PointType;

public class AddressDTO {
    private double latitude;
    private double longitude;
    private String street;
    private String house;
    private String city;
    private String country;
    private PointType pointType;

    public AddressDTO() {
    }

    public AddressDTO(double latitude, double longitude, String street, String house, String city, String country) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.street = street;
        this.house = house;
        this.city = city;
        this.country = country;
    }

    public AddressDTO(Address address, PointType pointType) {
        this.latitude = address.getLatitude();
        this.longitude = address.getLongitude();
        this.street = address.getThoroughfare();
        this.house = address.getFeatureName();
        this.city = address.getLocality();
        this.country = address.getCountryName();
        this.pointType = pointType;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public PointType getPointType() {
        return pointType;
    }

    public void setPointType(PointType pointType) {
        this.pointType = pointType;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "AddressDTO{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                ", pointType='" + pointType + '\'' +
                ", street='" + street + '\'' +
                ", house='" + house + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}
