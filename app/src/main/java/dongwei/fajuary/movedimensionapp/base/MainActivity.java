package dongwei.fajuary.movedimensionapp.base;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.hardware.Camera;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.aliyun.demo.recorder.AliyunVideoRecorder;
import com.aliyun.struct.common.VideoQuality;
import com.aliyun.struct.recorder.CameraType;
import com.aliyun.struct.recorder.FlashType;
import com.aliyun.struct.snap.AliyunSnapVideoParam;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.orhanobut.logger.Logger;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import dongwei.fajuary.dongweimoveview.utils.InstallUtils;
import dongwei.fajuary.dongweimoveview.utils.SmallFeatureUtils;
import dongwei.fajuary.dongweimoveview.utils.StatusBarUtil;
import dongwei.fajuary.dongweimoveview.utils.ToastHelper;
import dongwei.fajuary.movedimensionapp.R;
import dongwei.fajuary.movedimensionapp.apkupdate.DownLoadUtils;
import dongwei.fajuary.movedimensionapp.appview.CircleImageTransformation;
import dongwei.fajuary.movedimensionapp.base.beanModel.UserBasicsBean;
import dongwei.fajuary.movedimensionapp.base.beanModel.UserBasicsData;
import dongwei.fajuary.movedimensionapp.base.beanModel.UserRuleData;
import dongwei.fajuary.movedimensionapp.base.beanModel.UserRuleInfoBean;
import dongwei.fajuary.movedimensionapp.dialog.DetectionDialog;
import dongwei.fajuary.movedimensionapp.httpModel.HttpUtils;
import dongwei.fajuary.movedimensionapp.httpModel.StringDialogCallback;
import dongwei.fajuary.movedimensionapp.loginModel.LoginActivity;
import dongwei.fajuary.movedimensionapp.personInfoModel.BindePhonenumActivity;
import dongwei.fajuary.movedimensionapp.personInfoModel.FeedBackActivity;
import dongwei.fajuary.movedimensionapp.personInfoModel.MyVideolistActivity;
import dongwei.fajuary.movedimensionapp.personInfoModel.PersonalDataActivity;
import dongwei.fajuary.movedimensionapp.util.AppManager;
import dongwei.fajuary.movedimensionapp.util.CameraUtil;
import dongwei.fajuary.movedimensionapp.videoModel.VideoGenerationActivity;
import dongwei.fajuary.movedimensionapp.videoModel.VideoShooteActivity;
import dongwei.fajuary.movedimensionapp.webpageModel.NewyearCourseActivity;
import dongwei.fajuary.movedimensionapp.webpageModel.NewyearStatementActivity;
import dongwei.fajuary.movedimensionapp.webpageModel.PrizesRuleActivity;

public class MainActivity extends AppBaseActivity implements SurfaceHolder.Callback {

    /**
     * 头部
     */
    @BindView(R.id.layout_menucontent_personAvatarImg)
    ImageView personAvatarImg;//头像



    @BindView(R.id.layout_menucontent_nicknameTxt)
    TextView nicknameTxt;//昵称
    @BindView(R.id.layout_menucontent_balanceNumTxt)
    TextView balanceNumTxt;//余额


    @BindView(R.id.layout_menucontent_versionInfoTxt)
    TextView versionInfoTxt;//版本信息



    @BindView(R.id.layout_main_newyearsCourseLin)
    LinearLayout newyearsCourseLin;//拜年教程
    @BindView(R.id.layout_main_rankeListLin)
    LinearLayout rankeListLin;//排行榜
    @BindView(R.id.layout_main_newyearsVideoLin)
    LinearLayout newyearsVideoLin;//短视频

    @BindView(R.id.layout_main_leftMenuLin)
    LinearLayout leftMenuLin;//左边菜单

    @BindView(R.id.layout_menucontent_myvideoLin)
    LinearLayout myvideoLin;//我的视频
    @BindView(R.id.layout_menucontent_dynamicDimensionLin)
    LinearLayout dynamicDimensionLin;//关于动维

