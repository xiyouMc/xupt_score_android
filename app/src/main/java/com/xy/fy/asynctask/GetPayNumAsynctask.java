package com.xy.fy.asynctask;

import com.xy.fy.model.PayNumModel;

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
    private ResultCallback<PayNumModel> callback;

    public GetPayNumAsynctask(ResultCallback<PayNumModel> callback) {
        this.callback = callback;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        try {
            JSONObject jsonObject = new JSONObject(s);
            double pay = Double.parseDouble(jsonObject.optString("pay"));
            double old_pay = Double.parseDouble(jsonObject.optString("old_pay"));
            String content = jsonObject.optString("content");
            PayNumModel payNumModel = new PayNumModel();
            payNumModel.setPay(pay);
            payNumModel.setContent(content);
            payNumModel.setOld_pay(old_pay);
            this.callback.onResult(payNumModel);
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
