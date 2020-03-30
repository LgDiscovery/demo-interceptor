package com.logo.demo.inteceptor.component;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CacheSingleton {

    private static volatile CacheSingleton singleton;

    //private static Object lock = new Object();

    private static Lock lock = new ReentrantLock();

    private CacheSingleton(){}

    public static CacheSingleton getSingleton(){
        if(null!=singleton){
            return singleton;
        }else{
          try{
              if(lock.tryLock(3, TimeUnit.MINUTES)){
                  if(null == singleton){
                      singleton = new CacheSingleton();
                  }
              }
          } catch (InterruptedException e) {
              e.printStackTrace();
          } finally {
              lock.unlock();
          }
            return singleton;
        }
    }
}
