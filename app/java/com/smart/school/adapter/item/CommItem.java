package com.smart.school.adapter.item;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;


/**
 * Created by user01 on 2015-07-16.
 */

public class CommItem implements Parcelable{

    private String no;
    private String code;
    private String name;
    private String count;
    private String list;
    private String kind_code;
    private String time;
    private String floor_name;
    private String title;
    private int icon;
    private int isClick;
    private int nCode;
    private boolean isSelect;
    private ArrayList<CommItem> mList;
    private ArrayList<CommItem> tel;

    public CommItem(){

    }

    public CommItem(String name){
        this.name = name;
    }

    public CommItem(String no, String name){
        this.no = no;
        this.name = name;
    }

    public CommItem(String no, String code, String name){
        this.no = no;
        this.code = code;
        this.name = name;
    }

    public CommItem(String no, int nCode, String name){
        this.no = no;
        this.nCode = nCode;
        this.name = name;
    }

    /*public CommItem(String no, String name, String icon){
        this.no = no;
        this.name = name;
        this.icon = icon;
    }*/

    public CommItem(String no, String name, boolean isSelect) {
        this.no = no;
        this.name = name;
        this.isSelect = isSelect;
    }

    public CommItem(String no, String name, int icon ,boolean isSelect) {
        this.no = no;
        this.name = name;
        this.icon = icon;
        this.isSelect = isSelect;
    }

    public CommItem(String no, String name, int isClick) {
        super();
        this.no = no;
        this.name = name;
        this.isClick = isClick;
    }

    public CommItem(String no, String name, ArrayList<CommItem> arr) {
        super();
        this.no = no;
        this.name = name;
        this.mList = arr;
    }


    protected CommItem(Parcel in) {
        no = in.readString();
        code = in.readString();
        name = in.readString();
        count = in.readString();
        list = in.readString();
        kind_code = in.readString();
        time = in.readString();
        floor_name = in.readString();
        title = in.readString();
        icon = in.readInt();
        isClick = in.readInt();
        nCode = in.readInt();
        isSelect = in.readByte() != 0;
        mList = in.createTypedArrayList(CommItem.CREATOR);
        tel = in.createTypedArrayList(CommItem.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(no);
        dest.writeString(code);
        dest.writeString(name);
        dest.writeString(count);
        dest.writeString(list);
        dest.writeString(kind_code);
        dest.writeString(time);
        dest.writeString(floor_name);
        dest.writeString(title);
        dest.writeInt(icon);
        dest.writeInt(isClick);
        dest.writeInt(nCode);
        dest.writeByte((byte) (isSelect ? 1 : 0));
        dest.writeTypedList(mList);
        dest.writeTypedList(tel);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CommItem> CREATOR = new Creator<CommItem>() {
        @Override
        public CommItem createFromParcel(Parcel in) {
            return new CommItem(in);
        }

        @Override
        public CommItem[] newArray(int size) {
            return new CommItem[size];
        }
    };

    public String getNo() {
        return no;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getCount() {
        return count;
    }

    public String getKind_code() {
        return kind_code;
    }

    public String getFloor_name() {
        return floor_name;
    }

    public String getTitle() {
        return title;
    }

    public int getIcon() {
        return icon;
    }

    public int getIsClick() {
        return isClick;
    }

    public int getnCode() {
        return nCode;
    }


    public String getList() {
        return list;
    }

    public String getTime() {
        return time;
    }

    public ArrayList<CommItem> getTel() {
        return tel;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public ArrayList<CommItem> getmList() {
        return mList;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public void setList(String list) {
        this.list = list;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public void setIsClick(int isClick) {
        this.isClick = isClick;
    }

    public void setnCode(int nCode) {
        this.nCode = nCode;
    }

    public void setKind_code(String kind_code) {
        this.kind_code = kind_code;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public void setmList(ArrayList<CommItem> mList) {
        this.mList = mList;
    }

    public void setFloor_name(String floor_name) {
        this.floor_name = floor_name;
    }
}
