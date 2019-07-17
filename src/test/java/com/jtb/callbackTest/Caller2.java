package com.jtb.callbackTest;

import org.junit.Test;

/**
 * @auther: jtb
 * @date: 2019/7/8 01:00
 * @description: 将Caller1改造成匿名内部类
 */
public class Caller2 {
    @Test
    public void CallbackFunc() {
        // 执行类
        Execute execute = new Execute();
        execute.execute(1, result -> System.out.println("执行回调完成，结果：" + result));
    }
}
