package com.xy.fy.asynctask;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;

import top.codemc.rpcapi.HttpUtilMc;

/**
 * Description:
 * Data：18/01/2017-2:14 PM
 * Author: Mark
 */

public class CheckPayStatusAsynctask extends AsyncTask<String, String, String> {
    private String userName;
    private ResultCallback<Boolean> callback;

    public CheckPayStatusAsynctask(String userName, ResultCallback<Boolean> callback) {
        this.userName = userName;
        this.callback = callback;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        try {
            JSONObject jsonObject = new JSONObject(s);
            int payPermission = jsonObject.optInt("payPermission");
            callback.onResult(payPermission == 1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected String doInBackground(String... params) {
        return HttpUtilMc.queryStringForPost(
                HttpUtilMc.BASE_URL + "CheckPayServlet?username=" + userName.trim());
    }
}
