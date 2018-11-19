package dongwei.fajuary.movedimensionapp.wxapi;


import android.content.Intent;
import android.os.Bundle;

import com.orhanobut.logger.Logger;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.socialize.weixin.view.WXCallbackActivity;

import dongwei.fajuary.dongweimoveview.utils.ToastHelper;
import dongwei.fajuary.movedimensionapp.Alipay.PayUtils;
import dongwei.fajuary.movedimensionapp.videoModel.FinalReceiver;


//WXPayEntryActivity
public class WXPayEntryActivity extends
        WXCallbackActivity implements IWXAPIEventHandler {

    public static IWXAPI api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.e("初始结果---");

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
        String orderID= PayUtils.orderID;
        int specialEffectsInt=PayUtils.specialEffectsInt;
        Intent intent = new Intent();
        if (baseResp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            if (baseResp.errCode == 0) {
//                ToastHelper.showTextToast(this,"支付成功");
                sendOrderedBroadcast(intent,//意图动作,指定action动作
                        null, //receiverPermission,接收这条广播具备什么权限
                        new FinalReceiver(orderID,specialEffectsInt),//resultReceiver,最终的广播接受者,广播一定会传给他
                        null, //scheduler,handler对象处理广播的分发
                        0,//initialCode,初始代码
                        "", //initialData,初始数据
                        null//initialExtras,额外的数据,如果觉得初始数据不够,可以通过bundle来指定其他数据
                );
            } else {
                ToastHelper.showTextToast(this,"支付失败");
            }
        } else {
        }


        finish();


    }


}