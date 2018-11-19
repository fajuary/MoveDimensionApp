package dongwei.fajuary.movedimensionapp.videoModel.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import dongwei.fajuary.dongweimoveview.base.BaseHolder;
import dongwei.fajuary.dongweimoveview.base.DefaultAdapter;
import dongwei.fajuary.dongweimoveview.utils.SmallFeatureUtils;
import dongwei.fajuary.movedimensionapp.R;
import dongwei.fajuary.movedimensionapp.util.PictureLoadUtil;
import dongwei.fajuary.movedimensionapp.videoModel.bean.SpecialEffectsInfo;

/**
 * 作者：${神游风云（fajuary）} on 2018/2/2 16:28
 * 邮箱：fajuary@foxmail.com
 * 特效列表
 */

public class SpecialListAdapter extends DefaultAdapter {

    public SpecialListAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    public BaseHolder getHolder(View contentView, int viewType) {
        SpecialEffectsHolder viewHolder=new SpecialEffectsHolder(contentView);
        return viewHolder;
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.recycleview_specialeffects_itemlayout;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof SpecialEffectsHolder){
            SpecialEffectsHolder viewHolder= (SpecialEffectsHolder) holder;

            SpecialEffectsInfo effectsInfo= (SpecialEffectsInfo) mInfos.get(position);
            if (null!=effectsInfo){
                double moneyDl=effectsInfo.getMoney();

                String picUrl=effectsInfo.getPicUrl();
                if (TextUtils.isEmpty(picUrl)){
                    picUrl="no";
                }
                PictureLoadUtil.loadDefaultImg(viewHolder.effectsImg,picUrl,R.mipmap.head_sortbg);

                viewHolder.effectsPriceTxt.setText("¥"+moneyDl);

                boolean selected=effectsInfo.isSelected();

                if (selected){
                    viewHolder.effectSelectImg.setVisibility(View.VISIBLE);
                }else {
                    viewHolder.effectSelectImg.setVisibility(View.GONE);
                }
            }
        }
//        SpecialEffectsInfo
    }

    class SpecialEffectsHolder extends BaseHolder{

        @BindView(R.id.recycleview_specialeffects_speciallinLayout)
        LinearLayout speciallinLayout;

        @BindView(R.id.recycleview_specialeffects_effectsImg)
        ImageView effectsImg;

        @BindView(R.id.recycleview_specialeffects_effectSelectImg)
        ImageView effectSelectImg;//选中特效框

        @BindView(R.id.recycleview_specialeffects_effectsPriceTxt)
        TextView effectsPriceTxt;

        public SpecialEffectsHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            int width = SmallFeatureUtils.getWindowWith((Activity) mContext);
            /**
             * 设置图片宽高
             */
            LinearLayout.LayoutParams layoutParams =
                    new LinearLayout.LayoutParams(width/4,LinearLayout.LayoutParams.WRAP_CONTENT);
            speciallinLayout.setLayoutParams(layoutParams);
            speciallinLayout.setPadding(0,40,0,40);
        }

        @Override
        public void setData(Object data, int position) {

        }
    }
}
