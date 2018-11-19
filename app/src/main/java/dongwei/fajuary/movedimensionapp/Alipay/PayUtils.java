package dongwei.fajuary.movedimensionapp.Alipay;

import android.app.Activity;

import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONObject;

/**
 * 支付宝/微信支付相关处理
 */
public class PayUtils {
    /**
     * Error:Execution failed for task ':app:transformClassesWithJarMergingForDebug'.
     > com.android.build.api.transform.TransformException: java.util.zip.ZipException: duplicate entry: com/tencent/a/a/a/a/a.class
     */
    // 商户PID
    // 商户收款账号

    private static final int SDK_PAY_FLAG = 1;
    public static int noti = 1;
    public Activity activity;
//    @SuppressLint("HandlerLeak")
//    private Handler mHandler = new Handler() {
//        @SuppressWarnings("unused")
//        public void handleMessage(Message msg) {
//            switch ( msg.what ) {
//                case SDK_PAY_FLAG: {//支付宝支付相关处理
//                    Logger.e(" msg.obj--->" + msg.obj);
//                    PayResult payResult = new PayResult((String) msg.obj);
//                    /**
//                     * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
//                     * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
//                     * docType=1) 建议商户依赖异步通知
//                     */
//                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
//
//                    String resultStatus = payResult.getResultStatus();
//                    Logger.e("resultStatus----》"+resultStatus);
//                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
//                    if ( TextUtils.equals(resultStatus, "9000") ) {
//                        SmallFeatureUtils.Toast("支付成功");
//                        if ( from != null ) {
//                            JSONObject jsonObject = PayUtils.from;
//                        }
//                        Intent intent = new Intent ();
//
//                        if (classType.equals("buyGoods")){//购买商品处理
//                            intent.setClass(activity, MyOrderActivity.class);
//
//                            intent.putExtra("appointType","order");
//
//                            activity.startActivity (intent);
//
//                        }else if (classType.equals("orderNo")){
//                            intent.setClass(activity, MyOrderActivity.class);
//
//                            intent.putExtra("appointType","order");
//
//                            activity.startActivity (intent);
//
//                        }else {
//                            intent.setClass (activity,MyWalletActivity.class);
//                            activity.startActivity (intent);
//                        }
//
//
//                    } else {
//                        // 判断resultStatus 为非"9000"则代表可能支付失败
//                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
//                        if ( TextUtils.equals(resultStatus, "8000") ) {
//                            SmallFeatureUtils.Toast("支付结果确认中");
//
//                        } else {
//                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
//                            SmallFeatureUtils.Toast("支付失败");
//
//                        }
//                    }
//                    break;
//                }
//                default:
//                    break;
//            }
//        }
//    };

    public static String orderID;
    public static int specialEffectsInt;

    public PayUtils(String orderid,int effectsInt) {
        orderID = orderid;
        specialEffectsInt=effectsInt;

    }


//    /**
//     * call alipay sdk pay. 调用SDK支付
//     * 支付宝相关参数
//     */
//    public void pay(final Activity activity, String sign) {
//        this.activity = activity;
//        /**
//         * 特别注意，这里的签名逻辑需要放在服务端，切勿将私钥泄露在代码中！
//         */
//
//        /**
//         * 完整的符合支付宝参数规范的订单信息
//         */
//        final String payInfo = sign;
//        Runnable payRunnable = new Runnable() {
//
//            @Override
//            public void run() {
//                // 构造PayTask 对象
//                PayTask alipay = new PayTask(activity);
//                // 调用支付接口，获取支付结果
//                String result = alipay.pay(payInfo, true);
//
//                Message msg = new Message();
//                msg.what = SDK_PAY_FLAG;
//                msg.obj = result;
//
//                mHandler.sendMessage(msg);
//            }
//        };
//
//        // 必须异步调用
//        Thread payThread = new Thread(payRunnable);
//        payThread.start();
//    }

    public static String payType;
    public static String downUrlStr;
    public void wxpay(Activity activity, JSONObject sig) {//微信支付相关参数
        IWXAPI api;
        String appidStr = sig.optString("appId");
        api = WXAPIFactory.createWXAPI(activity, appidStr, false);
        api.registerApp(appidStr);
        PayReq request = new PayReq();
        request.appId = appidStr;
        request.partnerId = sig.optString("partnerid");
        request.prepayId = sig.optString("prepay_id");
        request.packageValue = sig.optString("pack");
        request.nonceStr = sig.optString("nonceStr");
        request.timeStamp = sig.optString("timeStamp");
        request.sign = sig.optString("sign");
        api.sendReq(request);
    }

//    /**
//     * get the sdk version. 获取SDK版本号
//     */
//    public void getSDKVersion() {
//        PayTask payTask = new PayTask(activity);
//        String version = payTask.getVersion();
//        SmallFeatureUtils.Toast(version);
//    }

}
