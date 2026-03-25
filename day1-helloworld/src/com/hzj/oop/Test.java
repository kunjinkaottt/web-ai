package com.hzj.oop;

import java.util.ArrayList;
import java.util.List;

public class Test {

    //实例代码块
    {
    
    }

    //静态代码块
    static{

    }

    public static void main(String[] args) {

        /*Teacher t = new Teacher();
        t.setName("hzj");
        t.setSex('男');
        t.setSkill("java");
        System.out.println(t.getName() + "的技能是：" + t.getSkill());
        System.out.println(t.getName() + "的性别是：" + t.getSex());*/

        Student stu = new Student("hzj", '男', 18, "学校");
        System.out.println(stu.toString());

        People peo = new Student();
        Student stu2 = (Student) peo;



    }

}
