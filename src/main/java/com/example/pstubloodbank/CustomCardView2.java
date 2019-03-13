package com.example.pstubloodbank;

/**
 * Created by JOY KARMAKER on 14-Dec-17.
 */
public class CustomCardView2 {
    private String name;
    private String city;
    private String distance;
    private String email;

    public CustomCardView2(String name,String city,String e, String distance) {
        this.name = name;
        this.city = city;
        this.distance = distance;
        email=e;
    }


    public String getName() {
        return name;
    }
    public String getEmail(){
        return email;
    }

    public String getCity() {
        return city;
    }

    public String getDistance() {
        return distance;
    }


}

