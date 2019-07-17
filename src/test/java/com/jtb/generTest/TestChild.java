package com.jtb.generTest;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @auther: jtb
 * @date: 2019/5/28 14:41
 * @description:
 */
public class TestChild extends TestBase implements TestInterface {

    public TestChild() {
        System.out.println("This is  TestChild Constructor");
    }

    @Test
    @Override
    public void findStr() {
        String str = "我是字符";
        List list = new ArrayList();
        List list1 = super.testBase(str);
        List list2 = super.testBase(111);
        list.add(list1);
        list.add(list2);
        System.out.println("This is  TestChild findStr + " + list.get(0) + list.get(1));

    }
}
