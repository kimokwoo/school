package com.smart.school.school.item;

import android.os.Parcel;
import android.os.Parcelable;


public class NoNameItem implements Parcelable {
    private String no;
    private String name;

    public NoNameItem(String no, String name) {
        this.no = no;
        this.name = name;
    }

    protected NoNameItem(Parcel in){

        no = in.readString();
        name = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeString(no);
        parcel.writeString(name);
    }

    public static final Creator<NoNameItem> CREATOR = new Creator<NoNameItem>() {
        @Override
        public NoNameItem createFromParcel(Parcel in) {
            return new NoNameItem(in);
        }

        @Override
        public NoNameItem[] newArray(int size) {
            return new NoNameItem[size];
        }
    };

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static Creator<NoNameItem> getCREATOR() {
        return CREATOR;
    }
}





