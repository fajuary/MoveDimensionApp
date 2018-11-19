package dongwei.fajuary.movedimensionapp.videoModel;

import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.orhanobut.logger.Logger;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import dongwei.fajuary.dongweimoveview.utils.StatusBarUtil;
import dongwei.fajuary.dongweimoveview.utils.ToastHelper;
import dongwei.fajuary.movedimensionapp.R;
import dongwei.fajuary.movedimensionapp.base.AppBaseActivity;
import dongwei.fajuary.movedimensionapp.httpModel.HttpUtils;
import dongwei.fajuary.movedimensionapp.httpModel.StringDialogCallback;
import dongwei.fajuary.movedimensionapp.loginModel.LoginActivity;
import dongwei.fajuary.movedimensionapp.util.AppManager;

public class SharingStyleActivity extends AppBaseActivity {

    @BindView(R.id.activity_sharestyle_leftRelayout)
    RelativeLayout leftRelayout;//返回
    @BindView(R.id.activity_sharestyle_backhomeRelayout)
    RelativeLayout backhomeRelayout;//回到首页


    @BindView(R.id.activity_sharestyle_nicknameEdtxt)
    EditText nicknameEdtxt;//昵称
    @BindView(R.id.activity_sharestyle_blessingEdtxt)
    EditText blessingEdtxt;//祝福语
    @BindView(R.id.activity_sharestyle_selectOpenImg)
    ImageView selectOpenImg;//是否公开


    @BindView(R.id.activity_sharestyle_wechatLin)
    LinearLayout wechatLin;//分享到微信
    @BindView(R.id.activity_sharestyle_wxcircleLin)
    LinearLayout wxcircleLin;//分享到朋友圈
    @BindView(R.id.activity_sharestyle_qqLin)
    LinearLayout qqLin;//分享到QQ好友


    @Override
    public int getLayoutId() {
        return R.layout.activity_sharing_style;
    }

    private boolean openBl = true;
    private String videoImgPath;
    private int videoId;//

    @Override
    public void newInitView() {
        AppManager.getAppManager().addShareAct(this);

        StatusBarUtil.setColor(this, Color.parseColor("#101010"));


        selectOpenImg.setSelected(openBl);

        if (null != getIntent()) {
            videoImgPath = getIntent().getStringExtra("videoImgPath");
            videoId = getIntent().getIntExtra("videoId",-1);
        }
        String nickname=preSaveUtil.getString("nickname");
        nicknameEdtxt.setText(nickname);
    }

    @Override
    public void initListener() {
        leftRelayout.setOnClickListener(this);
        backhomeRelayout.setOnClickListener(this);
        selectOpenImg.setOnClickListener(this);

        wechatLin.setOnClickListener(this);
        wxcircleLin.setOnClickListener(this);
        qqLin.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    private UMWeb umWeb;

    @Override
    public void processClick(View view) {

//        umWeb = new UMWeb(withTargetUrl);

        switch (view.getId()) {
            case R.id.activity_sharestyle_leftRelayout://返回
                AppManager.getAppManager().finishActivity(this);
                break;
            case R.id.activity_sharestyle_backhomeRelayout://回到首页
                AppManager.getAppManager().exitShareActs();

                break;
            case R.id.activity_sharestyle_wechatLin://分享到微信
                shareplam = SHARE_MEDIA.WEIXIN;
                updateVideoUrl();
                break;
            case R.id.activity_sharestyle_wxcircleLin://分享到朋友圈
                shareplam = SHARE_MEDIA.WEIXIN_CIRCLE;
                updateVideoUrl();

                break;
            case R.id.activity_sharestyle_qqLin://分享到QQ好友
                shareplam = SHARE_MEDIA.QQ;
                updateVideoUrl();

                break;
            case R.id.activity_sharestyle_selectOpenImg://是否公开
                openBl = !openBl;
                selectOpenImg.setSelected(openBl);
                if (openBl) {
                    videoType = "1";
                } else {
                    videoType = "2";
                }

                break;
        }
    }

    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA platform) {
            ToastHelper.showTextToast(SharingStyleActivity.this, "分享成功啦");
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            ToastHelper.showTextToast(SharingStyleActivity.this, "分享失败啦");
        }

        @Override
        public void onStart(SHARE_MEDIA share_media) {
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            ToastHelper.showTextToast(SharingStyleActivity.this, "分享取消了");

        }
    };

    private String videoType = "1";
    private SHARE_MEDIA shareplam;

    private void updateVideoUrl() {
        final String blessingStr = blessingEdtxt.getText().toString().trim();
        if (TextUtils.isEmpty(blessingStr)) {
            ToastHelper.showTextToast(this, "祝福语为空");
            return;
        }
        final String nickname = nicknameEdtxt.getText().toString().trim();
        String token = preSaveUtil.getString("token");
        Map<String, String> params = new HashMap<>();
        params.put("token", token);
        params.put("title",nickname);
        params.put("blessing",blessingStr);
        params.put("type", videoType);//imgUrl
        OkGo.<String>post(HttpUtils.updateVideoUrl)//
                .tag(this)//
                .cacheKey("updateVideoUrl")
                .params("id", videoId)
                .params(params)        // 这里可以上传参数
                .execute(new StringDialogCallback(this) {
                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastHelper.showTextToast(SharingStyleActivity.this, "检查网络，请重试...");
                    }

                    @Override
                    public void onSuccess(com.lzy.okgo.model.Response<String> response) {

                        String jsonStr = response.body();
                        Logger.json(jsonStr);

                        Intent intent = new Intent();
                        try {
                            JSONObject jsonObject = new JSONObject(jsonStr);
                            if (null != jsonObject) {
                                String status = jsonObject.optString("status");
                                String msgStr = jsonObject.optString("msg");
                                if (status.equals("200")) {
                                    String withTargetUrl = jsonObject.optString("data");
                                    umWeb = new UMWeb(withTargetUrl);
                                    UMImage umImage = new UMImage(SharingStyleActivity.this, videoImgPath);
                                    umWeb.setThumb(umImage);
                                    umWeb.setTitle(nickname + "给您拜年了");//标题
                                    umWeb.setDescription("祝您：" + blessingStr);//描述
                                    new ShareAction(SharingStyleActivity.this)
                                            .setPlatform(shareplam)//传入平台
                                            .withMedia(umWeb)//分享链接
                                            .setCallback(umShareListener)//回调监听器
                                            .share();
                                } else if (status.equals("301")) {
                                    ToastHelper.showTextToast(SharingStyleActivity.this, "请重新登录");
                                    intent.setClass(SharingStyleActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                } else {
                                    ToastHelper.showTextToast(SharingStyleActivity.this, msgStr);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        UMShareAPI.get(this).release();

    }
}
