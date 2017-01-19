package com.xy.fy.asynctask;

import com.cardsui.ScoreCard.MyPlayCard;
import com.fima.cardsui.objects.CardStack;
import com.fima.cardsui.views.CardUI;
import com.xy.fy.bmobpay.PayServiceImpl;
import com.xy.fy.bmobpay.api.PayListener;
import com.xy.fy.main.LoginActivity;
import com.xy.fy.main.R;
import com.xy.fy.main.ShowScoreActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import java.util.ArrayList;
import java.util.HashMap;

import top.codemc.common.util.ProgressDialogUtil;
import top.codemc.common.util.ScoreUtil;
import top.codemc.common.util.StaticVarUtil;
import top.codemc.common.util.Util;
import top.codemc.common.util.ViewUtil;

public class ShowCardAsyncTask extends AsyncTask<String, String, Boolean> {

    ArrayList<HashMap<String, Object>> listItem;
    private CardUI mCardView;
    private Resources resources;
    private boolean isFirst;
    private Activity mActivity;
    private String scoreJson;

    private ProgressDialogUtil dialog;

    private ShowCardAsyncTask() {
    }

    public ShowCardAsyncTask(Activity mActivity, Resources resources, boolean isFirst,
                             CardUI mCardView, ArrayList<HashMap<String, Object>> listItem, String scoreJson) {
        this.mActivity = mActivity;
        this.resources = resources;
        this.isFirst = isFirst;
        this.mCardView = mCardView;
        this.listItem = listItem;
        this.scoreJson = scoreJson;

        dialog = ProgressDialogUtil.getInstance(mActivity);
    }

    @Override
    protected Boolean doInBackground(String... params) {
        // TODO Auto-generated method stub
        String[] xn = resources.getStringArray(R.array.xn);
        boolean result = false;
        for (int i = 0; i < xn.length; i++) {
            result = showCard(xn[i], isFirst);
        }
        return result;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        // TODO Auto-generated method stub
        super.onPostExecute(result);
        if (result) {
            mCardView.refresh();
        }
        if (!Util.isRecordLoginMessage(mActivity)) {
            Util.saveDeviceInfo(mActivity);
            Util.uploadDevInfos(mActivity);
        }
        dialog.dismiss();
    }

    private boolean showCard(String xn, boolean isFirst) {
        String first_score = "";
        String second_score = "";

        if (isFirst || (ScoreUtil.mapScoreOne.isEmpty() && ScoreUtil.mapScoreTwo.isEmpty())) {
            first_score = getScore(xn, "1") == null ? "" : getScore(xn, "1").toString();
            second_score = getScore(xn, "2") == null ? "" : getScore(xn, "2").toString();
            ScoreUtil.mapScoreOne.put(xn, first_score);
            ScoreUtil.mapScoreTwo.put(xn, second_score);
        } else {
            first_score = ScoreUtil.mapScoreOne.get(xn) == null ? "" : ScoreUtil.mapScoreOne.get(xn);
            second_score = ScoreUtil.mapScoreTwo.get(xn) == null ? "" : ScoreUtil.mapScoreTwo.get(xn);
        }

        // add one card, and then add another one to the last stack.
        String xqs_str = "";
        if (!first_score.equals("")) {
            xqs_str += "第一学期,";
            CardStack stackPlay = new CardStack();
            stackPlay.setTitle(xn);
            mCardView.addStack(stackPlay);
            MyPlayCard _myPlayCard = new MyPlayCard("第一学期", first_score, "#33b6ea", "#33b6ea", true,
                    false);
            String[][] first_score_array = getScoreToArray(first_score);
            _myPlayCard.setOnClickListener(
                    new ScoreClass(first_score_array.length, first_score_array, xn + " 第一学期"));
            mCardView.addCard(_myPlayCard);
            // mCardView.addCardToLastStack(new
            // MyCard("By Androguide & GadgetCheck"));
        }

        if (!second_score.equals("")) {
            xqs_str += "第二学期,";
            MyPlayCard myCard = new MyPlayCard("第二学期", second_score, "#e00707", "#e00707", false, true);
            String[][] second_score_array = getScoreToArray(second_score);
            myCard.setOnClickListener(
                    new ScoreClass(second_score_array.length, second_score_array, xn + " 第二学期"));
            mCardView.addCardToLastStack(myCard);
        }
        if (xqs_str.length() != 0) {
            xqs_str = xqs_str.substring(0, xqs_str.length() - 1);
            StaticVarUtil.list_Rank_xnAndXq.put(xn, xqs_str);
        }

        return true;

    }

