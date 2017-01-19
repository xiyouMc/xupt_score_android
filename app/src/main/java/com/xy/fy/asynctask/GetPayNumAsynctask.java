package com.xy.fy.asynctask;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;

import top.codemc.rpcapi.HttpUtilMc;

/**
 * Description:
 * Data：18/01/2017-8:09 PM
 * Author: Mark
 */

public class GetPayNumAsynctask extends AsyncTask<String, String, String> {
    private ResultCallback<Double> callback;

    public GetPayNumAsynctask(ResultCallback<Double> callback) {
        this.callback = callback;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        try {
            JSONObject jsonObject = new JSONObject(s);
            String pay = jsonObject.optString("pay");
            this.callback.onResult(Double.parseDouble(pay));
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    protected String doInBackground(String... params) {
        return HttpUtilMc.queryStringForPost(
                HttpUtilMc.BASE_URL + "GetPayNumServlet");
    }
}
