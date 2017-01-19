package com.xy.fy.bmobpay;

import com.dynamicload.framework.util.FrameworkUtil;
import com.vivavideo.mobile.commonui.XYActionSheetDialog;
import com.xy.fy.bmobpay.api.PayListener;
import com.xy.fy.bmobpay.api.PayService;

import android.app.Activity;
import android.app.ProgressDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import c.b.BP;
import c.b.PListener;
import top.codemc.common.util.ViewUtil;

/**
 * Description:
 * Data：18/01/2017-1:45 PM
 * Author: Mark
 */

public class PayServiceImpl implements PayService {
     ProgressDialog mProgressDialog;
    @Override
    public void pay(Activity activity, final String title, final String descript, final  double money, boolean aliOrWeChat, final PayListener payListener) {
        final View view = LayoutInflater.from(activity).inflate(R.layout.charge_channels, null);
        final XYActionSheetDialog dialog = new XYActionSheetDialog(activity).builder();
        dialog.showCancel(false);
        dialog.addViewContent(view);
        dialog.setTitle("购买查看平时和卷面权限，选择支付方式（单次购买，永久免费）").show();
        mProgressDialog = ViewUtil.getProgressDialog(activity,"");
        RelativeLayout alipayCharge = (RelativeLayout) view.findViewById(R.id.alipay_charge);
        alipayCharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgressDialog.show();
                BP.pay(title, descript, money, true, new PListener() {
                    @Override
                    public void orderId(String s) {
                        payListener.orderId(s);
                        mProgressDialog.dismiss();
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
                mProgressDialog.show();
                BP.pay(title, descript, money, false, new PListener() {
                    @Override
                    public void orderId(String s) {
                        payListener.orderId(s);
                        mProgressDialog.dismiss();
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
