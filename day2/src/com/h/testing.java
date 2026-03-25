package com.h;

import lombok.val;

import java.util.*;

public class testing {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        Collections.addAll(list,8,34,6,1,9,4,3,0);
        Collections.sort(list,Integer::compare);
        System.out.println(list);
    }

    public static void sum(int...nums){

    }
}
