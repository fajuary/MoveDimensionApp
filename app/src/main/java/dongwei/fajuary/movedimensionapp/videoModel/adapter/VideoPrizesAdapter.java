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
import dongwei.fajuary.movedimensionapp.videoModel.bean.PrizesInfo;

/**
 * 作者：${神游风云（fajuary）} on 2018/2/1 14:01
 * 邮箱：fajuary@foxmail.com
 *
 * 视频排行榜首页分类
 */

public class VideoPrizesAdapter extends DefaultAdapter {

    private Context mContext;
    public VideoPrizesAdapter(Context mContext) {
        super(mContext);
        this.mContext=mContext;
    }

    @Override
    public BaseHolder getHolder(View contentView, int viewType) {//viewType布局类型
        VideoSortHolder viewHolder=new VideoSortHolder(contentView);

        return viewHolder;
    }

    @Override
    public int getLayoutId(int ewType) {//viewType布局类型
        return R.layout.recycleview_videoheadsort_itemlayout;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//        UsersInfo
        if (holder instanceof VideoSortHolder){
            VideoSortHolder viewHolder= (VideoSortHolder) holder;
            PrizesInfo prizesInfo= (PrizesInfo) mInfos.get(position);
            if (null!=prizesInfo){
                String prizeImgUrl=prizesInfo.getImg();
                if (TextUtils.isEmpty(prizeImgUrl)){
                    prizeImgUrl="no";
                }
                String prizename=prizesInfo.getName();
                if (TextUtils.isEmpty(prizename)){
                    prizename="无";
                }
                viewHolder.prizestitleTxt.setText(prizename);
                int numberInt=prizesInfo.getNumber();
                PictureLoadUtil.loadDefaultImg(viewHolder.prizesImg,prizeImgUrl,R.mipmap.head_sortbg);
                viewHolder.prizesNumTxt.setText(numberInt+"名");

            }

        }
    }

     class VideoSortHolder extends BaseHolder{

        @BindView(R.id.recycleview_videoheadsort_itemlinlayout)
        LinearLayout itemlinlayout;

        @BindView(R.id.recycleview_headsortvideo_prizesImg)
        ImageView prizesImg;
        @BindView(R.id.recycleview_headsortvideo_prizestitleTxt)
        TextView prizestitleTxt;
        @BindView(R.id.recycleview_headsortvideo_prizesNumTxt)
        TextView prizesNumTxt;//奖品数量

        public VideoSortHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            int width = SmallFeatureUtils.getWindowWith((Activity) mContext);

            /**
             * 设置图片宽高
             */
            LinearLayout.LayoutParams layoutParams =
                    new LinearLayout.LayoutParams(width/3,LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(4,30,20,30);
            itemlinlayout.setLayoutParams(layoutParams);
            itemlinlayout.setPadding(0,40,0,30);

            LinearLayout.LayoutParams imageParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,width/4);
            imageParams.setMargins(20,0,20,0);
            prizesImg.setLayoutParams(imageParams);

        }

        @Override
        public void setData(Object data, int position) {

        }
    }
}
