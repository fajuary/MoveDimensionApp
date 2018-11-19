package dongwei.fajuary.movedimensionapp.personInfoModel;

import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

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
import dongwei.fajuary.movedimensionapp.httpModel.HttpUtils;
import dongwei.fajuary.movedimensionapp.httpModel.StringDialogCallback;
import dongwei.fajuary.movedimensionapp.loginModel.LoginActivity;
import dongwei.fajuary.movedimensionapp.util.AppManager;

/**
 * 意见反馈
 */
public class FeedBackActivity extends BaseHasLeftActivity {

    @BindView(R.id.activity_feedback_feedTitleEdtxt)
    EditText feedTitleEdtxt;//标题
    @BindView(R.id.activity_feedback_feedContentEdtxt)
    EditText feedContentEdtxt;//反馈内容


    @BindView(R.id.activity_feedback_submitFeedTxt)
    TextView submitFeedTxt;//提交反馈

    @Override
    public int getLayoutId() {
        return R.layout.activity_feed_back;
    }

    @Override
    public void newInitView() {
        titleTxt.setText("意见反馈");
        StatusBarUtil.setColor(this, Color.parseColor("#E51C23"));
        headRelayout.setBackgroundColor(Color.parseColor("#E51C23"));

    }

    @Override
    public void newViewListener() {
        submitFeedTxt.setOnClickListener(this);
    }

    @Override
    public void newViewClick(View view) {
        switch (view.getId()){
            case R.id.activity_feedback_submitFeedTxt:
                getAddFeedBackUrl();
                break;
        }
    }
    @Override
    public void initData() {

    }


    private void getAddFeedBackUrl() {
        String feedTitle=feedTitleEdtxt.getText().toString().trim();
        if (TextUtils.isEmpty(feedTitle)){
            ToastHelper.showTextToast(this,"请输入反馈标题");
            return;
        }

        String feedContentStr=feedContentEdtxt.getText().toString().trim();
        if (TextUtils.isEmpty(feedContentStr)){
            ToastHelper.showTextToast(this,"请输入反馈内容");
            return;
        }

        String token=preSaveUtil.getString("token");
        Map<String, String> params = new HashMap<>();
        params.put("token", token);
        params.put("title", feedTitle);
        params.put("content", feedContentStr);
        OkGo.<String>post(HttpUtils.getAddFeedBackUrl)//
                .tag(this)//
                .cacheKey("getAddFeedBackUrl")
                .params(params)        // 这里可以上传参数
                .execute(new StringDialogCallback(this) {
                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);

                        ToastHelper.showTextToast(FeedBackActivity.this,"检查网络，请重试...");

                    }

                    @Override
                    public void onSuccess(com.lzy.okgo.model.Response<String> response) {

                        String jsonStr = response.body();
                        Logger.json(jsonStr);

                        try {
                            JSONObject jsonObject = new JSONObject(jsonStr);
                            if (null != jsonObject) {
                                int statusInt=jsonObject.optInt("status");
                                String msgStr=jsonObject.optString("msg");
                                if (statusInt==200){
                                    AppManager.getAppManager().finishActivity(FeedBackActivity.this);
                                    ToastHelper.showTextToast(FeedBackActivity.this,msgStr);

                                }else if (statusInt==301){
                                    ToastHelper.showTextToast(FeedBackActivity.this,"请重新登录");
                                    Intent intent=new Intent();
                                    intent.setClass(FeedBackActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                }else {
                                    ToastHelper.showTextToast(FeedBackActivity.this,msgStr);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }





}
