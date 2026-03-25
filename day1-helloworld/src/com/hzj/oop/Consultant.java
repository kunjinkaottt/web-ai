package com.hzj.oop;

public class Consultant extends  People{
    private int number;

    public Consultant() {
    }

    public Consultant(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "Consultant{" +
                "number=" + number +
                '}';
    }

}
