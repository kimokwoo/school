package com.smart.school.school.item;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class MyInfoResponse implements Parcelable {
    public String val_bus;
    public String val_busstop;
    public String val_allergy;
    public ArrayList<SchoolListItem> bus_info;
    public ArrayList<SchoolListItem> busstop_info;
    public ArrayList<SchoolListItem> allergy_info;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(val_bus);
        parcel.writeString(val_busstop);
        parcel.writeString(val_allergy);
        parcel.writeTypedList(bus_info);
        parcel.writeTypedList(busstop_info);
        parcel.writeTypedList(allergy_info);
    }

    protected MyInfoResponse(Parcel in) {
        val_bus = in.readString();
        val_busstop = in.readString();
        val_allergy = in.readString();
        bus_info = in.createTypedArrayList(SchoolListItem.CREATOR);
        busstop_info = in.createTypedArrayList(SchoolListItem.CREATOR);
        allergy_info = in.createTypedArrayList(SchoolListItem.CREATOR);
    }


    public static final Creator<MyInfoResponse> CREATOR = new Creator<MyInfoResponse>() {
        @Override
        public MyInfoResponse createFromParcel(Parcel source) {
            return new MyInfoResponse(source);
        }
        @Override
        public MyInfoResponse[] newArray(int size) {
            return new MyInfoResponse[size];
        }
    };

    public String getVal_bus() {
        return val_bus;
    }

    public void setVal_bus(String val_bus) {
        this.val_bus = val_bus;
    }

    public String getVal_busstop() {
        return val_busstop;
    }

    public void setVal_busstop(String val_busstop) {
        this.val_busstop = val_busstop;
    }

    public String getVal_allergy() {
        return val_allergy;
    }

    public void setVal_allergy(String val_allergy) {
        this.val_allergy = val_allergy;
    }

    public ArrayList<SchoolListItem> getBus_info() {
        return bus_info;
    }

    public void setBus_info(ArrayList<SchoolListItem> bus_info) {
        this.bus_info = bus_info;
    }

    public ArrayList<SchoolListItem> getBusstop_info() {
        return busstop_info;
    }

    public void setBusstop_info(ArrayList<SchoolListItem> busstop_info) {
        this.busstop_info = busstop_info;
    }

    public ArrayList<SchoolListItem> getAllergy_info() {
        return allergy_info;
    }

    public void setAllergy_info(ArrayList<SchoolListItem> allergy_info) {
        this.allergy_info = allergy_info;
    }
}
