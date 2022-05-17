package bsuir.diplom.mercury.entities;

import android.os.Parcel;
import android.os.Parcelable;

import bsuir.diplom.mercury.entities.enums.Role;

public class Staff implements Parcelable {
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

    protected Staff(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        name = in.readString();
        surname = in.readString();
        phoneNumber = in.readString();
        if (in.readByte() == 0) {
            photoImageResource = null;
        } else {
            photoImageResource = in.readInt();
        }
        if (in.readByte() == 0) {
            offerCount = null;
        } else {
            offerCount = in.readInt();
        }
        if (in.readByte() == 0) {
            commentCount = null;
        } else {
            commentCount = in.readInt();
        }
        if (in.readByte() == 0) {
            rating = null;
        } else {
            rating = in.readDouble();
        }
    }

    public static final Creator<Staff> CREATOR = new Creator<Staff>() {
        @Override
        public Staff createFromParcel(Parcel in) {
            return new Staff(in);
        }

        @Override
        public Staff[] newArray(int size) {
            return new Staff[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (id == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(id);
        }
        parcel.writeString(name);
        parcel.writeString(surname);
        parcel.writeString(phoneNumber);
        if (photoImageResource == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(photoImageResource);
        }
        if (offerCount == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(offerCount);
        }
        if (commentCount == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(commentCount);
        }
        if (rating == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(rating);
        }
    }

    @Override
    public String toString() {
        return "Staff{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", profession=" + profession +
                ", photoImageResource=" + photoImageResource +
                ", offerCount=" + offerCount +
                ", commentCount=" + commentCount +
                ", rating=" + rating +
                '}';
    }
}
