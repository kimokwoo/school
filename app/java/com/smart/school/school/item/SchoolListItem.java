package com.smart.school.school.item;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

public class SchoolListItem implements Parcelable {
    public String no;
    public String name;
    public String bsno; //버스 경로 번호
    public String seq; //순서
    public String busstop; //버스정류소 이름
    public String parr; //예상도착시간
    public String earr; //실제도착시간
    public String remaintime;
    public String ison; //도착여부
    public ArrayList<SchoolListItem> route_info;
    public ArrayList<SchoolListItem> bus_schedule;

    public SchoolListItem(String no, String name) {
        this.no = no;
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(no);
        parcel.writeString(name);
        parcel.writeString(bsno);
        parcel.writeString(seq);
        parcel.writeString(busstop);
        parcel.writeString(parr);
        parcel.writeString(earr);
        parcel.writeString(remaintime);
        parcel.writeString(ison);
        parcel.writeTypedList(route_info);
        parcel.writeTypedList(bus_schedule);

    }

    public SchoolListItem() {

    }

    protected SchoolListItem(Parcel in) {
        no = in.readString();
        name = in.readString();
        bsno = in.readString();
        seq = in.readString();
        busstop = in.readString();
        parr = in.readString();
        earr = in.readString();
        remaintime = in.readString();
        ison = in.readString();
        route_info = in.createTypedArrayList(SchoolListItem.CREATOR);
        bus_schedule = in.createTypedArrayList(SchoolListItem.CREATOR);
    }


    public static final Creator<SchoolListItem> CREATOR = new Creator<SchoolListItem>() {
        @Override
        public SchoolListItem createFromParcel(Parcel source) {
            return new SchoolListItem(source);
        }
        @Override
        public SchoolListItem[] newArray(int size) {
            return new SchoolListItem[size];
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

    public String getBsno() {
        return bsno;
    }

    public void setBsno(String bsno) {
        this.bsno = bsno;
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    public String getBusstop() {
        return busstop;
    }

    public void setBusstop(String busstop) {
        this.busstop = busstop;
    }

    public String getParr() {
        return parr;
    }

    public void setParr(String parr) {
        this.parr = parr;
    }

    public String getEarr() {
        return earr;
    }

    public void setEarr(String earr) {
        this.earr = earr;
    }

    public String getIson() {
        return ison;
    }

    public void setIson(String ison) {
        this.ison = ison;
    }

    public String getRemaintime() {
        return remaintime;
    }

    public void setRemaintime(String remaintime) {
        this.remaintime = remaintime;
    }

    public ArrayList<SchoolListItem> getRoute_info() {
        return route_info;
    }

    public void setRoute_info(ArrayList<SchoolListItem> route_info) {
        this.route_info = route_info;
    }

    public ArrayList<SchoolListItem> getBus_schedule() {
        return bus_schedule;
    }

    public void setBus_schedule(ArrayList<SchoolListItem> bus_schedule) {
        this.bus_schedule = bus_schedule;
    }
}
