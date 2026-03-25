package com.h.aaa;

import java.util.ArrayList;

public class generics {

    public static void main(String[] args) {

        ArrayList<XM> xm = new ArrayList<>();
        xm.add(new XM());
        xm.add(new XM());
        go(xm);

        ArrayList<BYD> byd = new ArrayList<>();
        byd.add(new BYD());
        byd.add(new BYD());
        go(byd);

        go2(xm);
        go2(byd);

        ArrayList<String> list = new ArrayList<>();
        list.add("hello");
        list.add("123");
        go(list);

        ArrayList<Boolean> list2 = new ArrayList<>();


        String s = "12";
        System.out.println(Integer.parseInt(s));

    }

    public static  <T> void go (ArrayList<T> t) {
        System.out.println(t);
    }

    public static void go2 (ArrayList<? extends Car> t) {
        System.out.println(t);
    }


}
