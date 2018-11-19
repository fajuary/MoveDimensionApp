package dongwei.fajuary.movedimensionapp.videoModel;

import android.content.Intent;
import android.os.Build;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

import com.aliyun.vodplayer.media.AliyunVidSts;
import com.aliyun.vodplayer.media.AliyunVodPlayer;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import dongwei.fajuary.dongweimoveview.utils.StatusBarUtil;
import dongwei.fajuary.dongweimoveview.utils.ToastHelper;
import dongwei.fajuary.movedimensionapp.R;
import dongwei.fajuary.movedimensionapp.base.AppBaseActivity;
import dongwei.fajuary.movedimensionapp.base.ImageClickInterface;
import dongwei.fajuary.movedimensionapp.httpModel.HttpUtils;
import dongwei.fajuary.movedimensionapp.httpModel.StringDialogCallback;
import dongwei.fajuary.movedimensionapp.loginModel.LoginActivity;
import dongwei.fajuary.movedimensionapp.util.AppManager;

/**
 * 视频详情
 */
public class VideoDetailsActivity extends AppBaseActivity {

    private AliyunVodPlayer aliyunVodPlayer;
    private String videoId;
    private AliyunVidSts mVidSts;

    @BindView(R.id.activity_videodetails_mSurfaceView)
    SurfaceView mSurfaceView;//视频展示

    @BindView(R.id.activity_videodetails_mWebView)
    WebView mWebView;//分享到QQ好友

    @BindView(R.id.activity_videodetails_leftRelayout)
    RelativeLayout leftRelayout;//向左
    @BindView(R.id.activity_videodetails_shareRelayout)
    RelativeLayout shareRelayout;//分享到QQ好友

    @Override
    public int getLayoutId() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        StatusBarUtil.setImgTransparent(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow ().getDecorView ().setSystemUiVisibility (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        return R.layout.activity_video_details;
    }


    private int type;
    private long addTime;
    private String webUrl;

    private String videoImgPath;
    @Override
    public void newInitView() {
        mWebView.setBackgroundColor(0);

        aliyunVodPlayer = new AliyunVodPlayer(this);
        aliyunVodPlayer.setAutoPlay(true);
        aliyunVodPlayer.setDisplay(mSurfaceView.getHolder());
        //循环播放
        aliyunVodPlayer.setCirclePlay(true);
        mSurfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                aliyunVodPlayer.setDisplay(holder);
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                aliyunVodPlayer.surfaceChanged();
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });

        if (null != getIntent()) {
            videoId = getIntent().getStringExtra("videoId");
            videoImgPath = getIntent().getStringExtra("videoImgPath");
            type=getIntent().getIntExtra("type",-1);
            addTime=getIntent().getLongExtra("addTime",-1);
            webUrl="http://weixinlive.dongweinet.com/specialEffects/1.html?id="+type+"&time="+addTime;

            mVidSts = new AliyunVidSts();
            mVidSts.setVid(videoId);
        }


        initWebView();
        mWebView.loadUrl(webUrl);
    }
    private void aliVodStop(){
        if (null!=aliyunVodPlayer){
            aliyunVodPlayer.stop();
        }
    }
    private void aliVodStart(){

    }

    @Override
    public void initListener() {

        leftRelayout.setOnClickListener(this);
        shareRelayout.setOnClickListener(this);


    }

    @Override
    public void processClick(View view) {
        Intent intent=new Intent();
        switch (view.getId()){
            case R.id.activity_videodetails_leftRelayout:
                AppManager.getAppManager().finishActivity(this);
                break;
            case R.id.activity_videodetails_shareRelayout://分享
                intent.setClass(this,SharingStyleActivity.class);
                intent.putExtra("videoImgPath",videoImgPath);
                intent.putExtra("videoId",videoId);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void initData() {
        getAssumeRoleUrl();
    }

    //    获取阿里云临时的凭证
    private void getAssumeRoleUrl() {
        String token = preSaveUtil.getString("token");
        Map<String, String> params = new HashMap<>();
        params.put("token", token);
        OkGo.<String>post(HttpUtils.getAssumeRoleUrl)//
                .tag(this)//
                .cacheKey("getAssumeRoleUrl")
                .params(params)        // 这里可以上传参数
                .execute(new StringDialogCallback(this) {
                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastHelper.showTextToast(VideoDetailsActivity.this, "检查网络，请重试...");
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
                                if (status.equals("200")) {

                                    JSONObject dataJson = jsonObject.optJSONObject("data");
                                    if (null != dataJson) {
                                        String expiration = dataJson.optString("expiration");
                                        String accessKeyId = dataJson.optString("accessKeyId");
                                        String accessKeySecret = dataJson.optString("accessKeySecret");
                                        String securityToken = dataJson.optString("securityToken");

                                        mVidSts.setAcId(accessKeyId);
                                        mVidSts.setAkSceret(accessKeySecret);
                                        mVidSts.setSecurityToken(securityToken);
                                        aliyunVodPlayer.prepareAsync(mVidSts);
                                        //开始播放
                                        aliyunVodPlayer.start();
                                    }
                                } else if (status.equals("301")) {
                                    ToastHelper.showTextToast(VideoDetailsActivity.this, "请重新登录");
                                    Intent intent = new Intent();
                                    intent.setClass(VideoDetailsActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                } else {
                                    ToastHelper.showTextToast(VideoDetailsActivity.this, msgStr);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
    private void initWebView() {
        mWebView.addJavascriptInterface(new ImageClickInterface(this), "injectedObject");
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setTextZoom(100);
        webSettings.setDisplayZoomControls(false);
        webSettings.setJavaScriptEnabled(true); // 设置支持javascript脚本
        webSettings.setAllowFileAccess(true); // 允许访问文件
        webSettings.setBuiltInZoomControls(true); // 设置显示缩放按钮
        webSettings.setSupportZoom(true); // 支持缩放
        webSettings.setLoadWithOverviewMode(true);
        /**
         *
         * 用WebView显示图片，可使用这个参数 设置网页布局类型： 1、LayoutAlgorithm.NARROW_COLUMNS ：
         * 适应内容大小 2、LayoutAlgorithm.SINGLE_COLUMN:适应屏幕，内容将自动缩放
         */
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        mWebView.setWebViewClient(mWebViewClient);
    }
    private WebViewClient mWebViewClient = new WebViewClient() {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    };

    private boolean isOnPause;
    @Override
    public void onPause() {
        super.onPause();
        try {
            if (mWebView != null) {
                mWebView.getClass().getMethod("onPause").invoke(mWebView, (Object[]) null);
                isOnPause = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // 重点:播放器没有停止,也没有暂停的时候,在activity的pause的时候也需要pause
        if (aliyunVodPlayer != null) {
            aliyunVodPlayer.pause();
        }
    }
    /**
     * 当Activity执行onResume()时让WebView执行resume
     */
    @Override
    protected void onResume() {
        super.onResume();
        try {
            if (isOnPause) {
                if (mWebView != null) {
                    mWebView.getClass().getMethod("onResume").invoke(mWebView, (Object[]) null);
                }
                isOnPause = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (aliyunVodPlayer != null) {
            aliyunVodPlayer.resume();
        }
    }

    /**
     * 该处的处理尤为重要:
     * 应该在内置缩放控件消失以后,再执行mWebView.destroy()
     * 否则报错WindowLeaked
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mWebView != null) {
            mWebView.getSettings().setBuiltInZoomControls(true);
            mWebView.setVisibility(View.GONE);
        }
        isOnPause = false;
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (aliyunVodPlayer != null) {
            aliyunVodPlayer.stop();
        }
    }
}
