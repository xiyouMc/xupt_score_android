package com.xy.fy.pay.api;

/**
 * Description:
 * Data：18/01/2017-1:46 PM
 * Author: Mark
 */

public interface PayService {
    void pay(String title,String descript,double money,boolean aliOrWeChat,PayListener payListener);
}
