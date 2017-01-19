package com.xy.fy.bmobpay;

import com.dynamicload.framework.framework.BaseMetaInfo;
import com.dynamicload.framework.util.FrameworkUtil;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import c.b.BP;

/**
 * Description:
 * Data：18/01/2017-1:41 PM
 * Author: Mark
 */

public class MetaInfo extends BaseMetaInfo {
    int PLUGINVERSION = 7;
    public MetaInfo() {
        BP.init("82e1cd019ba15322ca4a7da217e866ca");
        int pluginVersion = BP.getPluginVersion(FrameworkUtil.getContext());
        if (pluginVersion < PLUGINVERSION) {// 为0说明未安装支付插件,
            // 否则就是支付插件的版本低于官方最新版
            Toast.makeText(
                    FrameworkUtil.getContext(),
                    pluginVersion == 0 ? "监测到本机尚未安装支付插件,无法进行支付,请先安装插件(无流量消耗)"
                            : "监测到本机的支付插件不是最新版,最好进行更新,请先更新插件(无流量消耗)",
                    Toast.LENGTH_SHORT).show();
//                    installBmobPayPlugin("bp.db");

            installBmobPayPlugin("bp.db");
            return;
        }

    }

    void installBmobPayPlugin(String fileName) {
        try {
            InputStream is = FrameworkUtil.getContext().getAssets().open(fileName);
            File file = new File(Environment.getExternalStorageDirectory()
                    + File.separator + fileName + ".apk");
            if (file.exists())
                file.delete();
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            byte[] temp = new byte[1024];
            int i = 0;
            while ((i = is.read(temp)) > 0) {
                fos.write(temp, 0, i);
            }
            fos.close();
            is.close();

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(Uri.parse("file://" + file),
                    "application/vnd.android.package-archive");
            FrameworkUtil.getContext().startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
