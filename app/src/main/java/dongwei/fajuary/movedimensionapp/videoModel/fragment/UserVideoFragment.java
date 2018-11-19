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
import dongwei.fajuary.movedimensionapp.videoModel.adapter.UserVideoShooteAdapter;
import dongwei.fajuary.movedimensionapp.videoModel.bean.UserVideoBean;
import dongwei.fajuary.movedimensionapp.videoModel.bean.UserVideoData;
import dongwei.fajuary.movedimensionapp.videoModel.bean.UserVideoInfo;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserVideoFragment extends BaseFragment {


    @BindView(R.id.fragment_uservideo_mRecyclerView)
    XRecyclerView mRecyclerView;//我的视频列表

    private GridLayoutManager gridLayoutManager;
    private UserVideoShooteAdapter uservideoAdapter;

    private List<UserVideoInfo> uservideoList;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_user_video;
    }

    @Override
    public void initViews(View view) {
        /**头部
         */
        setUserVisibleHint(true);
        ButterKnife.bind(this, view);

        uservideoList=new ArrayList<>();
        uservideoAdapter=new UserVideoShooteAdapter(mContext);

        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        mRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);
        mRecyclerView
                .getDefaultRefreshHeaderView()
                .setRefreshTimeVisible(true);

        gridLayoutManager=new GridLayoutManager(mContext,2);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setAdapter(uservideoAdapter);
//        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(2, 20, true));


    }
    public static UserVideoFragment newInstance() {
        UserVideoFragment fragment = new UserVideoFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void initListener() {

        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                pageNum=1;
                uservideoList.clear();
                uservideoAdapter.setmInfos(uservideoList);

//                videoListAdapter.notifyDataSetChanged();
                getUserYearVideoListUrl();
            }

            @Override
            public void onLoadMore() {
                pageNum++;
                getUserYearVideoListUrl();
            }
        });

        //用户视频
        uservideoAdapter.setVideoItemClickListener(new UserVideoShooteAdapter.UserVideoItemClickListener() {
            @Override
            public void itemUserClick(UserVideoInfo userVideoInfo) {
                String videoId=userVideoInfo.getVideoId();//阿里视频id
                Intent intent=new Intent();
                intent.setClass(mContext, VideoDetailsActivity.class);
                intent.putExtra("videoId",videoId);
                intent.putExtra("type",userVideoInfo.getType());
                intent.putExtra("addtime",userVideoInfo.getAddtime());
                intent.putExtra("videoImgPath",userVideoInfo.getImgeUrl());
                startActivity(intent);
            }
        });

    }

    @Override
    public void processClick(View view) {

    }

    @Override
    public void initData() {
        getUserYearVideoListUrl();

    }
    private int pageNum=1;
    private void getUserYearVideoListUrl() {
        String token=preSaveUtil.getString("token");
        Map<String, String> params = new HashMap<>();
        params.put("pageNo", String.valueOf(pageNum));
        params.put("token",token);
        OkGo.<String>post(HttpUtils.getUserYearVideoListUrl)//
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
                                    UserVideoBean userVideoBean = JSON.parseObject(jsonStr, UserVideoBean.class);
                                    if (null!=userVideoBean){
                                        UserVideoData userVideoData=userVideoBean.getData();
                                        if (null!=userVideoData){
                                            List<UserVideoInfo> userVideoDataList=userVideoData.getList();
                                            uservideoList.addAll(userVideoDataList);
                                        }
                                    }
                                    uservideoAdapter.setmInfos(uservideoList);

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
