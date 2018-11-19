package dongwei.fajuary.movedimensionapp.loginModel;

import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
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
import dongwei.fajuary.movedimensionapp.base.MainActivity;
import dongwei.fajuary.movedimensionapp.dialog.LoginLoadingDialog;
import dongwei.fajuary.movedimensionapp.httpModel.HttpUtils;
import dongwei.fajuary.movedimensionapp.httpModel.StringDialogCallback;

public class PhoneNumLoginActivity extends BaseHasLeftActivity {


    @BindView(R.id.activity_phonenumlogin_phonenumEdtxt)
    EditText phonenumEdtxt;//手机号码

    @BindView(R.id.activity_phonenumlogin_codenumEdtxt)
    EditText codenumEdtxt;//验证码

    @BindView(R.id.activity_phonenumlogin_getCodeTxt)
    TextView getCodeTxt;//获取验证码

    @BindView(R.id.activity_phonenumlogin_loginTxt)
    TextView loginTxt;//登录


    private TimeCount timeCount;

    private LoginLoadingDialog.Builder loadingDialog;

    @Override
    public int getLayoutId() {
        return R.layout.activity_phonenum_login;
    }

    @Override
    public void newInitView() {
        titleTxt.setText("登录");
        StatusBarUtil.setColor(this, Color.parseColor("#E51C23"));
        headRelayout.setBackgroundColor(Color.parseColor("#E51C23"));

        timeCount = new TimeCount(60000, 1000);
        loadingDialog = new LoginLoadingDialog.Builder(this);
        loadingDialog.setTitle("获取中...").setLoadImg(R.drawable.loading_icon).create();
    }

    @Override
    public void newViewListener() {
        loginTxt.setOnClickListener(this);
        getCodeTxt.setOnClickListener(this);
    }


    public void showLoadingDialog() {
        loadingDialog.show();
    }

    public void setDialogDissmis() {
        loadingDialog.dismiss();

    }

    @Override
    public void newViewClick(View view) {
        switch (view.getId()) {
            case R.id.activity_phonenumlogin_getCodeTxt://获取验证码
                showLoadingDialog();
                getSmsCode();

                break;
            case R.id.activity_phonenumlogin_loginTxt://登录
                if (canclick) {
                    getPhoneLogin();
                }
                break;
        }
    }

    @Override
    public void initData() {

    }

    private void getSmsCode() {
        String phoneStr = phonenumEdtxt.getText().toString().trim().toLowerCase();
        if (TextUtils.isEmpty(phoneStr)) {
            ToastHelper.showTextToast(PhoneNumLoginActivity.this, "请输入手机号");
            return;
        } else if (!SmallFeatureUtils.isMobileNO(phoneStr)) {
            ToastHelper.showTextToast(PhoneNumLoginActivity.this, "请输入正确的手机号");
            return;
        }
        timeCount.start();
        Map<String, String> params = new HashMap<>();
        params.put("phone", phoneStr);
        OkGo.<String>post(HttpUtils.sendMsgCodeUrl)//
                .tag(this)//
                .params(params)        // 这里可以上传参数
                .execute(new StringDialogCallback(this) {
                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastHelper.showTextToast(PhoneNumLoginActivity.this, "请求失败,请重试...");
                        setDialogDissmis();
                    }

                    public void onSuccess(com.lzy.okgo.model.Response<String> response) {
                        setDialogDissmis();
                        String jsonStr = response.body();
                        Logger.json(jsonStr);

                        /**
                         * {"status":200,"msg":"发送验证码成功！","data":null}
                         */
                        try {
                            JSONObject jsonObject = new JSONObject(jsonStr);
                            if (null != jsonObject) {
                                String infoStr = jsonObject.optString("msg");
                                ToastHelper.showTextToast(PhoneNumLoginActivity.this, infoStr);
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
            getCodeTxt.setSelected(true);
            getCodeTxt.setText(millisUntilFinished / 1000 + "秒后重试");
        }

        @Override
        public void onFinish() {
            getCodeTxt.setText("获取验证码");
            getCodeTxt.setClickable(true);
            getCodeTxt.setSelected(false);

        }
    }

    private boolean canclick = true;

    private void getPhoneLogin() {
        String codeStr = codenumEdtxt.getText().toString().trim();
        String phoneStr = phonenumEdtxt.getText().toString().trim().toLowerCase();
        if (TextUtils.isEmpty(phoneStr)) {
            ToastHelper.showTextToast(PhoneNumLoginActivity.this, "请输入手机号");
            return;
        } else if (!SmallFeatureUtils.isMobileNO(phoneStr)) {
            ToastHelper.showTextToast(PhoneNumLoginActivity.this, "请输入正确的手机号");
            return;
        }
        if (TextUtils.isEmpty(codeStr)) {
            ToastHelper.showTextToast(PhoneNumLoginActivity.this, "请输入验证码");
            return;
        }
        canclick = false;
        showLoadingDialog();
        Map<String, String> params = new HashMap<>();
        params.put("phone", phoneStr);
        params.put("smsCode", codeStr);
        OkGo.<String>post(HttpUtils.phoneLoginUrl)//
                .tag(this)//
                .params(params)        // 这里可以上传参数
                .execute(new StringCallback() {
                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastHelper.showTextToast(PhoneNumLoginActivity.this, "请求失败,请重试...");
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
                                ToastHelper.showTextToast(PhoneNumLoginActivity.this, msg);
                                if (status.equals("200")) {
                                    JSONObject dataJson = jsonObject.optJSONObject("data");
                                    if (null != dataJson) {
                                        String token = dataJson.optString("token");
                                        preSaveUtil.setString("token", token);
                                        Intent intent = new Intent();
                                        intent.setClass(PhoneNumLoginActivity.this, MainActivity.class);
                                        startActivity(intent);
                                    }
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

}
