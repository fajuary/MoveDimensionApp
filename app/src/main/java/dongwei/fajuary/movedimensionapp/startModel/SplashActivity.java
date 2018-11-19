package dongwei.fajuary.movedimensionapp.startModel;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import dongwei.fajuary.movedimensionapp.base.AppBaseActivity;
import dongwei.fajuary.movedimensionapp.base.MainActivity;
import dongwei.fajuary.movedimensionapp.loginModel.LoginActivity;
import dongwei.fajuary.movedimensionapp.util.AppManager;


public class SplashActivity extends AppBaseActivity {
    @Override
    public int getLayoutId() {
        return 0;
    }



    @Override
    public void newInitView() {
        String token=preSaveUtil.getString("token");
        Intent intent=new Intent();
        if (TextUtils.isEmpty(token)){
            intent.setClass(this, LoginActivity.class);
        }else {
            intent.setClass(this, MainActivity.class);
        }
        startActivity(intent);
        AppManager.getAppManager().finishActivity(this);
    }
    @Override
    public void initListener() {


    }


    @Override
    public void processClick(View v) {

    }
    @Override
    public void initData() {
    }



    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }







}
