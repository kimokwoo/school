package com.smart.school.school.item;

import android.os.Parcel;
import android.os.Parcelable;


public class FoodMenuItem implements Parcelable {
    private String no;
    private String food_no;
    private String food_name;
    private String good_no;
    private String bad_no;
    private String cal;
    private String allergy;
    private String review_status;

    public FoodMenuItem(String no, String food_no,
                        String food_name, String good_no, String bad_no, String cal, String allergy, String review_status) {
        this.no = no;
        this.food_no = food_no;
        this.food_name = food_name;
        this.good_no = good_no;
        this.bad_no = bad_no;
        this.cal = cal;
        this.allergy = allergy;
        this.review_status = review_status;
    }

    protected FoodMenuItem(Parcel in){

        no = in.readString();
        food_no = in.readString();
        food_name = in.readString();
        good_no = in.readString();
        bad_no = in.readString();
        cal = in.readString();
        allergy = in.readString();
        review_status = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeString(no);
        parcel.writeString(food_no);
        parcel.writeString(food_name);
        parcel.writeString(good_no);
        parcel.writeString(bad_no);
        parcel.writeString(cal);
        parcel.writeString(allergy);
        parcel.writeString(review_status);
    }

    public static final Creator<FoodMenuItem> CREATOR = new Creator<FoodMenuItem>() {
        @Override
        public FoodMenuItem createFromParcel(Parcel in) {
            return new FoodMenuItem(in);
        }

        @Override
        public FoodMenuItem[] newArray(int size) {
            return new FoodMenuItem[size];
        }
    };

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getFood_no() {
        return food_no;
    }

    public void setFood_no(String food_no) {
        this.food_no = food_no;
    }

    public String getFood_name() {
        return food_name;
    }

    public void setFood_name(String food_name) {
        this.food_name = food_name;
    }

    public String getGood_no() {
        return good_no;
    }

    public void setGood_no(String good_no) {
        this.good_no = good_no;
    }

    public String getBad_no() {
        return bad_no;
    }

    public void setBad_no(String bad_no) {
        this.bad_no = bad_no;
    }

    public String getCal() {
        return cal;
    }

    public void setCal(String cal) {
        this.cal = cal;
    }

    public String getAllergy() {
        return allergy;
    }

    public void setAllergy(String allergy) {
        this.allergy = allergy;
    }

    public String getReview_status() {
        return review_status;
    }

    public void setReview_status(String review_status) {
        this.review_status = review_status;
    }

    public static Creator<FoodMenuItem> getCREATOR() {
        return CREATOR;
    }
}





