package com.xy.fy.bmobpay.api;

/**
 * Description:
 * Data：18/01/2017-1:47 PM
 * Author: Mark
 */

public interface PayListener {
    void orderId(String orderId);

    void succeed();

    void fail(int code, String reason);

    void unKnow();
}
