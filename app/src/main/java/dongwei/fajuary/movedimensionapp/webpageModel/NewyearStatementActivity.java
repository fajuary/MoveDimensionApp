package dongwei.fajuary.movedimensionapp.webpageModel;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

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
import dongwei.fajuary.movedimensionapp.base.BaseHasLeftActivity;
import dongwei.fajuary.movedimensionapp.base.ImageClickInterface;
import dongwei.fajuary.movedimensionapp.httpModel.HttpUtils;
import dongwei.fajuary.movedimensionapp.httpModel.StringDialogCallback;
import dongwei.fajuary.movedimensionapp.loginModel.LoginActivity;

/**
 * 拜年了声明
 */
public class NewyearStatementActivity extends BaseHasLeftActivity {


    @BindView(R.id.activity_newyearstate_mWebView)
    WebView mWebView;//网页展示

    // 网页链接
    private String mUrl="";

    @Override
    public int getLayoutId() {
        return R.layout.activity_newyear_statement;
    }

    @Override
    public void newInitView() {
        titleTxt.setText("排行榜");

        StatusBarUtil.setColor(this,Color.parseColor("#E51C23"));
        headRelayout.setBackgroundColor(Color.parseColor("#E51C23"));

        mUrl="http://weixinlive.dongweinet.com/ranking?"+System.currentTimeMillis()/1000;
        initWebView();
        mWebView.loadUrl(mUrl);
    }

    @Override
    public void newViewListener() {

    }

    @Override
    public void newViewClick(View view) {

    }


    @Override
    public void initData() {
//        getRuleByTypeUrl();
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

    private void getRuleByTypeUrl() {
        Map<String, String> params = new HashMap<>();
        params.put("type", "3");
        OkGo.<String>post(HttpUtils.getRuleByTypeUrl)//
                .tag(this)//
                .cacheKey("getRuleByTypeUrl")
                .params(params)        // 这里可以上传参数
                .execute(new StringDialogCallback(this) {
                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastHelper.showTextToast(NewyearStatementActivity.this,"检查网络，请重试...");
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

                                if (status.equals("200")){
                                    JSONObject dataJson=jsonObject.optJSONObject("data");
                                    if (null!=dataJson){
                                        String content=dataJson.optString("content");
                                        mWebView.loadData(content,
                                                "text/html",
                                                "utf-8");
                                    }
                                }else if (status.equals("301")){
                                    ToastHelper.showTextToast(NewyearStatementActivity.this,"请重新登录");
                                    Intent intent=new Intent();
                                    intent.setClass(NewyearStatementActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                }else {
                                    ToastHelper.showTextToast(NewyearStatementActivity.this,msgStr);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