    private StringBuilder getScore(String xn, String xq) {
        StringBuilder result = null;
        if (listItem != null) {
            result = new StringBuilder();
            JSONObject jsonObject;
            try {
                jsonObject = new JSONObject(scoreJson);
                JSONArray jsonArray = (JSONArray) jsonObject.get("liScoreModels");// ???
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject o = (JSONObject) jsonArray.get(i);
                    if (o.get("xn").equals(xn)) {
                        JSONArray jsonArray2 = (JSONArray) o.get("list_xueKeScore");
                        for (int j = 0; j < jsonArray2.length(); j++) {
                            JSONObject jsonObject2 = (JSONObject) jsonArray2.get(j);
                            if (jsonObject2.get("xq").equals(xq)) {
                                StaticVarUtil.kcdmList.put(
                                        jsonObject2.get("kcmc") == null ? " " : jsonObject2.get("kcmc").toString(),
                                        jsonObject2.get("kcdm").toString() + "|" + xn + "|" + xq);
                                result.append(
                                        jsonObject2.get("kcmc") == null ? " " : jsonObject2.get("kcmc").toString());
                                result.append("--"
                                        + jsonObject2.get("cj")
                                        .toString()
                                        + (jsonObject2.get("bkcj").equals(" ") ? " "
                                        : ("(" + jsonObject2.get("bkcj").toString() + ")"))
                                );
                                result.append(jsonObject2.get("pscj")
                                        .equals("") ? "/" : "--" + jsonObject2.get("pscj").toString());
                                result.append(jsonObject2.get("qmcj")
                                        .equals("") ? "/" : "--" + jsonObject2.get("qmcj").toString());
                                result.append("\n");
                            }
                        }
                    }
                }
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
        return result;
    }

    /*
     *
     * @param score
     *
     * @return
     */
    private String[][] getScoreToArray(String score) {
        String[] s = score.split("\n");
        String[][] result = new String[s.length][4];
        for (int i = 0; i < result.length; i++) {
            result[i] = s[i].split("--");
        }
        return result;
    }

    /*
     *
     * @author Administrator 2014-7-23
     */
    class ScoreClass implements OnClickListener {
        int col;
        String[][] score;
        String xn;
        private PayServiceImpl mPayService = new PayServiceImpl();

        public ScoreClass(int col, String[][] score, String xn) {
            this.col = col;
            this.score = score;
            this.xn = xn;
        }

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            if (Util.isFastDoubleClick()) {
                return;
            }
            CheckPayStatusAsynctask checkPayStatusAsynctask = new CheckPayStatusAsynctask(StaticVarUtil.student.getAccount(), new ResultCallback<Boolean>() {
                @Override
                public void onResult(Boolean aBoolean) {
                    if (aBoolean) {
                        Intent i = new Intent();
                        i.setClass(mActivity, ShowScoreActivity.class);
                        Bundle b = new Bundle();
                        b.putString("col", String.valueOf(col));
                        for (int j = 0; j < score.length; j++) {
                            b.putStringArray("score" + j, score[j]);
                        }
                        b.putString("xn_and_xq", xn);
                        i.putExtras(b);
                        mActivity.startActivity(i);
                    } else {
                        GetPayNumAsynctask getPayNumAsynctask = new GetPayNumAsynctask(new ResultCallback<Double>() {
                            @Override
                            public void onResult(Double aDouble) {
                                mPayService.pay(mActivity, "查看平时和卷面成绩", "charge", aDouble, true, new PayListener() {
                                    private String orderNoStr;

                                    @Override
                                    public void orderId(String orderId) {
                                        this.orderNoStr = orderId;
                                        Log.i("ScoreClass", "orderId:" + orderId);
                                    }

                                    @Override
                                    public void succeed() {
                                        Log.i("ScoreClass", "succeed");
                                        UpdatePayPermissionAsynctask updatePayPermissionAsynctask = new UpdatePayPermissionAsynctask(StaticVarUtil.student.getAccount(), this.orderNoStr);
                                        updatePayPermissionAsynctask.execute();
                                    }

                                    @Override
                                    public void fail(int code, String reason) {

                                    }

                                    @Override
                                    public void unKnow() {

                                    }
                                });
                            }
                        });
                        getPayNumAsynctask.execute();

                    }
                }
            });
            checkPayStatusAsynctask.execute();
        }
    }
}