    @BindView(R.id.layout_menucontent_visityeardeclareLin)
    LinearLayout visityeardeclareLin;//拜年了声明
    @BindView(R.id.layout_menucontent_bindephonenumLin)
    LinearLayout bindephonenumLin;//绑定手机号

    @BindView(R.id.layout_menucontent_prizesruleLin)
    LinearLayout prizesruleLin;//奖品规则
    @BindView(R.id.layout_menucontent_updatedVersionLin)
    LinearLayout updatedVersionLin;//版本更新

    @BindView(R.id.layout_menucontent_feedbackLin)
    LinearLayout feedbackLin;//意见反馈

    @BindView(R.id.layout_menucontent_appexitLin)
    LinearLayout appexitLin;//退出登录

    @BindView(R.id.layout_menucontent_firstRuleNameTxt)
    TextView firstRuleNameTxt;//第一个规则
    @BindView(R.id.layout_menucontent_secondRuleNameTxt)
    TextView secondRuleNameTxt;//第二个规则
    @BindView(R.id.layout_menucontent_thirdRuleNameTxt)
    TextView thirdRuleNameTxt;//第三个规则



    @BindView(R.id.activity_main_slideLayout)
    DrawerLayout mSlideLayout;
    @BindView(R.id.layout_main_mSurfaceView)
    SurfaceView mSurfaceView;//预览摄像头

    @BindView(R.id.layout_main_recordIconImg)
    ImageView recordIconImg;//跳到录制界面
    /**
     * 调用系统相机
     */
    private Camera mCamera;//摄像头
    private SurfaceHolder holder;
    private int cameraPosition = 1;//0代表前置摄像头，1代表后置摄像头


    private  AliyunSnapVideoParam snapVideoRecordParam;
    private final int REQUEST_RECORD=1003;



    private DetectionDialog.Builder dialogBuilder;
    private String token;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void newInitView() {
        token=preSaveUtil.getString("token");


        dialogBuilder=new DetectionDialog.Builder(this);
        StatusBarUtil.setTranslucentForDrawerLayoutDiff(this,mSlideLayout);
        if (Build.VERSION.SDK_INT >= 23) {
            permissionCheck();
        }
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //设置成白色的背景，字体颜色为黑色。
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        holder = mSurfaceView.getHolder();//获得句柄
        holder.addCallback(this);//添加回调

        snapVideoRecordParam = new AliyunSnapVideoParam.Builder()
                //设置录制分辨率，目前支持360p，480p，540p，720p
                .setResulutionMode(AliyunSnapVideoParam.RESOLUTION_720P)
                //设置视频比例，目前支持1:1,3:4,9:16
                .setRatioMode(AliyunSnapVideoParam.RATIO_MODE_3_4)
                .setRecordMode(AliyunSnapVideoParam.RECORD_MODE_AUTO) //设置录制模式，目前支持按录，点录和混合模式
//                .setRecordMode(2) //设置录制模式，目前支持按录，点录和混合模式
//                .setFilterList(eff_dirs) //设置滤镜地址列表,具体滤镜接口接收的是一个滤镜数组
                .setBeautyLevel(80) //设置美颜度
                .setBeautyStatus(true) //设置美颜开关
                .setCameraType(CameraType.FRONT) //设置前后置摄像头
                .setFlashType(FlashType.ON) // 设置闪光灯模式
                .setNeedClip(true) //设置是否需要支持片段录制
                .setMaxDuration(15000) //设置最大录制时长 单位毫秒
                .setMinDuration(2000) //设置最小录制时长 单位毫秒
                .setVideQuality(VideoQuality.HD) //设置视频质量
                .setGop(10) //设置关键帧间隔
//                .setVideoBitrate(2000) //设置视频码率，如果不设置则使用视频质量videoQulity参数计算出码率
                .setSortMode(AliyunSnapVideoParam.SORT_MODE_VIDEO)//设置导入相册过滤选择视频
//                .setFrameRate()
                .build();
    }


