package dongwei.fajuary.movedimensionapp.videoModel;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Transition;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.sdk.android.vod.upload.VODSVideoUploadCallback;
import com.alibaba.sdk.android.vod.upload.VODSVideoUploadClient;
import com.alibaba.sdk.android.vod.upload.VODSVideoUploadClientImpl;
import com.alibaba.sdk.android.vod.upload.model.SvideoInfo;
import com.alibaba.sdk.android.vod.upload.session.VodHttpClientConfig;
import com.alibaba.sdk.android.vod.upload.session.VodSessionCreateInfo;
import com.aliyun.vodplayer.media.AliyunLocalSource;
import com.aliyun.vodplayer.media.AliyunVodPlayer;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.orhanobut.logger.Logger;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import dongwei.fajuary.dongweimoveview.base.DefaultAdapter;
import dongwei.fajuary.dongweimoveview.utils.SmallFeatureUtils;
import dongwei.fajuary.dongweimoveview.utils.StatusBarUtil;
import dongwei.fajuary.dongweimoveview.utils.ToastHelper;
import dongwei.fajuary.movedimensionapp.Alipay.PayUtils;
import dongwei.fajuary.movedimensionapp.R;
import dongwei.fajuary.movedimensionapp.base.AppBaseActivity;
import dongwei.fajuary.movedimensionapp.base.ImageClickInterface;
import dongwei.fajuary.movedimensionapp.base.MainActivity;
import dongwei.fajuary.movedimensionapp.dialog.CustomDialog;
import dongwei.fajuary.movedimensionapp.httpModel.HttpUtils;
import dongwei.fajuary.movedimensionapp.httpModel.StringDialogCallback;
import dongwei.fajuary.movedimensionapp.loginModel.LoginActivity;
import dongwei.fajuary.movedimensionapp.util.AppManager;
import dongwei.fajuary.movedimensionapp.util.DialogUtils;
import dongwei.fajuary.movedimensionapp.videoModel.adapter.SpecialListAdapter;
import dongwei.fajuary.movedimensionapp.videoModel.bean.SpecialEffectsBean;
import dongwei.fajuary.movedimensionapp.videoModel.bean.SpecialEffectsData;
import dongwei.fajuary.movedimensionapp.videoModel.bean.SpecialEffectsInfo;

/**
 * 视频展示
 */
public class VideoGenerationActivity extends AppBaseActivity implements FinalReceiver.PersonInfoInterfce {
    public static String SDPATH = Environment.getExternalStorageDirectory()
            + "/dongweivideo/";

    private OrientationUtils orientationUtils;
    private boolean isTransition;
    private Transition transition;
    private String videoPath;
    private String videoImgPath;
    public final static String IMG_TRANSITION = "IMG_TRANSITION";
    public final static String TRANSITION = "TRANSITION";


    @BindView(R.id.activity_generatevideo_leftRelayout)
    RelativeLayout leftRelayout;

    @BindView(R.id.activity_generatevideo_rightRelayout)
    RelativeLayout rightRelayout;

    @BindView(R.id.activity_videogenerate_defaultBtmLin)
    LinearLayout defaultBtmLin;//底部优惠券
    @BindView(R.id.activity_videogenerate_recordPriceTxt)
    TextView recordPriceTxt;//录制价格
    @BindView(R.id.activity_videogenerate_recordNameTxt)
    TextView recordNameTxt;//录制名称
    @BindView(R.id.activity_videogenerate_generateVideoLin)
    LinearLayout generateVideoLin;//生成录制视频

    @BindView(R.id.activity_videogenerate_specialEffectsBtmLin)
    RelativeLayout specialEffectsBtmLin;//购买特效
    @BindView(R.id.activity_videogenerate_videoPriceTxt)
    TextView videoPriceTxt;//价格

    @BindView(R.id.activity_generatevideo_mWebView)
    WebView mWebView;//价格

    @BindView(R.id.activity_generatevideo_mSurfaceView)
    SurfaceView mSurfaceView;//视频展示
    private AliyunVodPlayer aliyunVodPlayer;


