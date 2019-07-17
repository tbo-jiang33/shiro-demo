package com.jtb.callbackTest;

import org.junit.Test;

/**
 * @auther: jtb
 * @date: 2019/7/8 01:00
 * @description: 回调函数的实现类，作为执行函数的参数，最后执行函数会调用此类的方法。
 *   使用回调函数最重要的是实现回调接口，实现此接口的方法，
 *   当调用执行函数时，执行函数完成后会调用此接口的方法，最终会回到这里来
 */
public class Caller1 implements ICallback {

    @Override
    public void run(String result) {
        System.out.println("执行回调完成，结果：" + result);
    }

    @Test
    public void CallbackFunc() {
        // 执行类
        Execute execute = new Execute();
        /* 调用执行方法，此方法预留了一个接口作为参数，实际传此接口的实现类(向上转型)
         * 并且最后会调用此接口(实现类)的方法，
         * 因为实际传的是它的实现类
         * 因为 execute(int tag, ICallback callback); 所以参数是此接口的子类，即本类
         */
        execute.execute(1, new Caller1());
    }
}
