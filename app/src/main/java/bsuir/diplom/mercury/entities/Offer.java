package bsuir.diplom.mercury.entities;

import java.sql.Timestamp;
import java.util.Date;

import bsuir.diplom.mercury.entities.enums.OfferStatus;

public class Offer {
    private String id;
    private String name;
    private Double length;
    private Double width;
    private Double height;
    private Double weight;
    private OfferStatus offerStatus;
    private Long date;

    public Offer() {
    }

    public Offer(String id, String name, Double length, Double width, Double height, Double weight, OfferStatus offerStatus) {
        this.id = id;
        this.name = name;
        this.length = length;
        this.width = width;
        this.height = height;
        this.weight = weight;
        this.offerStatus = offerStatus;
        //this.date = date;
    }

    public Offer(String name, Double length, Double width, Double height, Double weight, OfferStatus offerStatus) {
        this.name = name;
        this.length = length;
        this.width = width;
        this.height = height;
        this.weight = weight;
        this.offerStatus = offerStatus;
        //this.date = date;
    }

    public Offer(Item item) {
        this.name = item.getName();
        this.length = item.getLength();
        this.width = item.getWidth();
        this.height = item.getHeight();
        this.weight = item.getWeight();
        this.offerStatus = OfferStatus.IN_PROCESSING;
        this.date = new Date().getTime();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getLength() {
        return length;
    }

    public Double getWidth() {
        return width;
    }

    public Double getHeight() {
        return height;
    }

    public Double getWeight() {
        return weight;
    }

    public OfferStatus getOfferStatus() {
        return offerStatus;
    }

    public Long getDate() {
        return date;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Offer{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", length=" + length +
                ", width=" + width +
                ", height=" + height +
                ", weight=" + weight +
                ", offerStatus=" + offerStatus +
                ", date=" + new Timestamp(date) +
                '}';
    }
}


