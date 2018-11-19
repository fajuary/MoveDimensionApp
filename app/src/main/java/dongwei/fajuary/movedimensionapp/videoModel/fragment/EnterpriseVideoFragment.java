package dongwei.fajuary.movedimensionapp.videoModel.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
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
import butterknife.ButterKnife;
import dongwei.fajuary.dongweimoveview.utils.ToastHelper;
import dongwei.fajuary.movedimensionapp.R;
import dongwei.fajuary.movedimensionapp.base.BaseFragment;
import dongwei.fajuary.movedimensionapp.httpModel.HttpUtils;
import dongwei.fajuary.movedimensionapp.httpModel.StringDialogCallback;
import dongwei.fajuary.movedimensionapp.loginModel.LoginActivity;
import dongwei.fajuary.movedimensionapp.videoModel.VideoDetailsActivity;
import dongwei.fajuary.movedimensionapp.videoModel.adapter.EnterpriseVideoShooteAdapter;
import dongwei.fajuary.movedimensionapp.videoModel.bean.EnterpriseVideoBean;
import dongwei.fajuary.movedimensionapp.videoModel.bean.EnterpriseVideoData;
import dongwei.fajuary.movedimensionapp.videoModel.bean.EnterpriseVideoInfo;

/**
 * A simple {@link Fragment} subclass.
 */
public class EnterpriseVideoFragment extends BaseFragment {

    @BindView(R.id.fragment_enterprisevideo_mRecyclerView)
    XRecyclerView mRecyclerView;//我的视频列表

    private EnterpriseVideoShooteAdapter enterpriseVideoShooteAdapter;
    private List<EnterpriseVideoInfo> enterprisevideoList;
    private GridLayoutManager gridLayoutManager;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_enterprise_video;
    }

    @Override
    public void initViews(View view) {
        /**头部
         */
        setUserVisibleHint(true);
        ButterKnife.bind(this, view);

        enterprisevideoList=new ArrayList<>();
        enterpriseVideoShooteAdapter=new EnterpriseVideoShooteAdapter(mContext);

        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        mRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);
        mRecyclerView
                .getDefaultRefreshHeaderView()
                .setRefreshTimeVisible(true);

        gridLayoutManager=new GridLayoutManager(mContext,2);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setAdapter(enterpriseVideoShooteAdapter);
//        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(2, 20, false));


    }

    public static EnterpriseVideoFragment newInstance() {
        EnterpriseVideoFragment fragment = new EnterpriseVideoFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void processClick(View view) {

    }
    @Override
    public void initListener() {
        //企业视频
        enterpriseVideoShooteAdapter.setVideoItemClickListener(new EnterpriseVideoShooteAdapter.EnterpriseVideoItemClickListener() {
            @Override
            public void itemEnterpriseClick(EnterpriseVideoInfo enterpriseVideoInfo) {
                String videoId=enterpriseVideoInfo.getVideoId();//阿里视频id
                Intent intent=new Intent();
                intent.setClass(mContext, VideoDetailsActivity.class);
                intent.putExtra("videoId",videoId);
                intent.putExtra("type",enterpriseVideoInfo.getType());
                intent.putExtra("addtime",enterpriseVideoInfo.getAddtime());
                intent.putExtra("videoImgPath",enterpriseVideoInfo.getImgeUrl());
                startActivity(intent);
            }
        });

        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                pageNum=1;
                enterprisevideoList.clear();
                enterpriseVideoShooteAdapter.setmInfos(enterprisevideoList);

                getEnterpriseVideoListUrl();
            }

            @Override
            public void onLoadMore() {
                pageNum++;
                getEnterpriseVideoListUrl();
            }
        });
    }

    @Override
    public void initData() {
        getEnterpriseVideoListUrl();
    }

    private int pageNum=1;
    private void getEnterpriseVideoListUrl() {
        String token=preSaveUtil.getString("token");
        Map<String, String> params = new HashMap<>();
        params.put("pageNo", String.valueOf(pageNum));
        params.put("token",token);
        OkGo.<String>post(HttpUtils.getEnterpriseVideoListUrl)//
                .tag(this)//
                .cacheKey("getUserYearVideoListUrl")
                .params(params)        // 这里可以上传参数
                .execute(new StringDialogCallback(activity) {
                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastHelper.showTextToast(mContext,"检查网络，请重试...");
                    }

                    @Override
                    public void onSuccess(com.lzy.okgo.model.Response<String> response) {

                        String jsonStr = response.body();
                        Logger.json(jsonStr);

                        if(pageNum==1){
                            mRecyclerView.refreshComplete();
                        }

                        try {
                            JSONObject jsonObject = new JSONObject(jsonStr);
                            if (null != jsonObject) {
                                String status = jsonObject.optString("status");
                                String msgStr = jsonObject.optString("msg");
                                if (status.equals("200")){
                                    EnterpriseVideoBean enterpriseVideoBean = JSON.parseObject(jsonStr, EnterpriseVideoBean.class);
                                    if (null!=enterpriseVideoBean){
                                        EnterpriseVideoData enterpriseVideoData=enterpriseVideoBean.getData();
                                        if (null!=enterpriseVideoData){
                                            List<EnterpriseVideoInfo> enterpriseVideoDataList=enterpriseVideoData.getList();
                                            enterprisevideoList.addAll(enterpriseVideoDataList);
                                        }
                                    }

                                    enterpriseVideoShooteAdapter.setmInfos(enterprisevideoList);

                                }else if (status.equals("301")){
                                    ToastHelper.showTextToast(mContext,"请重新登录");
                                    Intent intent=new Intent();
                                    intent.setClass(mContext, LoginActivity.class);
                                    startActivity(intent);
                                }else {
                                    ToastHelper.showTextToast(mContext,msgStr);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }



}
