package com.xy.fy.bmobpay.api;

import android.app.Activity;

/**
 * Description:
 * Data：18/01/2017-1:46 PM
 * Author: Mark
 */

public interface PayService {
    void pay(Activity activity, String title, String descript, double money, boolean aliOrWeChat, PayListener payListener);
}
