package com.hzj.test;

public class Outer {
    private  int a =123;

    public class  Inner{
        private  int a =321;
        public void show(){
            //  Outer o = new Outer();
            //   System.out.println(o.a);
            int a = 100;
            System.out.println(Outer.this.a);
            System.out.println(this.a);
            System.out.println(a);
        }
    }
}