    private static final int PERMISSION_REQUEST_CODE = 1;
    private static final String[] permissionManifest = {
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO
    };
    private void permissionCheck() {
        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        for (String permission : permissionManifest) {
            if (PermissionChecker.checkSelfPermission(this, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                permissionCheck = PackageManager.PERMISSION_DENIED;
            }
        }
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, permissionManifest, PERMISSION_REQUEST_CODE);
        }

    }

    @Override
    public void initListener() {

        dialogBuilder.setCancleMsg("取消", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogBuilder.dismissDialog();
            }
        });

        dialogBuilder.setConfimMsg("确定", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //退出
                Intent intent=new Intent();
                intent.setClass(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        mSlideLayout.setDrawerShadow(R.mipmap.menu_icon, GravityCompat.START);
        leftMenuLin.setOnClickListener(this);
        recordIconImg.setOnClickListener(this);
        dynamicDimensionLin.setOnClickListener(this);
        newyearsCourseLin.setOnClickListener(this);
        visityeardeclareLin.setOnClickListener(this);
        prizesruleLin.setOnClickListener(this);
        rankeListLin.setOnClickListener(this);
        newyearsVideoLin.setOnClickListener(this);
        feedbackLin.setOnClickListener(this);
        bindephonenumLin.setOnClickListener(this);
        myvideoLin.setOnClickListener(this);

        personAvatarImg.setOnClickListener(this);
        appexitLin.setOnClickListener(this);
        updatedVersionLin.setOnClickListener(this);

        mSlideLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                //滑动过程中不断回调 slideOffset:0~1
                View content = mSlideLayout.getChildAt(0);
                View menu = drawerView;
                float scale = 1 - slideOffset;//1~0
                content.setTranslationX(menu.getMeasuredWidth() * (1 - scale));//0~width
            }
            @Override
            public void onDrawerOpened(View drawerView) {
            }
            @Override
            public void onDrawerClosed(View drawerView) {
            }
            @Override
            public void onDrawerStateChanged(int newState) {
            }
        });

    }

    @Override
    public void processClick(View view) {
        Intent intent=new Intent();
        switch (view.getId()){
            case R.id.layout_main_leftMenuLin://左边
                mSlideLayout.openDrawer(Gravity.LEFT);

                break;

            case R.id.layout_menucontent_personAvatarImg://个人资料
                if (TextUtils.isEmpty(token)){
                    dialogBuilder.showDialog();
                }else {
                    intent.setClass(this, PersonalDataActivity.class);
                    startActivity(intent);
                    releaseCamera();

                }


                break;
            case R.id.layout_menucontent_myvideoLin://我的视频
                if (TextUtils.isEmpty(token)){
                    dialogBuilder.showDialog();
                }else {
                    intent.setClass(this, MyVideolistActivity.class);
                    startActivity(intent);
                    releaseCamera();
                }


                break;
            case R.id.layout_menucontent_dynamicDimensionLin://关于动维
                intent.putExtra("webUrl",thirdWebUrl);
                intent.putExtra("webtitleName",thirdTitlename);
                intent.setClass(this, PrizesRuleActivity.class);
                startActivity(intent);
                releaseCamera();

                break;
            case R.id.layout_main_newyearsCourseLin://拜年教程
                intent.setClass(this, NewyearCourseActivity.class);
                startActivity(intent);
                releaseCamera();

                break;
            case R.id.layout_menucontent_visityeardeclareLin://拜年了声明
                intent.putExtra("webUrl",secondWebUrl);
                intent.putExtra("webtitleName",secondTitlename);
                intent.setClass(this, PrizesRuleActivity.class);
                startActivity(intent);
                releaseCamera();

                break;
            case R.id.layout_menucontent_bindephonenumLin://绑定手机号

                if (TextUtils.isEmpty(token)){
                    dialogBuilder.showDialog();
                }else {
                    intent.setClass(this, BindePhonenumActivity.class);
                    intent.putExtra("bindphone",bindphone);
                    startActivity(intent);
                    releaseCamera();

                }
                break;
            case R.id.layout_menucontent_prizesruleLin://奖品规则
                intent.putExtra("webUrl",firstWebUrl);
                intent.putExtra("webtitleName",firstTitlename);
                intent.setClass(this, PrizesRuleActivity.class);
                startActivity(intent);
                releaseCamera();

                break;
            case R.id.layout_menucontent_updatedVersionLin://版本更新
                if (!isUpdate) {
                   ToastHelper.showTextToast(this,"当前已是最新版本");
                }
                getVersionUpdateUrl();
                break;
            case R.id.layout_main_recordIconImg://视频录制
                if (TextUtils.isEmpty(token)){
                    dialogBuilder.showDialog();
                }else {
                    // TODO 视频待优化
                    AliyunVideoRecorder.startRecordForResult(this,REQUEST_RECORD,snapVideoRecordParam);
                    releaseCamera();
                }

                break;
            case R.id.layout_main_rankeListLin://排行榜

                if (TextUtils.isEmpty(token)){
                    dialogBuilder.showDialog();
                }else {
                    intent.setClass(this, NewyearStatementActivity.class);
                    startActivity(intent);
                    releaseCamera();
                }
                break;
            case R.id.layout_main_newyearsVideoLin://拜年视频

                if (TextUtils.isEmpty(token)){
                    dialogBuilder.showDialog();
                }else {
                    intent.setClass(this, VideoShooteActivity.class);
                    startActivity(intent);
                    releaseCamera();
                }
                break;
            case R.id.layout_menucontent_feedbackLin://意见反馈
                if (TextUtils.isEmpty(token)){
                    dialogBuilder.showDialog();
                }else {
                    intent.setClass(this, FeedBackActivity.class);
                    startActivity(intent);
                    releaseCamera();
                }
                break;
            case R.id.layout_menucontent_appexitLin:
                intent.setClass(this, LoginActivity.class);
                startActivity(intent);
                AppManager.getAppManager().AppExit(this);
                break;

        }

    }

    /**
     * 释放照相机
     */
    private void releaseCamera(){
        mCamera.stopPreview();
        mCamera.release();
        mCamera = null;
    }

    private String versionCode;

    @Override
    public void initData() {
        //版本信息
        PackageManager manager = this.getPackageManager ();
        PackageInfo info = null;
        try {
            info = manager.getPackageInfo (this.getPackageName (), 0);
            versionCode = info.versionName;
            versionInfoTxt.setText ("版本号：" + versionCode);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace ();
        }


        getUserBasicsInfoUrl();

        userRuleInfoUrl();

        getVersionUpdateUrl();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        mCamera.startPreview();
    }

    //创建
    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        // TODO Auto-generated method stub
        //当surfaceview创建时开启相机
        if(mCamera == null) {
            try {
                mCamera = Camera.open(Camera.CameraInfo.CAMERA_FACING_FRONT);
                mCamera.setPreviewDisplay(holder);//通过surfaceview显示取景画面
                int screenWidth= SmallFeatureUtils.getWindowWith(this);
                int screenHeight=SmallFeatureUtils.getWindowHeight(this);
                //50+300+480
                Camera.Parameters parameters = mCamera.getParameters();
                Camera.Size preSize = CameraUtil.getCloselyPreSize(true, screenWidth-240,
                        900,
                        parameters.getSupportedPreviewSizes());
                parameters.setPreviewSize(preSize.width, preSize.height);
                mCamera.setParameters(parameters);

                if (this.getResources().getConfiguration().orientation
                        != Configuration.ORIENTATION_LANDSCAPE) {
                    parameters.set("orientation", "portrait");
                    mCamera.setDisplayOrientation(90);
                    parameters.setRotation(90);
                } else {
                    parameters.set("orientation", "landscape");
                    mCamera.setDisplayOrientation(0);
                    parameters.setRotation(0);
                }
                mCamera.setParameters(parameters);
                mCamera.startPreview();//开始预览
                mCamera.cancelAutoFocus();// 2如果要实现连续的自动对焦，这一句必须加上
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

        if (null!=mCamera){
            mCamera.startPreview();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        // TODO Auto-generated method stub
        //当surfaceview关闭时，关闭预览并释放资源
        if (null!=mCamera){
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
            holder = null;
            mSurfaceView = null;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getUserBasicsInfoUrl();
        if (requestCode==REQUEST_RECORD){
            if(resultCode == Activity.RESULT_OK && data!= null){
                int type = data.getIntExtra(AliyunVideoRecorder.RESULT_TYPE,0);
                if(type ==  AliyunVideoRecorder.RESULT_TYPE_RECORD){//视频录制
                    String videoPath=data.getStringExtra(AliyunVideoRecorder.OUTPUT_PATH);//视频文件路径
                    Intent intent=new Intent();
                    intent.setClass(MainActivity.this, VideoGenerationActivity.class);
                    intent.putExtra("videoPath",videoPath);
                    startActivity(intent);
                }
            }else if(resultCode == Activity.RESULT_CANCELED){
                ToastHelper.showTextToast(this,"取消录制");
            }
        }
    }

    private void getUserBasicsInfoUrl() {
        String token=preSaveUtil.getString("token");
        Map<String, String> params = new HashMap<>();
        params.put("token", token);
        OkGo.<String>post(HttpUtils.userBasicsInfoUrl)//
                .tag(this)//
                .cacheKey("userBasicsInfoUrl")
                .params(params)        // 这里可以上传参数
                .execute(new StringDialogCallback(this) {
                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastHelper.showTextToast(MainActivity.this,"请求失败，请重试...");
                        nicknameTxt.setText("登录");
                        balanceNumTxt.setText("录制券 0.00元");
                    }

                    @Override
                    public void onSuccess(com.lzy.okgo.model.Response<String> response) {

                        //301 未登录  400 错误
                        String jsonStr = response.body();
                        Logger.json(jsonStr);

                        Intent intent=new Intent();
                        UserBasicsBean basicsBean = JSON.parseObject(jsonStr, UserBasicsBean.class);
                        if (null!=basicsBean){
                            int statusInt=basicsBean.getStatus();
                            String msgStr=basicsBean.getMsg();
                            UserBasicsData basicsData=basicsBean.getData();
                            if (statusInt==200){
                                setPersonInfo(basicsData);
                            }
                            else if (statusInt==301){
                                nicknameTxt.setText("登录");
                                balanceNumTxt.setText("录制券 0.00元");
//                                ToastHelper.showTextToast(MainActivity.this,"请重新登录！");
//                                intent.setClass(MainActivity.this,LoginActivity.class);
//                                startActivity(intent);
                            }else {
                                ToastHelper.showTextToast(MainActivity.this,msgStr);
                            }
                        }
                    }
                });
    }

    private String bindphone;
    private void setPersonInfo(UserBasicsData userBasicsData){
        String iconUrl=userBasicsData.getIcon();//头像
        String nickname=userBasicsData.getNickname();//昵称
        String balance=userBasicsData.getBalance();//余额
        bindphone=userBasicsData.getPhone();//手机号

        preSaveUtil.setString("nickname",nickname);
        if (TextUtils.isEmpty(iconUrl)){
            iconUrl="no";
        }
        if (TextUtils.isEmpty(nickname)){
            nickname="匿名";
        }
        if (TextUtils.isEmpty(balance)){
            balance="0.00";
        }
        nicknameTxt.setText(nickname);
        balanceNumTxt.setText("录制券 "+balance+"元");
//        personAvatarImg
        Picasso
                .with(MovedimensionApp.getInstance().getApplicationContext())
                .load(iconUrl)
                .error(R.drawable.default_personimg)
                .placeholder(R.drawable.default_personimg)
                .transform(new CircleImageTransformation())
                .into(personAvatarImg);
//        PictureLoadUtil.loadDefaultImg(personAvatarImg,iconUrl,R.drawable.default_personimg);
    }

    private String firstWebUrl;
    private String secondWebUrl;
    private String thirdWebUrl;

    private String firstTitlename;
    private String secondTitlename;
    private String thirdTitlename;
    private void userRuleInfoUrl() {
        Map<String, String> params = new HashMap<>();
        OkGo.<String>post(HttpUtils.userRuleUrl)//
                .tag(this)//
                .cacheKey("userRuleInfoUrl")
                .params(params)        // 这里可以上传参数
                .execute(new StringDialogCallback(this) {
                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastHelper.showTextToast(MainActivity.this,"请求失败，请重试...");
                    }

                    @Override
                    public void onSuccess(com.lzy.okgo.model.Response<String> response) {

                        //301 未登录  400 错误
                        String jsonStr = response.body();
                        Logger.json(jsonStr);

                        UserRuleInfoBean userRuleInfoBean = JSON.parseObject(jsonStr, UserRuleInfoBean.class);
                        if (null!=userRuleInfoBean){
                            List<UserRuleData> dataList=userRuleInfoBean.getData();
                            if (null!=dataList){
                                UserRuleData userRuleData1=dataList.get(0);
                                UserRuleData userRuleData2=dataList.get(1);
                                UserRuleData userRuleData3=dataList.get(2);

                                firstWebUrl=userRuleData1.getUrl();
                                secondWebUrl=userRuleData2.getUrl();
                                thirdWebUrl=userRuleData3.getUrl();

                                String firstName=userRuleData1.getName();
                                String secondName=userRuleData2.getName();
                                String thirdName=userRuleData3.getName();

                                firstTitlename=firstName;
                                secondTitlename=secondName;
                                thirdTitlename=thirdName;

                                firstRuleNameTxt.setText(firstName);
                                secondRuleNameTxt.setText(secondName);
                                thirdRuleNameTxt.setText(thirdName);

                            }
                        }
                    }
                });
    }



    //版本更新
    private void getVersionUpdateUrl() {


        Map<String, String> params = new HashMap<>();
        OkGo.<String>post(HttpUtils.getVersionUpdateUrl)//
                .tag(this)//
                .cacheKey("getProbjectTypeListUrl")
                .cacheMode(CacheMode.NO_CACHE)
                .params(params)        // 这里可以上传参数
                .execute(new StringCallback() {
                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);

                        Logger.e(response.body());
                        Logger.e("网络什么情况");
                        ToastHelper.showTextToast(MainActivity.this,"网络不给力，加载失败");
                    }

                    @Override
                    public void onSuccess(com.lzy.okgo.model.Response<String> response) {

                        String jsonStr = response.body();
                        Logger.json(jsonStr);

                        try {
                            JSONObject jsonObject = new JSONObject(jsonStr);
                            String status = jsonObject.optString("status");
                            if (status.equals("200")) {
                                JSONObject dataJson = jsonObject.optJSONObject("data");
                                if (null != dataJson) {
                                    String apkupdatePath = dataJson.optString("path");
                                    String versionStr = dataJson.optString("version_number");
                                    String describe = dataJson.optString("versions");

                                    getUpdateVersion(versionStr, apkupdatePath, describe);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
    }

    /**
     * 版本更新提示框
     */
    public void hintUpDateDialog(final String versionNum, final String versionInfo, final String appPathload) {
        final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(this).create();

        alertDialog.setCancelable(false);
        alertDialog.show();

        Window window = alertDialog.getWindow();
        window.setContentView(R.layout.update_apk_versionlayout);

        TextView versionNumTxt = (TextView) window.findViewById(R.id.update_apkversion_versionNumTxt);
        TextView versionDescripTxt = (TextView) window.findViewById(R.id.update_apkversion_versionDescripTxt);

        TextView cancleTxt = (TextView) window.findViewById(R.id.update_apkversion_cancleTxt);
        TextView updateTxt = (TextView) window.findViewById(R.id.update_apkversion_updateTxt);


        versionNumTxt.setText(versionNum);
        versionDescripTxt.setText("版本描述： " + versionInfo);
        cancleTxt.setText("退出");

        updateTxt.setText("立即更新");

        cancleTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppManager.getAppManager().AppExit(MainActivity.this);
            }
        });
        updateTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                //3.如果手机已经启动下载程序，执行downloadApk。否则跳转到设置界面
                if (DownLoadUtils.getInstance(getApplicationContext()).canDownload()) {
//                    DownloadApk.downloadApk(getApplicationContext(), appPathload, "科瑞仕更新", "科瑞仕");
                    showDownloadDialog(appPathload, versionNum, versionInfo);
                } else {
                    DownLoadUtils.getInstance(getApplicationContext()).skipToDownloadManager();
                }
            }
        });
    }

    private ProgressBar mProgress; //下载进度条控件
    private static final int DOWNLOADING = 1; //表示正在下载
    private static final int DOWNLOADED = 2; //下载完毕
    private static final int DOWNLOAD_FAILED = 3; //下载失败
    private int progress; //下载进度
    private boolean cancelFlag = false; //取消下载标志位


    private AlertDialog alertDialog2;
    private TextView progressTxt;

    private TextView versionNumTxt;
    private TextView versionDescripTxt;

    /**
     * 显示进度条对话框
     */
    public void showDownloadDialog(String uploadUrl, String versionnum, String versiondescrip) {
        alertDialog2 = new AlertDialog.Builder(this).create();

        alertDialog2.setCancelable(false);
        alertDialog2.show();
        Window window = alertDialog2.getWindow();
        window.setContentView(R.layout.softupdate_progress);

        versionNumTxt = (TextView) window.findViewById(R.id.dialog_updating_versionNumTxt);
        versionDescripTxt = (TextView) window.findViewById(R.id.dialog_updating_versionDescripTxt);

        mProgress = (ProgressBar) window.findViewById(R.id.update_progress);
        progressTxt = (TextView) window.findViewById(R.id.update_progressTxt);

        versionNumTxt.setText("版本号：" + versionnum);
        versionDescripTxt.setText("版本描述：" + versiondescrip);


        //下载apk
        downloadAPK(uploadUrl);
    }

    private static final String savePath = "/sdcard/updateAPK/"; //apk保存到SD卡的路径
    private static final String saveFileName = savePath +
            SmallFeatureUtils.getTimeYMDHMStr(String.valueOf(System.currentTimeMillis() / 1000), "yyyy-MM-dd hh:mm:ss") + "私人财富管家.apk"; //完整路径名

    public static final String APK_NAME = "update";

    /**
     * 下载apk的线程
     */
    public void downloadAPK(final String uploadUrl) {

        new InstallUtils(MainActivity.this, uploadUrl, APK_NAME, new InstallUtils.DownloadCallBack() {
            @Override
            public void onStart() {
                progressTxt.setText("更新进度：" + 0 + "%");
                mProgress.setProgress(0);
            }

            @Override
            public void onComplete(String path) {

                InstallUtils.installAPK(MainActivity.this, path, getPackageName() + ".fileProvider", new InstallUtils.InstallCallBack() {
                    @Override
                    public void onSuccess() {
                        ToastHelper.showTextToast(MainActivity.this,"正在安装程序");
                    }

                    @Override
                    public void onFail(Exception e) {
                        ToastHelper.showTextToast(MainActivity.this,"安装失败");

                    }
                });
                progressTxt.setText("更新进度：" + 100 + "%");
                mProgress.setProgress(100);
            }

            @Override
            public void onLoading(long total, long current) {

                progressTxt.setText("更新进度：" + (int) (current * 100 / total) + "%");
                mProgress.setProgress((int) (current * 100 / total));

            }

            @Override
            public void onFail(Exception e) {
                Logger.e("InstallUtils---onFail:" + e.getMessage());
            }
        }).downloadAPK();
    }


    private boolean isUpdate=false;

    private void valueVersion(String versionNum) {
        String thisversionNum = SmallFeatureUtils.getVersionName(this);
        if (versionNum.compareToIgnoreCase(thisversionNum) > 0) {
            isUpdate = true;
            versionInfoTxt.setTextColor(Color.parseColor("#E51C23"));
        } else {
            isUpdate = false;
            versionInfoTxt.setTextColor(Color.parseColor("#FFFFFF"));

        }
    }

    private void getUpdateVersion(String versionNum, String url, String content) {
        String thisversionNum = SmallFeatureUtils.getVersionName(this);
        if (versionNum.compareToIgnoreCase(thisversionNum) > 0) {
            hintUpDateDialog(versionNum, content, url);
        }
        valueVersion(versionNum);
    }

}
