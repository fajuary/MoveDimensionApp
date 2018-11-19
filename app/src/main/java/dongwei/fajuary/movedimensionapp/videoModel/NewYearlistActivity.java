package dongwei.fajuary.movedimensionapp.videoModel;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import dongwei.fajuary.dongweimoveview.utils.StatusBarUtil;
import dongwei.fajuary.dongweimoveview.utils.ToastHelper;
import dongwei.fajuary.dongweimoveview.viewModel.GridSpacingItemDecoration;
import dongwei.fajuary.movedimensionapp.R;
import dongwei.fajuary.movedimensionapp.base.BaseHasLeftActivity;
import dongwei.fajuary.movedimensionapp.httpModel.HttpUtils;
import dongwei.fajuary.movedimensionapp.httpModel.StringDialogCallback;
import dongwei.fajuary.movedimensionapp.loginModel.LoginActivity;
import dongwei.fajuary.movedimensionapp.videoModel.adapter.RankeListAdapter;
import dongwei.fajuary.movedimensionapp.videoModel.adapter.VideoPrizesAdapter;
import dongwei.fajuary.movedimensionapp.videoModel.bean.PrizesInfo;
import dongwei.fajuary.movedimensionapp.videoModel.bean.RankingBean;
import dongwei.fajuary.movedimensionapp.videoModel.bean.RankingData;
import dongwei.fajuary.movedimensionapp.videoModel.bean.UsersInfo;

/**
 * 拜年排行榜
 */
public class NewYearlistActivity extends BaseHasLeftActivity {


    @BindView(R.id.activity_newyearlist_sortRecycleView)
    RecyclerView sortRecycleView;//头部分类

    @BindView(R.id.activity_newyearlist_rankeRecycleView)
    RecyclerView rankeRecycleView;//拜年排行榜

    private VideoPrizesAdapter sortAdapter;
    private LinearLayoutManager horelayoutManager;

    private RankeListAdapter rankeListAdapter;
    private GridLayoutManager gridLayoutManager;


    @Override
    public int getLayoutId() {
        return R.layout.activity_new_yearlist;
    }

    @Override
    public void newInitView() {
        titleTxt.setText("拜年的大奖");
        StatusBarUtil.setColor(this, Color.parseColor("#E51C23"));
        headRelayout.setBackgroundColor(Color.parseColor("#E51C23"));

        sortAdapter=new VideoPrizesAdapter(this);
        horelayoutManager=new LinearLayoutManager(this);
        horelayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        sortRecycleView.setLayoutManager(horelayoutManager);
        sortRecycleView.setAdapter(sortAdapter);

        rankeListAdapter=new RankeListAdapter(this);
        gridLayoutManager=new GridLayoutManager(this,2);
        rankeRecycleView.setLayoutManager(gridLayoutManager);
        rankeRecycleView.setAdapter(rankeListAdapter);
        rankeRecycleView.addItemDecoration(new GridSpacingItemDecoration(2, 30, true));


    }

    @Override
    public void newViewListener() {

    }

    @Override
    public void newViewClick(View view) {

    }

    @Override
    public void initData() {

        getRankingVideoListUrl();
    }

    private void getRankingVideoListUrl() {
        String token=preSaveUtil.getString("token");
        Map<String, String> params = new HashMap<>();
        params.put("token",token);
        OkGo.<String>post(HttpUtils.getRankingVideoListUrl)//
                .tag(this)//
                .cacheKey("getRankingVideoListUrl")
                .params(params)        // 这里可以上传参数
                .execute(new StringDialogCallback(this) {
                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastHelper.showTextToast(NewYearlistActivity.this,"检查网络，请重试...");
                    }

                    @Override
                    public void onSuccess(com.lzy.okgo.model.Response<String> response) {

                        String jsonStr = response.body();
                        Logger.json(jsonStr);

                        try {
                            JSONObject jsonObject = new JSONObject(jsonStr);
                            if (null != jsonObject) {
                                String status = jsonObject.optString("status");
                                String msgStr = jsonObject.optString("msg");
                                if (status.equals("200")){
                                    RankingBean rankingBean = JSON.parseObject(jsonStr, RankingBean.class);
                                    if (null!=rankingBean){
                                        RankingData rankingData=rankingBean.getData();
                                        if (null!=rankingData){
                                            List<PrizesInfo> prizesList=rankingData.getPrizes();
                                            List<UsersInfo> usersList=rankingData.getUsers();

                                            sortAdapter.setmInfos(prizesList);

                                            rankeListAdapter.setmInfos(usersList);

                                        }
                                    }
                                }else if (status.equals("301")){
                                    ToastHelper.showTextToast(NewYearlistActivity.this,"请重新登录");
                                    Intent intent=new Intent();
                                    intent.setClass(NewYearlistActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                }else {
                                    ToastHelper.showTextToast(NewYearlistActivity.this,msgStr);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

}
