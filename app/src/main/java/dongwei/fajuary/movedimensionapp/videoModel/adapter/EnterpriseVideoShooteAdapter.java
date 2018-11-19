package dongwei.fajuary.movedimensionapp.videoModel.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import dongwei.fajuary.dongweimoveview.base.BaseHolder;
import dongwei.fajuary.dongweimoveview.base.DefaultAdapter;
import dongwei.fajuary.dongweimoveview.utils.SmallFeatureUtils;
import dongwei.fajuary.movedimensionapp.R;
import dongwei.fajuary.movedimensionapp.appview.RoundTransform;
import dongwei.fajuary.movedimensionapp.base.MovedimensionApp;
import dongwei.fajuary.movedimensionapp.videoModel.bean.EnterpriseVideoInfo;

/**
 * 作者：${神游风云（fajuary）} on 2018/2/11 18:58
 * 邮箱：fajuary@foxmail.com
 */

public class EnterpriseVideoShooteAdapter extends DefaultAdapter {

    public EnterpriseVideoShooteAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    public BaseHolder getHolder(View contentView, int viewType) {
        EnterpriseHolder viewHolder=new EnterpriseHolder(contentView);
        return viewHolder;
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.recycleview_enterprisevideo_itemlayout;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final EnterpriseVideoInfo enterpriseVideoInfo= (EnterpriseVideoInfo) mInfos.get(position);

        if (holder instanceof EnterpriseHolder){
            EnterpriseHolder viewHolder= (EnterpriseHolder) holder;

            String imgeUrl=enterpriseVideoInfo.getImgeUrl();
            String title=enterpriseVideoInfo.getTitle();
            String introduction=enterpriseVideoInfo.getIntroduction();

            if (TextUtils.isEmpty(title)){
                title="";
            }
            if (TextUtils.isEmpty(introduction)){
                introduction="";
            }
            if (TextUtils.isEmpty(imgeUrl)){
                imgeUrl="no";
            }

            Picasso
                    .with(MovedimensionApp.getInstance().getApplicationContext())
                    .load(imgeUrl)
                    .error(R.drawable.video_shooteicon)
                    .placeholder(R.drawable.video_shooteicon)
                    .transform(new RoundTransform(mContext))
                    .into(viewHolder.videoImgView);

            int width = SmallFeatureUtils.getWindowWith((Activity) mContext);
            /**
             * 设置图片宽高
             */
            LinearLayout.LayoutParams imglayoutParams  =
                    new LinearLayout.LayoutParams(width/2,width/3);
            viewHolder.videoImgView.setLayoutParams(imglayoutParams);
            viewHolder.videoImgView.setScaleType(ImageView.ScaleType.FIT_XY);



            viewHolder.enterpriseNameTxt.setText(title);
            viewHolder.descripTxt.setText(introduction);

            viewHolder.enterprisevideoLin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null!=videoItemClickListener){
                        videoItemClickListener.itemEnterpriseClick(enterpriseVideoInfo);
                    }
                }
            });
        }
    }
    private EnterpriseVideoItemClickListener videoItemClickListener;

    public void setVideoItemClickListener(EnterpriseVideoItemClickListener videoItemClickListener) {
        this.videoItemClickListener = videoItemClickListener;
    }

    public interface EnterpriseVideoItemClickListener{
        void itemEnterpriseClick(EnterpriseVideoInfo enterpriseVideoInfo);
    }

    class EnterpriseHolder extends BaseHolder{

        @BindView(R.id.recycleview_enterprisevideo_enterprisevideoLin)
        LinearLayout enterprisevideoLin;

        @BindView(R.id.recycleview_enterprisevideo_videoImgView)
        ImageView videoImgView;
        @BindView(R.id.recycleview_enterprisevideo_enterpriseNameTxt)
        TextView enterpriseNameTxt;
        @BindView(R.id.recycleview_enterprisevideo_descripTxt)
        TextView descripTxt;

        public EnterpriseHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
        @Override
        public void setData(Object data, int position) {
        }
    }
}
