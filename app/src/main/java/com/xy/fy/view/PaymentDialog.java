package com.xy.fy.view;

import com.xy.fy.main.R;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

/**
 * Created by alison on 17/1/19.
 */

public class PaymentDialog extends Dialog {

    private Context mContext;
    private LayoutInflater inflater;
    private OnPayClickListener payClickListener;
    private String mTitle;
    private String mContent;
    private String mPriceTag;
    private String mPayPrice;
    private String mOriginalPrice;

    private View rootView;
    private TextView mTitleTextView;
    private TextView mContentTextView;
    private TextView mPriceTagTextView;
    private TextView mPayPriceTextView;
    private TextView mOriginalTextView;
    private TextView mPayTextView;

    public interface OnPayClickListener {
        void onClick();
    }

    public PaymentDialog(Context context, String title, String content, String priceTag, String payPrice, String originalPrice, OnPayClickListener listener) {
        super(context, R.style.noTitleTransBgDialogStyle);
        mContext = context;
        inflater = LayoutInflater.from(context);
        payClickListener = listener;
        mTitle = title;
        mContent = content;
        mPriceTag = priceTag;
        mPayPrice = payPrice;
        mOriginalPrice = originalPrice;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rootView = inflater.inflate(R.layout.layout_payment_dialog, null);
        mTitleTextView = (TextView) rootView.findViewById(R.id.title);
        mContentTextView = (TextView) rootView.findViewById(R.id.content);
        mPriceTagTextView = (TextView) rootView.findViewById(R.id.price_tag);
        mPayPriceTextView = (TextView) rootView.findViewById(R.id.pay_price);
        mOriginalTextView = (TextView) rootView.findViewById(R.id.original_price);
        mOriginalTextView.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        mPayTextView = (TextView) rootView.findViewById(R.id.pay);
        mPayTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (payClickListener != null) {
                    payClickListener.onClick();
                }
            }
        });
        setTextView(mTitleTextView, mTitle);
        setTextView(mContentTextView, mContent);
        setTextView(mPriceTagTextView, mPriceTag);
        setTextView(mPayPriceTextView, mPayPrice);
        setTextView(mOriginalTextView, mOriginalPrice);

        this.setCanceledOnTouchOutside(true);
    }

    @Override
    public void show() {
        super.show();
        setContentView(rootView);
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.width = getScreenWidth(mContext) - 2 * mContext.getResources()
                .getDimensionPixelSize(R.dimen.AU_SPACE10);
        getWindow().setAttributes(layoutParams);
    }

    private static int getScreenWidth(Context context) {
        WindowManager manager =
                (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        return display.getWidth();
    }

    private void setTextView(TextView textView, String text) {
        if (TextUtils.isEmpty(text)) {
            textView.setVisibility(View.GONE);
        } else {
            textView.setText(text);
            textView.setVisibility(View.VISIBLE);
        }
    }
}
