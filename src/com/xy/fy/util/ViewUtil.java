package com.xy.fy.util;

import com.xy.fy.main.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.Gravity;
import android.widget.Toast;

/**
 * view帮助类
 */
public class ViewUtil {

  /**
   * Toast
   */
  private static Toast mToast;

  public interface DialogCallback {
    void onPost();
  }

  public static void showToast(Context context, String text) {
    if (mToast == null) {
      mToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
    } else {
      mToast.setText(text);
      mToast.setDuration(Toast.LENGTH_SHORT);
    }
    mToast.show();
  }

  public static void cancelToast() {
    if (mToast != null) {
      mToast.cancel();
    }
  }

  public static void toastLength(String string, Activity activity) {
    Toast toast = Toast.makeText(activity, string, Toast.LENGTH_LONG);
    toast.setGravity(Gravity.CENTER, 0, 0);
    // LayoutInflater inflater = LayoutInflater.from(activity);
    // View view = inflater.inflate(R.layout, null);
    // toast.setView(view);
    toast.show();
  }

  public static void showDialog(String string, String title, final Activity activity,
      boolean isShowNegative) {
    Builder builder = new AlertDialog.Builder(activity);
    builder.setTitle(title);
    builder.setMessage(string);
    builder.setPositiveButton(activity.getResources().getString(R.string.ok), new OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {
        dialog.cancel();
      }
    });
    if (isShowNegative) {
      builder.setNegativeButton(activity.getResources().getString(R.string.cancle), new OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
          dialog.cancel();
        }
      });
    }
    Dialog dialog = builder.create();
    dialog.show();
  }

  public static void showDialog(String string, String title, final Activity activity,
      boolean isShowNegative, final DialogCallback dialogCallback) {
    Builder builder = new AlertDialog.Builder(activity);
    builder.setTitle(title);
    builder.setMessage(string);
    builder.setPositiveButton(activity.getResources().getString(R.string.ok), new OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {
        dialog.cancel();
        dialogCallback.onPost();
      }
    });
    if (isShowNegative) {
      builder.setNegativeButton(activity.getResources().getString(R.string.cancel), new OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
          dialog.cancel();
        }
      });
    }
    Dialog dialog = builder.create();
    dialog.show();
  }

  public static ProgressDialog getProgressDialog(Context mContext, String title) {
    ProgressDialog progressDialog = new ProgressDialog(mContext);// 实例化
    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);// 设置进度条风格，风格为圆形，旋转的
    progressDialog.setTitle(title);// 设置ProgressDialog 标题
    progressDialog.setMessage(mContext.getResources().getString(R.string.waiting));// 设置ProgressDialog 提示信息
    progressDialog.setIndeterminate(false);// 设置ProgressDialog
    progressDialog.setCancelable(false);// 是否可以按退回按键取消
    return progressDialog;
  }
}
