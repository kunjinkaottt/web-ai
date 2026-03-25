package com.h.map;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class mapdemo {
    public static void main(String[] args) {

        Map<String, String> map = new HashMap<>();
        map.put("1", "1");
        map.put("2", "2");
        map.put("3", "3");
//        用lambda遍历map
        /*map.forEach((k,v)->{
            System.out.println(k+"--"+v);
        });*/
        Set< Map.Entry<String,String> > entries = map.entrySet();
//        然后遍历这个entries
        for(Map.Entry<String,String> entry : entries ){
            String key = entry.getKey();
            String value = entry.getValue();
            System.out.println(key+"--"+value);
        }
    }
}
