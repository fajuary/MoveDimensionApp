package dongwei.fajuary.movedimensionapp.loginModel;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.orhanobut.logger.Logger;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import dongwei.fajuary.dongweimoveview.utils.StatusBarUtil;
import dongwei.fajuary.dongweimoveview.utils.ToastHelper;
import dongwei.fajuary.movedimensionapp.R;
import dongwei.fajuary.movedimensionapp.base.AppBaseActivity;
import dongwei.fajuary.movedimensionapp.base.MainActivity;
import dongwei.fajuary.movedimensionapp.dialog.LoginLoadingDialog;
import dongwei.fajuary.movedimensionapp.httpModel.HttpUtils;
import dongwei.fajuary.movedimensionapp.httpModel.StringDialogCallback;

/**
 *
 */
public class LoginActivity extends AppBaseActivity {


    @BindView(R.id.activity_login_phoneNumLoginTxt)
    TextView phoneNumLoginTxt;//手机号码登录

    @BindView(R.id.activity_login_registerProtocolTxt)
    TextView registerProtocolTxt;//注册协议

    @BindView(R.id.activity_login_visitorsLoginTxt)
    TextView visitorsLoginTxt;//游客登录

    @BindView(R.id.activity_login_wechatLoginLin)
    LinearLayout wechatLoginLin;//微信登录


    private LoginLoadingDialog.Builder loadingDialog;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void newInitView() {
        StatusBarUtil.setTranslucent(this,10);


        mUMShareAPI = UMShareAPI.get(this);
        loadingDialog = new LoginLoadingDialog.Builder(this);
        loadingDialog.setTitle("登录中...").setLoadImg(R.drawable.loading_icon).create();
    }

    private UMShareAPI mUMShareAPI;

    @Override
    public void initListener() {
        phoneNumLoginTxt.setOnClickListener(this);
        wechatLoginLin.setOnClickListener(this);
        registerProtocolTxt.setOnClickListener(this);
        visitorsLoginTxt.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void processClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.activity_login_wechatLoginLin://登录
//                getLocalLogin();
                mUMShareAPI.doOauthVerify(this, SHARE_MEDIA.WEIXIN, authListener);
                break;
            case R.id.activity_login_phoneNumLoginTxt://手机号登录
                intent.setClass(this, PhoneNumLoginActivity.class);
                startActivity(intent);
                break;
            case R.id.activity_login_registerProtocolTxt://注册协议
                intent.setClass(this, RegisterProtocolActivity.class);
                startActivity(intent);
                break;

            case R.id.activity_login_visitorsLoginTxt:
                preSaveUtil.remove("token");
                intent.setClass(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                break;
        }
    }

    private Map<String, String> weixinMap = new HashMap<>();
    private Map<String, String> qqMap = new HashMap<>();
    private Map<String, String> weiboMap = new HashMap<>();

