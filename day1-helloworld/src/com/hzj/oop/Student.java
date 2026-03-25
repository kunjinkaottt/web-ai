package com.hzj.oop;

import lombok.Data;

@Data
public class Student extends People{
    private String name;
    private char sex;
    private int age;
    private String schoolname;

    public Student() {
    }

    public Student(String name, char sex, int age) {
        super();
        this.name = name;
        this.sex = sex;
        this.age = age;
    }

    public Student(String name, char sex, int age, String schoolname) {
        this(name,sex,age);
        /*this.name = name;
        this.sex = sex;
        this.age = age;*/
        this.schoolname = schoolname;
    }

    public void onlystu(){
        System.out.println("onlystu");
    }


    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", sex=" + sex +
                ", age=" + age +
                ", schoolname='" + schoolname + '\'' +
                '}';
    }
}

