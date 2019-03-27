package com.example.pstubloodbank;

/**
 * Created by JOY KARMAKER on 25-Dec-17.
 */
public class BloodAmounts {
    private String apos;
    private String abpos;
    private String opos;
    private String bpos;
    private String aneg;
    private String abneg;
    private String oneg;
    private String bneg;
    public static String user;

    public String getApos() {
        return apos;
    }

    public void setApos(String apos) {
        this.apos = apos;
    }

    public String getAbpos() {
        return abpos;
    }

    public void setAbpos(String abpos) {
        this.abpos = abpos;
    }

    public String getOpos() {
        return opos;
    }

    public void setOpos(String opos) {
        this.opos = opos;
    }

    public String getBpos() {
        return bpos;
    }

    public void setBpos(String bpos) {
        this.bpos = bpos;
    }

    public String getAneg() {
        return aneg;
    }

    public void setAneg(String aneg) {
        this.aneg = aneg;
    }

    public String getAbneg() {
        return abneg;
    }

    public void setAbneg(String abneg) {
        this.abneg = abneg;
    }

    public String getOneg() {
        return oneg;
    }

    public void setOneg(String oneg) {
        this.oneg = oneg;
    }

    public String getBneg() {
        return bneg;
    }

    public void setBneg(String bneg) {
        this.bneg = bneg;
    }

    public static void setUser(String s){
        user=s;
    }
     public static String getUser(){
         return user;
     }





}
