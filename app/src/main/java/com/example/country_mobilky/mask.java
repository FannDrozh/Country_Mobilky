package com.example.country_mobilky;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

public class mask implements Parcelable {

    private int ID;
    private String Country;
    private String Population;
    private String Image;

    protected mask(Parcel in) {
        ID = in.readInt();
        Country = in.readString();
        Population = in.readString();
    }

    public static final Creator<mask> CREATOR = new Creator<mask>() {
        @Override
        public mask createFromParcel(Parcel in) {
            return new mask(in);
        }

        @Override
        public mask[] newArray(int size) {
            return new mask[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(ID);
        dest.writeString(Country);
        dest.writeString(Population);
        dest.writeString(Image);
    }



    public mask(int ID, String country, String population, String image)
    {
        this.ID = ID;
        Country = country;
        Population = population;
        Image = image;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getPopulation() {
        return Population;
    }

    public void setPopulation(String population) {
        Population = population;
    }
}