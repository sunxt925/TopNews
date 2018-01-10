package com.didi.moon;

/**
 * Created by lyorall on 2018/1/9.
 */
public class Runner {
    static {
        String configJson = "";
    }
    public static void main(String[] args) {
        SecureDataEraser eraser = new SecureDataEraser();
        DBUtil.getConnection();
        eraser.hideInfo()


    }
}
