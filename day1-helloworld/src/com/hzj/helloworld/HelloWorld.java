package com.hzj.helloworld;

import java.util.ArrayList;
import java.util.Scanner;

public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Hello World");
//        System.out.println(getVerifyCode());
//        sc();
//        System.out.println(getsushu());
//        System.out.println(testarr());
        HelloWorld hw  = new HelloWorld();
        hw.student();
    }

    //    生成一个验证码返回
    /* public static String getVerifyCode() {
        String verifyCode = "";
        for (int i = 0; i < 6; i++) {
            verifyCode += (int) (Math.random() * 10);
        }
        return verifyCode;
    }*/

//    写一个程序，用户输入年龄然后输出
    public static void  sc() {
        System.out.println("请输入年龄：");
        Scanner scanner = new Scanner(System.in);
        int age = scanner.nextInt();
        System.out.println("年龄是：" + age);
    }

    public static ArrayList<Integer> getsushu() {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 101; i <=200 ; i++) {
            boolean flag = true;
            for (int j = 2; j <= i / 2; j++) {
                if (i % j == 0) {
                    flag = false;
                }
            }
            if (flag) {
                list.add(i);
            }
        }
        return list;
    }

    public static String testarr() {
        String[] arr = {"name","1"};
        for(String s:arr){
            System.out.println(s);
        }
        ArrayList<String> arraylist = new ArrayList<>();
        arraylist.add("123");
        arraylist.add("456");
        arraylist.add("789");
        arraylist.add("10");

        arraylist.forEach(n -> {
        String sum = "";
                   if ("123".equals( n)) {
                       sum = n;
                   }
        System.out.println(sum);
                   });
        return  arr[0];

    }

    public void student() {
        Student stu = new Student();
        stu.setName("hzj");
        stu.setChinese(80);
        stu.setMath(90);
//        System.out.println(stu.name+"的语文成绩："+ stu.chinese+"，数学成绩："+ stu.math+ ",总成绩"+(stu.chinese+ stu.math)+",平均分："+ (stu.chinese+ stu.math)/2);
//        Student.printStudent(stu);
        stu.printScore(stu);
    }


}
