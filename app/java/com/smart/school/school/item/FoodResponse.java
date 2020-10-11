package com.smart.school.school.item;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class FoodResponse implements Parcelable {
    public String remark;
    public ArrayList<FoodMenuItem> menu_info;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(remark);
        parcel.writeTypedList(menu_info);

    }

    protected FoodResponse(Parcel in) {
        remark = in.readString();
        menu_info = in.createTypedArrayList(FoodMenuItem.CREATOR);
    }


    public static final Creator<FoodResponse> CREATOR = new Creator<FoodResponse>() {
        @Override
        public FoodResponse createFromParcel(Parcel source) {
            return new FoodResponse(source);
        }
        @Override
        public FoodResponse[] newArray(int size) {
            return new FoodResponse[size];
        }
    };

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public ArrayList<FoodMenuItem> getMenu_info() {
        return menu_info;
    }

    public void setMenu_info(ArrayList<FoodMenuItem> menu_info) {
        this.menu_info = menu_info;
    }
}
