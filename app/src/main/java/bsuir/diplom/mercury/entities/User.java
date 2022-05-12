package bsuir.diplom.mercury.entities;

public class User {
    private String phoneNumber;
    private String surname;
    private String name;

    public User(String phoneNumber, String surname, String name) {
        this.phoneNumber = phoneNumber;
        this.surname = surname;
        this.name = name;
    }

    public User() {
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
}
