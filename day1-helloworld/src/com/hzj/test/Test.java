package com.hzj.test;

public class Test {
    public static void main(String[] args) {
        Outer.Inner oi = new Outer().new Inner();

        oi.show();
    }
}
