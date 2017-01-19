package com.xy.fy.asynctask;

import android.os.AsyncTask;

import top.codemc.rpcapi.HttpUtilMc;

/**
 * Description:
 * Data：18/01/2017-7:22 PM
 * Author: Mark
 */

public class UpdatePayPermissionAsynctask extends AsyncTask<String, String, String> {
    private String userName, orderNo;

    public UpdatePayPermissionAsynctask(String userName, String orderNo) {
        this.userName = userName;
        this.orderNo = orderNo;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }

    @Override
    protected String doInBackground(String... params) {
        return HttpUtilMc.queryStringForPost(
                HttpUtilMc.BASE_URL + "CheckOrderNoServlet?username=" + userName.trim()+ "&orderNo=" + this.orderNo)
                ;
    }
}
