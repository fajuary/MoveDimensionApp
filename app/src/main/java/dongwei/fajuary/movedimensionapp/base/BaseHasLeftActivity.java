package dongwei.fajuary.movedimensionapp.base;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import dongwei.fajuary.movedimensionapp.R;
import dongwei.fajuary.movedimensionapp.util.AppManager;

/**
 * 作者：${神游风云（fajuary）} on 2018/1/31 20:33
 * 邮箱：fajuary@foxmail.com
 *
 * 有头部并且有左边的箭头→
 */

public abstract class BaseHasLeftActivity extends AppBaseActivity {
    @BindView(R.id.base_backleftnoright_headRelayout)
    public RelativeLayout headRelayout;//顶部

    @BindView(R.id.base_backleftnoright_leftRelayout)
    public RelativeLayout leftRelayout;//向左
    @BindView(R.id.base_backleftnoright_leftImgView)
    public ImageView leftImgView;//向左
    @BindView(R.id.base_backleftnoright_titleTxt)
    public TextView titleTxt;//标题
    @Override
    public void joinActivity() {
        AppManager.getAppManager().addActivity(this);
    }

    public abstract void newInitView();
    public abstract void newViewListener();//
    public abstract void newViewClick(View view);
    @Override
    public void initListener() {
        leftRelayout.setOnClickListener(this);
        newViewListener();
    }

    @Override
    public void processClick(View view) {
        if (view.getId()==R.id.base_backleftnoright_leftRelayout){
            setResult(RESULT_OK,new Intent());
            AppManager.getAppManager().finishActivity(this);
        }else {
            newViewClick(view);
        }
    }

}
