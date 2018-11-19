package dongwei.fajuary.movedimensionapp.base;

import butterknife.ButterKnife;
import dongwei.fajuary.dongweimoveview.base.BaseActivity;
import dongwei.fajuary.movedimensionapp.util.AppManager;

/**
 * 作者：${神游风云（fajuary）} on 2018/1/31 19:30
 * 邮箱：fajuary@foxmail.com
 */

public abstract class AppBaseActivity extends BaseActivity{


    @Override
    public void joinActivity() {
        AppManager.getAppManager().addActivity(this);
    }

    public abstract void newInitView();
    @Override
    public void initViews() {
        ButterKnife.bind(this);
        newInitView();
    }
}
