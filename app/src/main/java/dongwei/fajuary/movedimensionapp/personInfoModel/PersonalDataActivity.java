package dongwei.fajuary.movedimensionapp.personInfoModel;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
import dongwei.fajuary.movedimensionapp.R;
import dongwei.fajuary.movedimensionapp.base.BaseHasLeftActivity;
import dongwei.fajuary.movedimensionapp.httpModel.HttpUtils;
import dongwei.fajuary.movedimensionapp.httpModel.StringDialogCallback;
import dongwei.fajuary.movedimensionapp.loginModel.LoginActivity;
import dongwei.fajuary.movedimensionapp.personInfoModel.adapter.BlesseListAdapter;
import dongwei.fajuary.movedimensionapp.personInfoModel.adapter.PraiseListAdapter;
import dongwei.fajuary.movedimensionapp.personInfoModel.bean.GiftVoinfo;
import dongwei.fajuary.movedimensionapp.personInfoModel.bean.GiftgiveVoinfo;
import dongwei.fajuary.movedimensionapp.personInfoModel.bean.PersonalBean;
import dongwei.fajuary.movedimensionapp.personInfoModel.bean.PersonalData;
import dongwei.fajuary.movedimensionapp.personInfoModel.bean.SpotFabuloussInfo;
import dongwei.fajuary.movedimensionapp.util.PictureLoadUtil;

/**
 * 我的信息
 */
public class PersonalDataActivity extends BaseHasLeftActivity {

    @BindView(R.id.activity_personaldata_personImgView)
    ImageView personImgView;//头像

    @BindView(R.id.activity_personaldata_nicknameTxt)
    TextView nicknameTxt;//昵称

    @BindView(R.id.activity_personaldata_blessingNumTxt)
    TextView blessingNumTxt;//共积祝福数
    @BindView(R.id.activity_personaldata_nationalRankeNumTxt)
    TextView nationalRankeNumTxt;//全国排名

    @BindView(R.id.activity_personaldata_bindphoneNumTxt)
    TextView bindphoneNumTxt;//未绑定手机号

    @BindView(R.id.activity_personaldata_goodluckNumImg)
    ImageView goodluckNumImg;//福个数图片
    @BindView(R.id.activity_personaldata_goodluckNumTxt)
    TextView goodluckNumTxt;//福个数

    @BindView(R.id.activity_personaldata_justNumImg)
    ImageView justNumImg;//才个数图片
    @BindView(R.id.activity_personaldata_justNumTxt)
    TextView justNumTxt;//才个数

    @BindView(R.id.activity_personaldata_prosperityNumImg)
    ImageView prosperityNumImg;//禄个数图片
    @BindView(R.id.activity_personaldata_prosperityNumTxt)
    TextView prosperityNumTxt;//禄个数


    @BindView(R.id.activity_personaldata_blesseRecyclerView)
    RecyclerView blesseRecyclerView;//福列表
    @BindView(R.id.activity_personaldata_praiseRecyclerView)
    RecyclerView praiseRecyclerView;//赞列表


    private BlesseListAdapter blesseListAdapter;
    private PraiseListAdapter praiseListAdapter;
    private LinearLayoutManager blesseVerlinlayout;
    private LinearLayoutManager praiseVerlinlayout;

    private List<GiftgiveVoinfo> blesseList;
    private List<SpotFabuloussInfo> praiseList;

    @Override
    public int getLayoutId() {
        return R.layout.activity_personal_data;
    }

    @Override
    public void newInitView() {
        titleTxt.setText("我的信息");
        StatusBarUtil.setColor(this, Color.parseColor("#E51C23"));
        headRelayout.setBackgroundColor(Color.parseColor("#E51C23"));

        blesseListAdapter=new BlesseListAdapter(this);
        blesseVerlinlayout=new LinearLayoutManager(this);
        blesseVerlinlayout.setOrientation(LinearLayoutManager.VERTICAL);
        blesseRecyclerView.setLayoutManager(blesseVerlinlayout);
        blesseRecyclerView.setAdapter(blesseListAdapter);

        praiseListAdapter=new PraiseListAdapter(this);
        praiseVerlinlayout=new LinearLayoutManager(this);
        praiseVerlinlayout.setOrientation(LinearLayoutManager.VERTICAL);
        praiseRecyclerView.setLayoutManager(praiseVerlinlayout);
        praiseRecyclerView.setAdapter(praiseListAdapter);



    }