    UMAuthListener authListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            //授权开始的回调
            loadingDialog.show();
        }

        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            loadingDialog.dismiss();

            Logger.e("授权数据");

            String openid = "";
            String sns_avatar = "";
            String sns_loginname = "";
            String token = "";
            String sns = "";

            if (data != null) {
                Logger.e(data.toString());

                sns_avatar = data.get("iconurl");
                if (TextUtils.isEmpty(sns_avatar)) {
                    sns_avatar = data.get("profile_image_url");
                }
                sns_loginname = data.get("name");
                if (TextUtils.isEmpty(sns_loginname)) {
                    sns_loginname = data.get("screen_name");
                }
                token = data.get("accessToken");
                if (TextUtils.isEmpty(token)) {
                    token = data.get("access_token");
                }
                if (platform == SHARE_MEDIA.SINA) { // 新浪微博
                    sns = "sina";
                    openid = data.get("uid");

                } else if (platform == SHARE_MEDIA.QQ) { // QQ
                    sns = "qq";
                    openid = data.get("uid");
                    qqMap.put("openid", openid);

                } else if (platform == SHARE_MEDIA.WEIXIN) { // 微信


                    sns = "wechat";
                    openid = data.get("openid");

                    weixinMap.put("openid", openid);
                }
                mUMShareAPI.getPlatformInfo(LoginActivity.this, platform, umAuthListener);

//                thirdTypeLogin(sns,sns_id,token,sns_loginname,sns_avatar);


            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {

            loadingDialog.dismiss();

            ToastHelper.showTextToast(LoginActivity.this, "授权失败");
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            loadingDialog.dismiss();

            ToastHelper.showTextToast(LoginActivity.this, "取消授权");

        }
    };
    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            //授权开始的回调
        }

        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {

            Logger.e("获取授权信息");
            Logger.e(data.toString());
            String nickname;
            String sex;
            String headimgurl;
            String unionid;

            String sexStr;
            String userImgUrl;
            if (platform == SHARE_MEDIA.SINA) { // 新浪微博


            } else if (platform == SHARE_MEDIA.QQ) { // QQ
//
                nickname = data.get("name");
                sex = data.get("sex");
                headimgurl = data.get("iconurl");
                unionid = data.get("unionid");

                if (TextUtils.isEmpty(sex)) {
                    sexStr = "0";
                } else if (sex.equals("男")) {
                    sexStr = "1";
                } else {
                    sexStr = "2";
                }

                qqMap.put("nickname", nickname);
                qqMap.put("sex", sexStr);
                qqMap.put("icon", headimgurl);
                qqMap.put("nickname", nickname);
                qqMap.put("unionid", unionid);

                getLocalLogin("qq", qqMap);

            } else if (platform == SHARE_MEDIA.WEIXIN) { // 微信
                nickname = data.get("name");
                sex = data.get("sex");
                String province = data.get("province");
                String city = data.get("city");
                String country = data.get("country");
                headimgurl = data.get("iconurl");
                unionid = data.get("unionid");

                if (TextUtils.isEmpty(sex)) {
                    sexStr = "0";
                } else if (sex.equals("男")) {
                    sexStr = "1";
                } else {
                    sexStr = "2";
                }

                weixinMap.put("nickname", nickname);
                weixinMap.put("sex", sexStr);
                weixinMap.put("province", province);
                weixinMap.put("city", city);
                weixinMap.put("country", country);
                weixinMap.put("icon", headimgurl);
                weixinMap.put("unionid", unionid);


                getLocalLogin("weixin", weixinMap);
            }

        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            ToastHelper.showTextToast(LoginActivity.this, "授权失败");
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            ToastHelper.showTextToast(LoginActivity.this, "取消授权");

        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Logger.e("requestCode--" + requestCode + "--resultCode-" + resultCode + "---data--" + data);
        mUMShareAPI.onActivityResult(requestCode, resultCode, data);
    }


    private void getLocalLogin(String httpType, Map<String, String> params) {

        int typeInt = 1;
        if (httpType.equals("qq")) {
            typeInt = 1;
        } else if (httpType.equals("weixin")) {
            typeInt = 2;
        }

        OkGo.<String>post(HttpUtils.loginUrl)//
                .tag(this)//
                .cacheKey("getLocalLogin")
                .params("type", typeInt)
                .params(params)        // 这里可以上传参数
                .params("recommendId", 1)
                .execute(new StringDialogCallback(this) {
                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);

                        ToastHelper.showTextToast(LoginActivity.this, "检查网络，请重试...");
                    }

                    @Override
                    public void onSuccess(com.lzy.okgo.model.Response<String> response) {

                        String jsonStr = response.body();
                        Logger.json(jsonStr);

                        try {
                            JSONObject jsonObject = new JSONObject(jsonStr);
                            if (null != jsonObject) {

                                String status = jsonObject.optString("status");

                                String msgStr = jsonObject.optString("msg");
                                String dataToken = jsonObject.optString("data");
                                preSaveUtil.setString("token", dataToken);

                                ToastHelper.showTextToast(LoginActivity.this, msgStr);

                                Intent intent = new Intent();
                                intent.setClass(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }


}
