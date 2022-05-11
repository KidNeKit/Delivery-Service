package bsuir.diplom.mercury.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

public class Item implements Parcelable {
    private String name;
    private Double length;
    private Double width;
    private Double height;
    private Double weight;

    public Item() {
    }

    public Item(String name, Double length, Double width, Double height, Double weight) {
        this.name = name;
        this.length = length;
        this.width = width;
        this.height = height;
        this.weight = weight;
    }

    protected Item(Parcel in) {
        name = in.readString();
        if (in.readByte() == 0) {
            length = null;
        } else {
            length = in.readDouble();
        }
        if (in.readByte() == 0) {
            width = null;
        } else {
            width = in.readDouble();
        }
        if (in.readByte() == 0) {
            height = null;
        } else {
            height = in.readDouble();
        }
        if (in.readByte() == 0) {
            weight = null;
        } else {
            weight = in.readDouble();
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        if (length == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(length);
        }
        if (width == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(width);
        }
        if (height == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(height);
        }
        if (weight == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(weight);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return name.equals(item.name) &&
                length.equals(item.length) &&
                width.equals(item.width) &&
                height.equals(item.height) &&
                weight.equals(item.weight);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, length, width, height, weight);
    }

    @Override
    public String toString() {
        return "Item{" +
                "name='" + name + '\'' +
                ", length=" + length +
                ", width=" + width +
                ", height=" + height +
                ", weight=" + weight +
                '}';
    }
}
