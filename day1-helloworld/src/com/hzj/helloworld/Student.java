package com.hzj.helloworld;

import java.util.Objects;

public class Student {
    private  String name;
    private double math;
    private double chinese;

    public Student() {

    }
    public double getAvg() {
        return (math + chinese) / 2;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public double getMath() {
        return math;
    }
    public void setMath(double math) {
        this.math = math;
    }

    public double getChinese() {
        return chinese;
    }
    public void setChinese(double chinese) {
        this.chinese = chinese;
    }

    public static void main(String[] args) {
    }

    public void printStudent(Student student) {
            System.out.println("姓名：" + student.getName() + "，平均分：" + student.getAvg());
    }

    public void printScore(Student stu){
        System.out.println("姓名：" + stu.getName() + "，语文成绩：" + stu.getChinese() + "，数学成绩：" + stu.getMath() + "，总成绩：" + (stu.getChinese() + stu.getMath()) + "，平均分：" + stu.getAvg());
    }
}
