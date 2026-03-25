package com.hzj;

public class Testdemo2 {
    public static void main(String[] args) {

        new Thread(new MyRunnable()).start();
        for (int i = 0; i < 5; i++) {
            System.out.println("主线程："+i);
        }
    }
}

class MyRunnable implements Runnable{
    public void run(){
     for (int i = 0; i < 5; i++){
         System.out.println("子线程："+i);
     }
    }
}