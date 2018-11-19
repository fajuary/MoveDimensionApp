package dongwei.fajuary.movedimensionapp.base;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.alivc.player.AliVcMediaPlayer;
import com.aliyun.common.httpfinal.QupaiHttpFinal;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.common.QueuedWork;

import dongwei.fajuary.dongweimoveview.viewModel.autolayout.config.AutoLayoutConifg;

/**
 * 作者：${神游风云（fajuary）} on 2018/1/30 17:37
 * 邮箱：fajuary@foxmail.com
 */

public class MovedimensionApp extends Application{

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static MovedimensionApp instance;

    public static MovedimensionApp getInstance() {
        return instance;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        AutoLayoutConifg.getInstance().useDeviceSize().init(this);

        loadLibs();
        QupaiHttpFinal.getInstance().initOkHttpFinal();

        Logger.init("mytag")    //LOG TAG默认是PRETTYLOGGER
                .methodCount(3)                 // 决定打印多少行（每一行代表一个方法）默认：2
                .hideThreadInfo()               // 隐藏线程信息 默认：显示
                .logLevel(LogLevel.NONE)        // 是否显示Log 默认：LogLevel.FULL（全部显示）
                .methodOffset(2);                // 默认：0

        //开启debug模式，方便定位错误，具体错误检查方式可以查看http://dev.umeng.com/social/android/quick-integration的报错必看，正式发布，请关闭该模式
        Config.DEBUG = true;
        QueuedWork.isUseThreadPool = false;
        UMShareAPI.get(this);
        initShareSdk();
    }
    private void loadLibs(){
        System.loadLibrary("QuCore-ThirdParty");
        System.loadLibrary("QuCore");
        //初始化播放器（只需调用一次即可，建议在application中初始化）
        AliVcMediaPlayer.init(getApplicationContext());
    }

    private void initShareSdk(){
        PlatformConfig.setWeixin("wxb6f21429e3e90611","f8fba5ab52daee9e26a33f0f88a183d3");
        PlatformConfig.setQQZone("1105401051","Y26hpTBJyQLs4l5o");
        Config.isJumptoAppStore = true;
    }
}
