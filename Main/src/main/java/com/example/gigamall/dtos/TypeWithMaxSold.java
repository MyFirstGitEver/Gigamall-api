package com.example.gigamall.dtos;

public class TypeWithMaxSold {
    private String type;
    private int soldNum;

    public TypeWithMaxSold(String type, int soldNum) {
        this.type = type;
        this.soldNum = soldNum;
    }

    TypeWithMaxSold() {

    }

    public String getType() {
        return type;
    }

    public int getSoldNum() {
        return soldNum;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setSoldNum(int soldNum) {
        this.soldNum = soldNum;
    }
}
