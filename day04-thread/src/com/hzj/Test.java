package com.hzj;

public class Test {
    public static void main(String[] args) {
        Thread t1 = new MyThread();
        t1.start();//不能直接调用run ，调用run的话还是单线程，先执行run再执行主线程，只有调用start才是开辟一个新的线程去执行run
        for (int i = 0; i < 6; i++){ // 如果这部分放在子线程start之前，则会先执行完这部分再去执行子线程start
            System.out.println("主线程："+i);
        }
    }
}

class MyThread extends  Thread{
    public void run(){
        for (int i = 0; i < 6; i++){
            System.out.println("子线程："+i);
        }
    }
}