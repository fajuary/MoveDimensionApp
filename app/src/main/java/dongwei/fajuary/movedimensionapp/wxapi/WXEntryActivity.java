package dongwei.fajuary.movedimensionapp.wxapi;


import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;

import com.orhanobut.logger.Logger;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.socialize.weixin.view.WXCallbackActivity;

import dongwei.fajuary.dongweimoveview.utils.ToastHelper;

public class WXEntryActivity extends WXCallbackActivity implements IWXAPIEventHandler {

    public static IWXAPI api;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.e("什么情况---");
        api = WXAPIFactory.createWXAPI(this, "wxb6f21429e3e90611");
        api.handleIntent(getIntent(), this);
    }
    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        api.handleIntent(intent, this);
        Logger.e("什么情况---");
        super.onNewIntent(intent);

    }

    @Override
    public void onReq(BaseReq baseReq) {
        Logger.e("结果1---" + baseReq.toString());

    }


    @Override
    public void onResp(BaseResp baseResp) {
        Logger.e("结果2---" + baseResp.toString());
        Logger.e("分享回调值---" + baseResp.errCode);
        Intent intent = new Intent();
        if (baseResp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            if (baseResp.errCode == 0) {
                // 发送无序广播


            } else {
                ToastHelper.showTextToast(this,"取消支付");
            }
        }else {
            super.onResp(baseResp);//一定要加super，实现我们的方法，否则不能回调
        }
        finish();

    }


}
