package com.jtb.callbackTest;

/**
 * @auther: jtb
 * @date: 2019/7/8 00:56
 * @description:
 */
/**
 * 执行类
 * @author jtb
 * @date 2019/7/8 0:56 
 */
public class Execute {
    public void execute(int tag, ICallback callback) {
        callback.run("执行函数通过回调返回，并且标记加一，原：" + tag + "加一：" + ++tag);
    }
}
