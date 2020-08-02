package com.zwk.test;

import java.util.HashMap;

/**
 * @author mr.z
 * @date 2020/7/30 - 17:14
 */
public class MyThread implements Runnable {

    @Override
    public void run() {
        split("l");
    }

    public  static void split (String m){
        String str="laslkav;lkjsn;skldnf;";
        String[] r=str.split(m);
        System.out.println(r.length-1);
    }
}
