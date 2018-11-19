package dongwei.fajuary.movedimensionapp.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import dongwei.fajuary.movedimensionapp.R;

/**
 * 作者：${神游风云（fajuary）} on 2018/2/3 13:58
 * 邮箱：fajuary@foxmail.com
 */

public class DialogUtils {

    public AlertDialog getAlertDialog(Activity mActivity){
        AlertDialog alertDialog = null;
        //初始化Builder
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(mActivity);
        //加载自定义的那个View,同时设置下
        final LayoutInflater layoutInflater =mActivity.getLayoutInflater();
        View viewcustomView = layoutInflater.inflate(R.layout.dialog_whether_itemlayout, null,false);
        alertBuilder.setView(viewcustomView);
        alertBuilder.setCancelable(false);
        alertDialog = alertBuilder.create();

        return alertDialog;
    }


    //选择卡牌
    public static Dialog selectPayTypeDialog(Context mContext){
        Dialog selectPayTypeDialog;
        selectPayTypeDialog = new Dialog(mContext, R.style.MyDialog);
        selectPayTypeDialog.setCanceledOnTouchOutside(true);
        selectPayTypeDialog.setCancelable(true);
        selectPayTypeDialog.setContentView(R.layout.dialog_selectpaytype_itemlayout);
        Window window = selectPayTypeDialog.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) mContext).getWindowManager().getDefaultDisplay().getMetrics(dm);
        params.width = LinearLayout.LayoutParams.MATCH_PARENT;
        params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        window.setWindowAnimations(R.style.buycard_dialogstyle);
        window.setGravity(Gravity.BOTTOM);
        window.setAttributes(params);
        return selectPayTypeDialog;
    }

}
