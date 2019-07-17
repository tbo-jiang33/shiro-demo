package com.jtb.generTest;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @auther: jtb
 * @date: 2019/5/28 14:41
 * @description:
 */
public class TestBase {

    public int i = 0;
    public int b = 1;
    public int c = 2;

    public <T> List testBase(T t) {
        System.out.println("This is TestBase list" + t.getClass());
        List<T> list = new ArrayList<>();
        list.add(t);
        return list;
    }

    protected void findStr () {
        System.out.println("This is TestBase - findStr");
    }

}
