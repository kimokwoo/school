package com.smart.school.school.item;

import android.os.Parcel;
import android.os.Parcelable;


public class BulletinItem implements Parcelable {
    private String no;
    private String title;
    private String content;
    private String reg_date;

    public BulletinItem(String no, String title, String content, String reg_date) {
        this.no = no;
        this.title = title;
        this.content = content;
        this.reg_date = reg_date;
    }

    protected BulletinItem(Parcel in){

        no = in.readString();
        title = in.readString();
        content = in.readString();
        reg_date = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeString(no);
        parcel.writeString(title);
        parcel.writeString(content);
        parcel.writeString(reg_date);
    }

    public static final Creator<BulletinItem> CREATOR = new Creator<BulletinItem>() {
        @Override
        public BulletinItem createFromParcel(Parcel in) {
            return new BulletinItem(in);
        }

        @Override
        public BulletinItem[] newArray(int size) {
            return new BulletinItem[size];
        }
    };

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getReg_date() {
        return reg_date;
    }

    public void setReg_date(String reg_date) {
        this.reg_date = reg_date;
    }

    public static Creator<BulletinItem> getCREATOR() {
        return CREATOR;
    }
}





