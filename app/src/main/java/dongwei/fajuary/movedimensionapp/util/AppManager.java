package dongwei.fajuary.movedimensionapp.util;

import android.app.ActivityManager;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Activity管理类
 *
 */
public class AppManager {
    private  List<AppCompatActivity> activityStack;
    private List<AppCompatActivity> exitLiveExit;
    private static AppManager instance;

    private List<AppCompatActivity> exitHealthExit;


    private List<AppCompatActivity> exitWithdrawalsExit;

    private List<AppCompatActivity> shareActList;
    private AppManager() {
        if (activityStack == null) {
            activityStack = new LinkedList<>();
        }
        if (exitLiveExit==null){
            exitLiveExit = new LinkedList();
        }
        if (exitHealthExit==null){
            exitHealthExit = new LinkedList();
        }
        if (exitWithdrawalsExit==null){
            exitWithdrawalsExit = new LinkedList();
        }
        if (shareActList==null){
            shareActList = new LinkedList();
        }
    }
    /**
     * 单一实例
     */
    public static AppManager getAppManager() {
        if (instance == null) {
            instance = new AppManager();
        }
        return instance;
    }

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(AppCompatActivity activity) {


        activityStack.add(activity);
    }


    /**
     * 结束指定的Activity
     */
    public void finishActivity(AppCompatActivity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }

    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        for (AppCompatActivity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    /**
     * 退出应用程序
     */
    @SuppressWarnings("deprecation")
    public void AppExit(Context context) {
        try {
            finishAllActivity();
            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            activityManager.restartPackage(context.getPackageName());
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }

//
//        Iterator var1 = this.activityStack.iterator();
//
//        while (var1.hasNext()) {
//            AppCompatActivity activity = (AppCompatActivity) var1.next();
//            activity.finish();
//        }
//        System.exit(0);
    }



    public void exitLiveDetaill(){
        if (null!=exitLiveExit){
            activityStack.removeAll(exitLiveExit);
        }
        Iterator var1 = this.exitLiveExit.iterator();
        while (var1.hasNext()) {
            AppCompatActivity activity = (AppCompatActivity) var1.next();
            activity.finish();
        }
    }
    public void addLiveActivity(AppCompatActivity activity) {
        this.exitLiveExit.add(activity);
    }
    public void addHealthActivity(AppCompatActivity activity) {
        this.exitHealthExit.add(activity);
    }
    public void exitHealthDetaill(){
        if (null!=exitHealthExit){
            activityStack.removeAll(exitHealthExit);
        }
        Iterator var1 = this.exitLiveExit.iterator();
        while (var1.hasNext()) {
            AppCompatActivity activity = (AppCompatActivity) var1.next();
            activity.finish();
        }
    }

    public void addWithdrawalsActivity(AppCompatActivity activity) {
        this.exitWithdrawalsExit.add(activity);
    }
    public void exitWithdrawals(){
        if (null!=exitWithdrawalsExit){
            activityStack.removeAll(exitWithdrawalsExit);
        }
        Iterator var1 = this.exitLiveExit.iterator();
        while (var1.hasNext()) {
            AppCompatActivity activity = (AppCompatActivity) var1.next();
            activity.finish();
        }
    }

    /**
     * 加入分享
     */
    public void addShareAct(AppCompatActivity activity){
        this.shareActList.add(activity);
    }
    public void exitShareActs(){
        if (null!=shareActList){
            activityStack.removeAll(shareActList);
        }
        Iterator var1 = this.shareActList.iterator();
        while (var1.hasNext()) {
            AppCompatActivity activity = (AppCompatActivity) var1.next();
            activity.finish();
        }
    }
}
