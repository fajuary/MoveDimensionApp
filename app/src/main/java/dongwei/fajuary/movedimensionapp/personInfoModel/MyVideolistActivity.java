package dongwei.fajuary.movedimensionapp.personInfoModel;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import dongwei.fajuary.dongweimoveview.utils.StatusBarUtil;
import dongwei.fajuary.dongweimoveview.utils.ToastHelper;
import dongwei.fajuary.movedimensionapp.R;
import dongwei.fajuary.movedimensionapp.base.BaseHasLeftActivity;
import dongwei.fajuary.movedimensionapp.httpModel.HttpUtils;
import dongwei.fajuary.movedimensionapp.httpModel.StringDialogCallback;
import dongwei.fajuary.movedimensionapp.loginModel.LoginActivity;
import dongwei.fajuary.movedimensionapp.personInfoModel.adapter.VideoListAdapter;
import dongwei.fajuary.movedimensionapp.personInfoModel.bean.MyYearVideoBean;
import dongwei.fajuary.movedimensionapp.personInfoModel.bean.MyYearVideoData;
import dongwei.fajuary.movedimensionapp.personInfoModel.bean.MyYearVideoInfo;
import dongwei.fajuary.movedimensionapp.videoModel.VideoDetailsActivity;

/**
 * 我的视频列表
 */
public class MyVideolistActivity extends BaseHasLeftActivity implements VideoListAdapter.VideoItemClickListener {

    @BindView(R.id.activity_videolist_mRecyclerView)
    XRecyclerView mRecyclerView;//我的视频列表

    private VideoListAdapter videoListAdapter;
    private LinearLayoutManager verlinlayoutManager;

    private List<MyYearVideoInfo> videoList;



    @Override
    public int getLayoutId() {
        return R.layout.activity_my_videolist;
    }

    @Override
    public void newInitView() {

        titleTxt.setText("我的视频");
        StatusBarUtil.setColor(this, Color.parseColor("#E51C23"));
        headRelayout.setBackgroundColor(Color.parseColor("#E51C23"));


        videoList=new ArrayList<>();
        videoListAdapter=new VideoListAdapter(this);
        verlinlayoutManager=new LinearLayoutManager(this);
        verlinlayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(verlinlayoutManager);
        mRecyclerView.setAdapter(videoListAdapter);

        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        mRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);
        mRecyclerView
                .getDefaultRefreshHeaderView()
                .setRefreshTimeVisible(true);

        mRecyclerView.refresh();



    }

    @Override
    public void newViewListener() {
        videoListAdapter.setVideoItemClickListener(this);

        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                pageNo=1;
                videoList.clear();
                videoListAdapter.setmInfos(videoList);

                getMyVideosListUrl();
            }

            @Override
            public void onLoadMore() {
                pageNo++;
                getMyVideosListUrl();
            }
        });
    }

    @Override
    public void newViewClick(View view) {

    }
    @Override
    public void initData() {
        getMyVideosListUrl();
    }

    private int pageNo=1;
    private void getMyVideosListUrl() {
        String token=preSaveUtil.getString("token");
        Map<String, String> params = new HashMap<>();
        params.put("pageNo", String.valueOf(pageNo));
        params.put("token",token);
        OkGo.<String>post(HttpUtils.getMyVideosListUrl)//
                .tag(this)//
                .cacheKey("getWorshipYearVideoListUrl")
                .params(params)        // 这里可以上传参数
                .execute(new StringDialogCallback(this) {
                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastHelper.showTextToast(MyVideolistActivity.this,"检查网络，请重试...");
                    }

                    @Override
                    public void onSuccess(com.lzy.okgo.model.Response<String> response) {

                        String jsonStr = response.body();
                        Logger.json(jsonStr);

                        if(pageNo==1){
                            mRecyclerView.refreshComplete();
                        }

                        try {
                            JSONObject jsonObject = new JSONObject(jsonStr);
                            if (null != jsonObject) {
                                String status = jsonObject.optString("status");
                                String msgStr = jsonObject.optString("msg");
                                if (status.equals("200")){
                                    MyYearVideoBean worshipYearVideoBean = JSON.parseObject(jsonStr, MyYearVideoBean.class);
                                    if (null!=worshipYearVideoBean){
                                        MyYearVideoData yearVideoData=worshipYearVideoBean.getData();
                                        if (null!=yearVideoData){
                                            List<MyYearVideoInfo> yearVideoDataList=yearVideoData.getList();
                                            videoList.addAll(yearVideoDataList);

                                        }
                                    }
                                    videoListAdapter.setmInfos(videoList);

                                }else if (status.equals("301")){
                                    ToastHelper.showTextToast(MyVideolistActivity.this,"请重新登录");
                                    Intent intent=new Intent();
                                    intent.setClass(MyVideolistActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                }else {

                                    ToastHelper.showTextToast(MyVideolistActivity.this,msgStr);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
    //查看视频详情
    @Override
    public void itemClick(MyYearVideoInfo yearVideoInfo) {
        String videoId=yearVideoInfo.getVideoId();//阿里视频id
        Intent intent=new Intent();
        intent.setClass(this, VideoDetailsActivity.class);
        intent.putExtra("videoId",videoId);
        intent.putExtra("type",yearVideoInfo.getType());
        intent.putExtra("addtime",yearVideoInfo.getAddtime());
        intent.putExtra("videoImgPath",yearVideoInfo.getImgeUrl());
        startActivity(intent);
    }
}