    @Override
    public void newViewListener() {

    }

    @Override
    public void newViewClick(View view) {
    }

    @Override
    public void initData() {
        getPersonalDataUrl();
    }

    private void setPersonInfo( PersonalData personalData){
        String iconUrl=personalData.getIcon();
        if (TextUtils.isEmpty(iconUrl)){
            iconUrl="no";
        }
        PictureLoadUtil.loadDefaultImg(personImgView,iconUrl,R.drawable.default_personimg);


        int rankingInt=personalData.getRanking();//排名
        int spotLaudInt=personalData.getSpotLaud();//点赞数
        String nickname=personalData.getNickname();
        if (TextUtils.isEmpty(nickname)){
            nickname="匿名";
        }
        String phone=personalData.getPhone();
        if (TextUtils.isEmpty(phone)){
            bindphoneNumTxt.setText("未绑定手机号");
        }else {
            bindphoneNumTxt.setText(phone);
        }
        nicknameTxt.setText(nickname);
        //全国排名
        nationalRankeNumTxt.setText("NO."+rankingInt);
        blessingNumTxt.setText(spotLaudInt+" 个");

        List<GiftVoinfo> giftVoList=personalData.getGiftVoList();
        if (giftVoList.size()>0){
            GiftVoinfo giftVoinfo=giftVoList.get(0);
            if (null!=giftVoinfo){
                String picUrl=giftVoinfo.getPicUrl();
                if (TextUtils.isEmpty(picUrl)){
                    picUrl="no";
                }
                int number=giftVoinfo.getNumber();
                goodluckNumTxt.setText(number+" 个");

                PictureLoadUtil.loadDefaultImg(goodluckNumImg,picUrl,R.mipmap.myinfo_blesseicon);
            }
        }

        if (giftVoList.size()>1){
            GiftVoinfo giftVoinfo=giftVoList.get(1);
            String picUrl=giftVoinfo.getPicUrl();
            if (TextUtils.isEmpty(picUrl)){
                picUrl="no";
            }
            int number=giftVoinfo.getNumber();

            justNumTxt.setText(number+" 个");

            PictureLoadUtil.loadDefaultImg(justNumImg,picUrl,R.mipmap.myinfo_justicon);

        }

        if (giftVoList.size()>2){
            GiftVoinfo giftVoinfo=giftVoList.get(2);
            String picUrl=giftVoinfo.getPicUrl();
            if (TextUtils.isEmpty(picUrl)){
                picUrl="no";
            }
            int number=giftVoinfo.getNumber();
            prosperityNumTxt.setText(number+" 个");
            PictureLoadUtil.loadDefaultImg(prosperityNumImg,picUrl,R.mipmap.myinfo_prosperityicon);
        }

        blesseList=personalData.getGiftGiveVoList();
        blesseListAdapter.setmInfos(blesseList);

        praiseList=personalData.getSpotFabulouss();

        praiseListAdapter.setmInfos(praiseList);


    }

    private void getPersonalDataUrl() {
        String token=preSaveUtil.getString("token");
        Map<String, String> params = new HashMap<>();
        params.put("token", token);
        OkGo.<String>post(HttpUtils.getPersonalDataUrl)//
                .tag(this)//
                .cacheKey("getPersonalDataUrl")
                .params(params)        // 这里可以上传参数
                .execute(new StringDialogCallback(this) {
                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastHelper.showTextToast(PersonalDataActivity.this,"检查网络，请重试...");
                    }

                    @Override
                    public void onSuccess(com.lzy.okgo.model.Response<String> response) {

                        String jsonStr = response.body();
                        Logger.json(jsonStr);

                        try {
                            JSONObject jsonObject = new JSONObject(jsonStr);
                            if (null != jsonObject) {
                                int statusInt=jsonObject.optInt("status");
                                String msgStr=jsonObject.optString("msg");
                                if (statusInt==200){
                                    PersonalBean personalBean = JSON.parseObject(jsonStr, PersonalBean.class);
                                    if (null!=personalBean){
                                        PersonalData personalData=personalBean.getData();
                                        if (null!=personalData){
                                            setPersonInfo(personalData);
                                        }
                                    }
                                }else if (statusInt==301){
                                    ToastHelper.showTextToast(PersonalDataActivity.this,"请重新登录");
                                    Intent intent=new Intent();
                                    intent.setClass(PersonalDataActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                }else {
                                    ToastHelper.showTextToast(PersonalDataActivity.this,msgStr);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
