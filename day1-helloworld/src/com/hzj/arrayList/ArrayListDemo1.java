package com.hzj.arrayList;

import java.util.ArrayList;
import java.util.Arrays;

public class ArrayListDemo1 {
    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<>();

//        增
        list.add("hello");
        list.add("123");
        list.add("hello2");
        list.add("123");
//        删  // 删除第一个内容为“123”的，后面的都保留，参数也可以是索引值（下标）
//        remove参数是下标，则方法调用后会返回删除的那个元素的内容
        list.remove("123"); //
//        改
        list.set(0,"hello1");
//        查
        System.out.println(list.get(1));
        System.out.println(list);


        //将集合转为数组
        /*Object[] arr = list.toArray();
        System.out.println(Arrays.toString(arr));*/

    }

}
