package bsuir.diplom.mercury.entities;

import bsuir.diplom.mercury.entities.enums.Role;

public class Staff {
    private Integer id;
    private String name;
    private String surname;
    private String phoneNumber;
    private Role profession;
    private Integer photoImageResource;
    private Integer offerCount;
    private Integer commentCount;
    private Double rating;

    public Staff(Integer id, String name, String surname, String phoneNumber, Role profession, Integer photoImageResource, Integer offerCount, Integer commentCount, Double rating) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.profession = profession;
        this.photoImageResource = photoImageResource;
        this.offerCount = offerCount;
        this.commentCount = commentCount;
        this.rating = rating;
    }

    public Staff() {
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Role getProfession() {
        return profession;
    }

    public void setProfession(Role profession) {
        this.profession = profession;
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
