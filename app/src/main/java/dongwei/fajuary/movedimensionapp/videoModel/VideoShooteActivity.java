package dongwei.fajuary.movedimensionapp.videoModel;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import dongwei.fajuary.dongweimoveview.utils.StatusBarUtil;
import dongwei.fajuary.movedimensionapp.R;
import dongwei.fajuary.movedimensionapp.base.BaseHasLeftActivity;
import dongwei.fajuary.movedimensionapp.videoModel.fragment.EnterpriseVideoFragment;
import dongwei.fajuary.movedimensionapp.videoModel.fragment.UserVideoFragment;

/**
 * 视频拍摄
 */
public class VideoShooteActivity extends BaseHasLeftActivity {
    @BindView(R.id.activity_videoshoote_usernewyearTxt)
    TextView usernewyearTxt;//用户
    @BindView(R.id.activity_videoshoote_enterprisenewyearTxt)
    TextView enterprisenewyearTxt;//企业


    private List<Fragment> mFragments;

    @Override
    public int getLayoutId() {
        return R.layout.activity_video_shoote;
    }

    private EnterpriseVideoFragment videoFragment;
    private UserVideoFragment userVideoFragment;

    @Override
    public void newInitView() {
        titleTxt.setText("拜年视频");
        StatusBarUtil.setColor(this, Color.parseColor("#E51C23"));
        headRelayout.setBackgroundColor(Color.parseColor("#E51C23"));

        usernewyearTxt.setSelected(true);
        mFragments=new ArrayList<>();
        userVideoFragment=UserVideoFragment.newInstance();
        videoFragment= EnterpriseVideoFragment.newInstance();

        mFragments.add(userVideoFragment);
        mFragments.add(videoFragment);

        setIndexSelected(0);
    }

    @Override
    public void newViewListener() {
        usernewyearTxt.setOnClickListener(this);
        enterprisenewyearTxt.setOnClickListener(this);

    }

    @Override
    public void newViewClick(View view) {
        switch (view.getId()){
            case R.id.activity_videoshoote_usernewyearTxt:
                usernewyearTxt.setSelected(true);
                enterprisenewyearTxt.setSelected(false);
                userVideoFragment=UserVideoFragment.newInstance();
                mFragments.set(0,userVideoFragment);
                setIndexSelected(0);

                break;
            case R.id.activity_videoshoote_enterprisenewyearTxt:
                usernewyearTxt.setSelected(false);
                enterprisenewyearTxt.setSelected(true);
                videoFragment= EnterpriseVideoFragment.newInstance();
                mFragments.set(1,videoFragment);
                setIndexSelected(1);

                break;
        }
    }

    private int mIndex=-1;
    private void setIndexSelected(int index) {
        if(mIndex==index){
            return;
        }
        //再次赋值
        Fragment fragment=mFragments.get(index);
        //开启事务
        FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.activity_videoshoote_mFramelayout,fragment);
        fragmentTransaction.commit();
        mIndex=index;
    }

    @Override
    public void initData() {

    }



}