    /**
     * 特效列表
     */
    @BindView(R.id.activity_videogenerate_specialRecyclerView)
    RecyclerView specialRecyclerView;



    @BindView(R.id.activity_generatevideo_mProgressBar)
    ProgressBar mProgressBar;

    private CustomDialog customDialog;

    private SpecialListAdapter specialListAdapter;
    private LinearLayoutManager horelinManager;

    private List<SpecialEffectsInfo> specialList;


    private VODSVideoUploadClient vodsVideoUploadClient;

    @Override
    public int getLayoutId() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        StatusBarUtil.setImgTransparent(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow ().getDecorView ().setSystemUiVisibility (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        return R.layout.activity_video_generation;
    }

    @Override
    public void newInitView() {

        customDialog = new CustomDialog(this);

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

        AppManager.getAppManager().addShareAct(this);
        interfce=this;

        if (null != getIntent()) {
            videoPath = getIntent().getStringExtra("videoPath");
            Logger.e("videoPath", videoPath);
        }

        vodsVideoUploadClient = new VODSVideoUploadClientImpl(this.getApplicationContext());
        vodsVideoUploadClient.init();

        initVideo();

        specialListAdapter = new SpecialListAdapter(this);
        horelinManager = new LinearLayoutManager(this);
        horelinManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        specialRecyclerView.setLayoutManager(horelinManager);
        specialRecyclerView.setAdapter(specialListAdapter);

        initWebView();
    }

    private String webUrl;

    @Override
    public void initListener() {
        leftRelayout.setOnClickListener(this);
        rightRelayout.setOnClickListener(this);
        generateVideoLin.setOnClickListener(this);

        specialEffectsBtmLin.setOnClickListener(this);

        specialListAdapter.setOnItemClickListener(new DefaultAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int viewType, Object data, int position) {
                if (position == 0) {
                    defaultBtmLin.setVisibility(View.VISIBLE);
                    specialEffectsBtmLin.setVisibility(View.GONE);
                } else {
                    defaultBtmLin.setVisibility(View.GONE);
                    specialEffectsBtmLin.setVisibility(View.VISIBLE);
                }

                if (null != specialList) {
                    for (int i = 0; i < specialList.size(); i++) {
                        SpecialEffectsInfo effectsInfo = specialList.get(i);
                        effectsInfo.setSelected(false);
                        specialList.set(i, effectsInfo);
                    }

                    SpecialEffectsInfo specialEffectsInfo = specialList.get(position);

                    if (null != specialEffectsInfo) {
                        double moneyDl = specialEffectsInfo.getMoney();
                        buyspecialEffectDl = moneyDl;
                        videoPriceTxt.setText("¥" + moneyDl);
                        specialEffectsInt = specialEffectsInfo.getId();
                        specialEffectsInfo.setSelected(true);

                        int type=specialEffectsInfo.getType();
                        long addTime=specialEffectsInfo.getId();
                        webUrl="http://weixinlive.dongweinet.com/specialEffects/1.html?id="+type+"&time="+System.currentTimeMillis()/1000;

                        mWebView.loadUrl(webUrl);
                    }
                    specialList.set(position, specialEffectsInfo);
                    specialListAdapter.setmInfos(specialList);
                }
            }
        });
    }


    private double buyspecialEffectDl = 0.00;

    @Override
    public void processClick(View view) {
        switch (view.getId()) {
            case R.id.activity_generatevideo_leftRelayout:
                AppManager.getAppManager().finishActivity(this);
                break;
            case R.id.activity_generatevideo_rightRelayout:
                Intent intent = new Intent();
                intent.setClass(this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.activity_videogenerate_generateVideoLin://生成录制视频
                showPayTypeDialog();
                break;
            case R.id.activity_videogenerate_specialEffectsBtmLin://购买特效
                payTypeInt = 1;

                if (firstPaymoney<buyspecialEffectDl){
                    getSpecialEffectOrderUrl();
                }else {
                    showPayTypeDialog();
                }
                break;
        }
    }

    @Override
    public void initData() {

        getSpecialEffectsUrl();
    }

    private void initVideo() {

        //借用了jjdxm_ijkplayer的URL



        AliyunLocalSource.AliyunLocalSourceBuilder asb = new AliyunLocalSource.AliyunLocalSourceBuilder();
        asb.setSource(videoPath);
        //aliyunVodPlayer.setLocalSource(asb.build());
        AliyunLocalSource mLocalSource = asb.build();
        aliyunVodPlayer.prepareAsync(mLocalSource);

        //创建MediaMetadataRetriever对象
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        //绑定资源
        mmr.setDataSource(videoPath);
        //获取第一帧图像的bitmap对象
        Bitmap bitmap = mmr.getFrameAtTime();
        saveBitmapFile(bitmap);
//        videoImgPath
        //增加封面
        ImageView imageView = new ImageView(this);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageBitmap(bitmap);

        //过渡动画
    }

    private void saveBitmapFile(Bitmap bitmap) {
        try {
            File basefile = new File(SDPATH);
            if (!basefile.exists()) {
                basefile.mkdir();
            }
            String videoname = SmallFeatureUtils.getTimeYMDHMStr(String.valueOf(System.currentTimeMillis() / 1000), "yyyy-MM-dd HH:mm:ss");
            File file = new File(SDPATH, "dongwei_video" + videoname + ".png");
//            if (file.exists()) {
//                file.delete();
//            }
            FileOutputStream out = new FileOutputStream(file);
            videoImgPath = file.getAbsolutePath();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        vodsVideoUploadClient.pause();

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

    @Override
    protected void onResume() {
        super.onResume();
        vodsVideoUploadClient.resume();

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

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onDestroy() {
        super.onDestroy();
        vodsVideoUploadClient.release();

        if (orientationUtils != null)
            orientationUtils.releaseListener();


        if (mWebView != null) {
            mWebView.getSettings().setBuiltInZoomControls(true);
            mWebView.setVisibility(View.GONE);
        }
        isOnPause = false;
    }






    //特效列表
    private void getSpecialEffectsUrl() {
        String token = preSaveUtil.getString("token");
        Map<String, String> params = new HashMap<>();
        params.put("token", token);
        OkGo.<String>post(HttpUtils.getSpecialEffectsUrl)//
                .tag(this)//
                .cacheKey("getSpecialEffectsUrl")
                .params(params)        // 这里可以上传参数
                .execute(new StringDialogCallback(this) {
                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);

                        ToastHelper.showTextToast(VideoGenerationActivity.this, "检查网络，请重试...");

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
                                    SpecialEffectsBean specialEffectsBean = JSON.parseObject(jsonStr, SpecialEffectsBean.class);
                                    if (null != specialEffectsBean) {
                                        SpecialEffectsData effectsData = specialEffectsBean.getData();
                                        if (null != effectsData) {
                                            double balanceDl = effectsData.getBalance();
                                            recordPriceTxt.setText("¥" + balanceDl);
                                            recordNameTxt.setText("您有" + balanceDl + "元录制卷");
                                            firstPaymoney=balanceDl;
                                            specialList = effectsData.getList();
                                            if (specialList.size() > 0) {
                                                SpecialEffectsInfo effectsInfo = specialList.get(0);
                                                effectsInfo.setSelected(true);
                                                specialList.set(0, effectsInfo);
                                                specialEffectsInt = effectsInfo.getId();
                                                double moneyDl = effectsInfo.getMoney();
                                                buyspecialEffectDl = moneyDl;
                                                int type=effectsInfo.getType();
                                                long addTime=effectsInfo.getId();
                                                webUrl="http://weixinlive.dongweinet.com/specialEffects/1.html?id="+type+"&time="+System.currentTimeMillis()/1000;

                                                mWebView.loadUrl(webUrl);

                                            }
                                            specialListAdapter.setmInfos(specialList);
                                        }
                                    }
                                } else if (status.equals("301")) {
                                    ToastHelper.showTextToast(VideoGenerationActivity.this, "请重新登录");
                                    Intent intent = new Intent();
                                    intent.setClass(VideoGenerationActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                } else {
                                    ToastHelper.showTextToast(VideoGenerationActivity.this, msgStr);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }


    //选择支付方式
    private void showPayTypeDialog() {
        Dialog selectPayTypeDialog = DialogUtils.selectPayTypeDialog(this);
        initPayTypeView(selectPayTypeDialog);
        selectPayTypeDialog.show();
    }

    private TextView promptlypayTxt;//立即支付

    private LinearLayout selectRecordpayLin;//选择录制券
    private ImageView selectRecordpayImg;//选择录制券支付

    private LinearLayout selectWxpayLin;//选择微信
    private ImageView selectWxpayImg;//选择微信支付

    private TextView buypriceTxt;//购买价格
    private TextView cancleTxt;//取消

    private int payTypeInt = 2;
    private int specialEffectsInt = -1;

    private TextView firstpayTxt;

    private double firstPaymoney=0.00;
    private void initPayTypeView(final Dialog dialog) {

        cancleTxt = (TextView) dialog.findViewById(R.id.dialog_selectpaytype_cancleTxt);
        buypriceTxt = (TextView) dialog.findViewById(R.id.dialog_selectpaytype_buypriceTxt);

        firstpayTxt = (TextView) dialog.findViewById(R.id.dialog_selectpaytype_firstpayTxt);


        firstpayTxt.setText("使用"+firstPaymoney+"元录制券");
        selectRecordpayLin = (LinearLayout) dialog.findViewById(R.id.dialog_selectpaytype_selectRecordpayLin);
        selectRecordpayImg = (ImageView) dialog.findViewById(R.id.dialog_selectpaytype_selectRecordpayImg);

        selectWxpayLin = (LinearLayout) dialog.findViewById(R.id.dialog_selectpaytype_selectWxpayLin);
        selectWxpayImg = (ImageView) dialog.findViewById(R.id.dialog_selectpaytype_selectWxpayImg);

        promptlypayTxt = (TextView) dialog.findViewById(R.id.dialog_selectpaytype_promptlypayTxt);

        buypriceTxt.setText("¥" + buyspecialEffectDl);
        selectRecordpayImg.setSelected(true);
        //选择录制
        selectRecordpayLin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectRecordpayImg.setSelected(true);
                selectWxpayImg.setSelected(false);
                payTypeInt = 2;

            }
        });
        //选择微信
        selectWxpayLin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectRecordpayImg.setSelected(false);
                selectWxpayImg.setSelected(true);
                payTypeInt = 1;
            }
        });
        //立即支付
        promptlypayTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (firstPaymoney<buyspecialEffectDl){
                    payTypeInt = 1;
//                    ToastHelper.showTextToast(VideoGenerationActivity.this,"请选择其他支付方式");
//                    return;
                }
                getSpecialEffectOrderUrl();
                if (null!=dialog){
                    dialog.dismiss();
                }
            }
        });
        //取消
        cancleTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != dialog && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });
    }

    //特效列表
    private void getSpecialEffectOrderUrl() {
        String token = preSaveUtil.getString("token");
        Map<String, String> params = new HashMap<>();
        params.put("token", token);
        OkGo.<String>post(HttpUtils.getSpecialEffectOrderUrl)//
                .tag(this)//
                .cacheKey("getSpecialEffectOrderUrl")
                .params(params)        // 这里可以上传参数
                .params("seId", specialEffectsInt)
                .params("type", payTypeInt)
                .execute(new StringDialogCallback(this) {
                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastHelper.showTextToast(VideoGenerationActivity.this, "检查网络，请重试...");
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

                                         orderId = dataJson.optString("orderId");
                                        if (payTypeInt == 1) {
                                            PayUtils weixinPay = new PayUtils(orderId,specialEffectsInt);
                                            weixinPay.wxpay(VideoGenerationActivity.this, dataJson);
                                        } else {
                                            getAssumeRoleUrl();
                                        }


                                    }
                                } else if (status.equals("301")) {
                                    ToastHelper.showTextToast(VideoGenerationActivity.this, "请重新登录");
                                    Intent intent = new Intent();
                                    intent.setClass(VideoGenerationActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                } else {
                                    ToastHelper.showTextToast(VideoGenerationActivity.this, msgStr);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    //    获取阿里云临时的凭证
    private void getAssumeRoleUrl() {
//        customDialog.setLoadTitle("视频生成中...");

//        customDialog.show();
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
                        ToastHelper.showTextToast(VideoGenerationActivity.this, "检查网络，请重试...");
//                        customDialog.dismiss();
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
                                        uploadAlishortVideo(accessKeyId, accessKeySecret, securityToken);
                                    }
                                } else if (status.equals("301")) {
                                    ToastHelper.showTextToast(VideoGenerationActivity.this, "请重新登录");
                                    Intent intent = new Intent();
                                    intent.setClass(VideoGenerationActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                } else {
                                    ToastHelper.showTextToast(VideoGenerationActivity.this, msgStr);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }


    private void uploadAlishortVideo(final String accessKeyId, final String accessKeySecret, final String securityToken) {
        final long expriedTime = 15 * 1000;
        //参数请确保存在，如不存在SDK内部将会直接将错误throw Exception
// 文件路径保证存在之外因为Android 6.0之后需要动态获取权限，请开发者自行实现获取"文件读写权限".
        VodHttpClientConfig vodHttpClientConfig = new VodHttpClientConfig.Builder()
                .setMaxRetryCount(2)//重试次数
                .setConnectionTimeout(15 * 1000)//连接超时
                .setSocketTimeout(15 * 1000)//socket超时
                .build();
        //构建短视频VideoInfo,常见的描述，标题，详情都可以设置
        SvideoInfo svideoInfo = new SvideoInfo();
        svideoInfo.setTitle(new File(videoPath).getName());
        svideoInfo.setDesc("");
        svideoInfo.setCateId(1);
        //构建点播上传参数(重要)
        mProgressBar.setVisibility(View.VISIBLE);
        VodSessionCreateInfo vodSessionCreateInfo = new VodSessionCreateInfo.Builder()
                .setImagePath(videoImgPath)//图片地址
                .setVideoPath(videoPath)//视频地址
                .setAccessKeyId(accessKeyId)//临时accessKeyId
                .setAccessKeySecret(accessKeySecret)//临时accessKeySecret
                .setSecurityToken(securityToken)//securityToken
                .setExpriedTime(String.valueOf(expriedTime))//STStoken过期时间
//                .setRequestID(requestID)//requestID，开发者可以传将获取STS返回的requestID设置也可以不设.
                .setIsTranscode(true)//是否转码.如开启转码请AppSever务必监听服务端转码成功的通知
                .setSvideoInfo(svideoInfo)//短视频视频信息
                .setVodHttpClientConfig(vodHttpClientConfig)//网络参数
                .build();
        vodsVideoUploadClient.uploadWithVideoAndImg(vodSessionCreateInfo, new VODSVideoUploadCallback() {
            @Override
            public void onUploadSucceed(String videoId, String imageUrl) {
                //上传成功返回视频ID和图片URL.
                Logger.e("onUploadSucceed" + "videoId:" + videoId + "imageUrl" + imageUrl);

                addVideoUrl(videoId,imageUrl);
            }

            @Override
            public void onUploadFailed(String code, String message) {
                //上传失败返回错误码和message.错误码有详细的错误信息请开发者仔细阅读
                Logger.e("onUploadFailed" + "code" + code + "message" + message);
                //阿里视频上传失败
                mProgressBar.setVisibility(View.GONE);

                uploadDefaultUrl();
            }

            @Override
            public void onUploadProgress(long uploadedSize, long totalSize) {
                //上传的进度回调,非UI线程
                Logger.e("onUploadProgress" + uploadedSize * 100 / totalSize);
                progress = (int) (uploadedSize * 100 / totalSize);
                handler.sendEmptyMessage(0);
            }

            @Override
            public void onSTSTokenExpried() {
                Logger.e("onSTSTokenExpried");
                //STS token过期之后刷新STStoken，如正在上传将会断点续传
                vodsVideoUploadClient.refreshSTSToken(accessKeyId, accessKeySecret, securityToken, String.valueOf(expriedTime));


            }

            @Override
            public void onUploadRetry(String code, String message) {
                //上传重试的提醒
                Logger.e("onUploadRetry" + "code" + code + "message" + message);

            }

            @Override
            public void onUploadRetryResume() {
                //上传重试成功的回调.告知用户重试成功

                Logger.e("onUploadRetryResume");
            }
        });
    }

    private int progress;
    // UI只允许在主线程更新。
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            //更新进度
            if (progress == 100) {
                mProgressBar.setVisibility(View.GONE);
            } else {
                mProgressBar.setVisibility(View.VISIBLE);

            }
            mProgressBar.setProgress(progress);

        }
    };


    private String orderId;
    private void uploadDefaultUrl(){

        String token = preSaveUtil.getString("token");
        Map<String, String> params = new HashMap<>();
        params.put("token", token);
        params.put("orderId", orderId);
        OkGo.<String>post(HttpUtils.getUploadVideoUrl)//
                .tag(this)//
                .cacheKey("getUploadVideoUrl")
                .params(params)        // 这里可以上传参数
                .execute(new StringDialogCallback(this) {
                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastHelper.showTextToast(VideoGenerationActivity.this, "检查网络，请重试...");
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
                                    ToastHelper.showTextToast(VideoGenerationActivity.this, msgStr);

                                } else if (status.equals("301")) {
                                    ToastHelper.showTextToast(VideoGenerationActivity.this, "请重新登录");
                                    Intent intent = new Intent();
                                    intent.setClass(VideoGenerationActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                } else {
                                    ToastHelper.showTextToast(VideoGenerationActivity.this, msgStr);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void addVideoUrl(final String videoId, final String videoImgPath){
        String token = preSaveUtil.getString("token");
        Map<String, String> params = new HashMap<>();
        params.put("token", token);
        params.put("videoId", videoId);
        params.put("orderId", orderId);//imgUrl
        params.put("imgeUrl", videoImgPath);//imgUrl
        OkGo.<String>post(HttpUtils.getAddVideoUrl)//
                .tag(this)//
                .cacheKey("getAddVideoUrl")
                .params("specialId",specialEffectsInt)
                .params(params)        // 这里可以上传参数
                .execute(new StringDialogCallback(this) {
                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastHelper.showTextToast(VideoGenerationActivity.this, "检查网络，请重试...");
                    }

                    @Override
                    public void onSuccess(com.lzy.okgo.model.Response<String> response) {

                        String jsonStr = response.body();
                        Logger.json(jsonStr);

                        Intent intent=new Intent();
                        try {
                            JSONObject jsonObject = new JSONObject(jsonStr);
                            if (null != jsonObject) {
                                String status = jsonObject.optString("status");
                                String msgStr = jsonObject.optString("msg");

                                ToastHelper.showTextToast(VideoGenerationActivity.this, msgStr);

                                if (status.equals("200")) {
                                    int videoId=jsonObject.optInt("data");
                                    intent.setClass(VideoGenerationActivity.this,SharingStyleActivity.class);
                                    intent.putExtra("videoImgPath",videoImgPath);
                                    intent.putExtra("videoId",videoId);
                                    startActivity(intent);
                                } else if (status.equals("301")) {
                                    ToastHelper.showTextToast(VideoGenerationActivity.this, "请重新登录");
                                    intent.setClass(VideoGenerationActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                } else {
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
    public static FinalReceiver.PersonInfoInterfce interfce;

    private int getassumeInt=0;
    /**
     * 支付成功
     */
    @Override
    public void perSonInfo(String orderID,int specialEffectsInt) {
        orderId=orderID;
        this.specialEffectsInt=specialEffectsInt;
        if (getassumeInt==0){
            getAssumeRoleUrl();
            getassumeInt++;
        }

    }


    private void initWebView() {
        mWebView.setBackgroundColor(0);

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
    protected void onStop() {
        super.onStop();
        if (aliyunVodPlayer != null) {
            aliyunVodPlayer.stop();
        }
    }
}
