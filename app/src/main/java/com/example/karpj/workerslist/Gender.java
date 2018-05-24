package com.example.karpj.workerslist;

public class Gender {
    private  String gender;
    private int src;

    public Gender(String gender, int src) {
        this.gender = gender;
        this.src = src;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getSrc() {
        return src;
    }

    public void setSrc(int src) {
        this.src = src;
    }

    @Override
    public String toString() {
        return  gender;
    }
}
