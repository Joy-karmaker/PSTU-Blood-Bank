package com.example.pstubloodbank;

/**
 * Created by JOY KARMAKER on 14-Dec-17.
 */
public class CustomCardView {
    private String name;
    private String blood_group;
    private String unit;
    private String city;
    private String hospitalname;
    private String phone;
    private String date;


    public CustomCardView(String name, String blood_group, String unit, String city, String hospitalname, String phone, String date) {
        this.name = name;
        this.blood_group = blood_group;
        this.unit = unit;
        this.city = city;
        this.hospitalname = hospitalname;
        this.phone = phone;
        this.date = date;

    }

    public String getBlood_group() {
        return blood_group;
    }

    public String getName() {
        return name;
    }

    public String getUnit() {
        return unit;
    }

    public String getCity() {
        return city;
    }

    public String getHospitalname() {
        return hospitalname;
    }

    public String getPhone() {
        return phone;
    }

    public String getDate() {
        return date;
    }
}


