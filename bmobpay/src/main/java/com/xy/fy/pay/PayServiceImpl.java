package com.xy.fy.pay;

import com.dynamicload.framework.util.FrameworkUtil;
import com.vivavideo.mobile.commonui.XYActionSheetDialog;
import com.xy.fy.pay.api.PayListener;
import com.xy.fy.pay.api.PayService;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import c.b.BP;
import c.b.PListener;

/**
 * Description:
 * Data：18/01/2017-1:45 PM
 * Author: Mark
 */

public class PayServiceImpl implements PayService {
    @Override
    public void pay(final String title, final String descript,final  double money, boolean aliOrWeChat, final PayListener payListener) {
        final View view = LayoutInflater.from(FrameworkUtil.getContext()).inflate(R.layout.charge_channels, null);
        XYActionSheetDialog dialog = new XYActionSheetDialog(FrameworkUtil.getContext()).builder();
        dialog.showCancel(false);
        dialog.addViewContent(view);
        dialog.setTitle("选择支付方式").show();
        RelativeLayout alipayCharge = (RelativeLayout) view.findViewById(R.id.alipay_charge);
        alipayCharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BP.pay(title, descript, money, true, new PListener() {
                    @Override
                    public void orderId(String s) {
                        payListener.orderId(s);
                    }

                    @Override
                    public void succeed() {
                        payListener.succeed();
                    }

                    @Override
                    public void fail(int i, String s) {
                        payListener.fail(i, s);
                    }

                    @Override
                    public void unknow() {
                        payListener.unKnow();
                    }
                });
            }
        });
        RelativeLayout wxCharge = (RelativeLayout) view.findViewById(R.id.wx_charge);
        wxCharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BP.pay(title, descript, money, false, new PListener() {
                    @Override
                    public void orderId(String s) {
                        payListener.orderId(s);
                    }

                    @Override
                    public void succeed() {
                        payListener.succeed();
                    }

                    @Override
                    public void fail(int i, String s) {
                        payListener.fail(i, s);
                    }

                    @Override
                    public void unknow() {
                        payListener.unKnow();
                    }
                });
            }
        });

    }
}
