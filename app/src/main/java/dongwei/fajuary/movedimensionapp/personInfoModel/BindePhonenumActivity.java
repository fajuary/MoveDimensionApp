package dongwei.fajuary.movedimensionapp.personInfoModel;

import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import dongwei.fajuary.dongweimoveview.utils.SmallFeatureUtils;
import dongwei.fajuary.dongweimoveview.utils.StatusBarUtil;
import dongwei.fajuary.dongweimoveview.utils.ToastHelper;
import dongwei.fajuary.movedimensionapp.R;
import dongwei.fajuary.movedimensionapp.base.BaseHasLeftActivity;
import dongwei.fajuary.movedimensionapp.dialog.LoginLoadingDialog;
import dongwei.fajuary.movedimensionapp.httpModel.HttpUtils;
import dongwei.fajuary.movedimensionapp.httpModel.StringDialogCallback;
import dongwei.fajuary.movedimensionapp.util.AppManager;

/**
 * 绑定手机号
 */
public class BindePhonenumActivity extends BaseHasLeftActivity {

    @BindView(R.id.activity_feedback_bindephoneNumLin)
    LinearLayout bindephoneNumLin;//绑定手机号
    @BindView(R.id.activity_feedback_phoneNumTxt)
    TextView phoneNumTxt;//绑定手机号


    @BindView(R.id.activity_feedback_getCodeTxt)
    TextView getCodeTxt;//获取验证码
    @BindView(R.id.activity_feedback_phoneNumEdtxt)
    EditText phoneNumEdtxt;//手机号
    @BindView(R.id.activity_feedback_validateCodeEdtxt)
    EditText validateCodeEdtxt;//验证码
    @BindView(R.id.activity_feedback_bindingTxt)
    TextView bindingTxt;//绑定手机号

    private String bindphone;

    private TimeCount timeCount;

    private LoginLoadingDialog.Builder loadingDialog;

    @Override
    public int getLayoutId() {
        return R.layout.activity_binde_phonenum;
    }

    @Override
    public void newInitView() {
        titleTxt.setText("绑定手机号");
        StatusBarUtil.setColor(this, Color.parseColor("#E51C23"));
        headRelayout.setBackgroundColor(Color.parseColor("#E51C23"));

        if (TextUtils.isEmpty(bindphone)){
            bindephoneNumLin.setVisibility(View.GONE);
        }else {
            bindephoneNumLin.setVisibility(View.VISIBLE);
            phoneNumTxt.setText(bindphone);
        }
        timeCount = new TimeCount(60000, 1000);
        loadingDialog = new LoginLoadingDialog.Builder(this);
        loadingDialog.setTitle("获取中...").setLoadImg(R.drawable.loading_icon).create();
    }

    @Override
    public void newViewListener() {
        getCodeTxt.setOnClickListener(this);
        bindingTxt.setOnClickListener(this);

    }

    public void showLoadingDialog() {
        loadingDialog.show();
    }

    public void setDialogDissmis() {
        loadingDialog.dismiss();

    }
    @Override
    public void newViewClick(View view) {
        switch (view.getId()){
            case R.id.activity_feedback_getCodeTxt://获取验证码
                showLoadingDialog();
                getSendUrl();
                break;
            case R.id.activity_feedback_bindingTxt://绑定手机号

                if (canclick) {
                    getPhoneLogin();
                }
                break;
        }
    }
    @Override
    public void initData() {

    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK,new Intent());
        super.onBackPressed();
    }

    private boolean canclick = true;
    private void getPhoneLogin() {
        String codeStr = validateCodeEdtxt.getText().toString().trim();
        String phoneStr = phoneNumEdtxt.getText().toString().trim().toLowerCase();
        if (TextUtils.isEmpty(phoneStr)) {
            ToastHelper.showTextToast(this, "请输入手机号");
            return;
        } else if (!SmallFeatureUtils.isMobileNO(phoneStr)) {
            ToastHelper.showTextToast(this, "请输入正确的手机号");
            return;
        }
        if (TextUtils.isEmpty(codeStr)) {
            ToastHelper.showTextToast(this, "请输入验证码");
            return;
        }
        canclick = false;
        showLoadingDialog();

        String token=preSaveUtil.getString("token");
        Map<String, String> params = new HashMap<>();
        params.put("token", token);
        params.put("phone", phoneStr);
        params.put("smsCode", codeStr);
        OkGo.<String>post(HttpUtils.bindingPhoneUrl)//
                .tag(this)//
                .params(params)        // 这里可以上传参数
                .execute(new StringCallback() {
                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastHelper.showTextToast(BindePhonenumActivity.this, "请求失败,请重试...");
                        setDialogDissmis();
                        canclick = true;
                    }

                    public void onSuccess(com.lzy.okgo.model.Response<String> response) {
                        canclick = true;

                        setDialogDissmis();
                        String jsonStr = response.body();
                        Logger.json(jsonStr);

                        try {
                            JSONObject jsonObject = new JSONObject(jsonStr);
                            if (null != jsonObject) {
                                String status = jsonObject.optString("status");
                                String msg = jsonObject.optString("msg");
                                ToastHelper.showTextToast(BindePhonenumActivity.this, msg);
                                if (status.equals("200")) {
                                    setResult(RESULT_OK,new Intent());
                                    AppManager.getAppManager().finishActivity(BindePhonenumActivity.this);
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
    private class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            getCodeTxt.setClickable(false);//防止重复点击
            getCodeTxt.setText(millisUntilFinished / 1000 + "秒后重试");
        }
        @Override
        public void onFinish() {
            getCodeTxt.setText("获取验证码");
            getCodeTxt.setClickable(true);
        }
    }

    private void getSendUrl() {

        String phoneNumStr=phoneNumEdtxt.getText().toString().trim();
        if (TextUtils.isEmpty(phoneNumStr)){
            ToastHelper.showTextToast(this,"请输入手机号");
            return;
        } else if (!SmallFeatureUtils.isMobileNO(phoneNumStr)){
            ToastHelper.showTextToast(this,"请输入正确的手机号");
            return;
        }

        Map<String, String> params = new HashMap<>();
        params.put("phone", phoneNumStr);

        OkGo.<String>post(HttpUtils.sendMsgCodeUrl)//
                .tag(this)//
                .cacheKey("sendMsgCodeUrl")
                .params(params)        // 这里可以上传参数
                .execute(new StringDialogCallback(this) {
                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);

                        ToastHelper.showTextToast(BindePhonenumActivity.this,"检查网络，请重试...");
                        setDialogDissmis();
                    }

                    @Override
                    public void onSuccess(com.lzy.okgo.model.Response<String> response) {
                        setDialogDissmis();

                        String jsonStr = response.body();
                        Logger.json(jsonStr);

                        try {
                            JSONObject jsonObject = new JSONObject(jsonStr);
                            if (null != jsonObject) {


                                String msgStr = jsonObject.optString("msg");

                                ToastHelper.showTextToast(BindePhonenumActivity.this,msgStr);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
