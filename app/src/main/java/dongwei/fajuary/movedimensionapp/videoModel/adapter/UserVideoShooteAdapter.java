package dongwei.fajuary.movedimensionapp.videoModel.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import dongwei.fajuary.dongweimoveview.base.BaseHolder;
import dongwei.fajuary.dongweimoveview.base.DefaultAdapter;
import dongwei.fajuary.dongweimoveview.utils.SmallFeatureUtils;
import dongwei.fajuary.movedimensionapp.R;
import dongwei.fajuary.movedimensionapp.appview.CircleImageTransformation;
import dongwei.fajuary.movedimensionapp.appview.RoundTransform;
import dongwei.fajuary.movedimensionapp.base.MovedimensionApp;
import dongwei.fajuary.movedimensionapp.videoModel.bean.UserVideoInfo;

/**
 * 作者：${神游风云（fajuary）} on 2018/2/11 18:58
 * 邮箱：fajuary@foxmail.com
 */

public class UserVideoShooteAdapter extends DefaultAdapter {


    public UserVideoShooteAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    public BaseHolder getHolder(View contentView, int viewType) {
        UserVideoHolder viewHolder=new UserVideoHolder(contentView);
        return viewHolder;
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.recycleview_uservideo_itemlayout;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final UserVideoInfo userVideoInfo= (UserVideoInfo) mInfos.get(position);

        if (holder instanceof UserVideoHolder){
            UserVideoHolder videoHolder= (UserVideoHolder) holder;

            String blessing=userVideoInfo.getBlessing();
            if (TextUtils.isEmpty(blessing)){
                blessing="";
            }
            videoHolder.videoTitleTxt.setText(blessing);
            String nickname=userVideoInfo.getNickname();
            if (TextUtils.isEmpty(nickname)){
                nickname="";
            }
            videoHolder.videousernameTxt.setText(nickname);
            String imgeUrl=userVideoInfo.getImgeUrl();
            if (TextUtils.isEmpty(imgeUrl)){
                imgeUrl="no";
            }

            /**
             * 设置图片宽高
             */
            int width = SmallFeatureUtils.getWindowWith((Activity) mContext);

            /**
             * 设置图片宽高
             */
            FrameLayout.LayoutParams imglayoutParams  =
                    new FrameLayout.LayoutParams(width/2,2*width/3);
//            videoHolder.uservideoLin.setLayoutParams(imglayoutParams);

            videoHolder.videoImgView.setLayoutParams(imglayoutParams);
            videoHolder.videoImgView.setScaleType(ImageView.ScaleType.FIT_XY);

//            recycleview_uservideo_uservideoLin
            Picasso
                    .with(MovedimensionApp.getInstance().getApplicationContext())
                    .load(imgeUrl)
                    .error(R.drawable.video_shooteicon)
                    .placeholder(R.drawable.video_shooteicon)
                    .transform(new RoundTransform(mContext))
                    .into(videoHolder.videoImgView);

            String iconUrl=userVideoInfo.getIcon();
            if (TextUtils.isEmpty(iconUrl)){
                iconUrl="no";
            }
            Picasso
                    .with(MovedimensionApp.getInstance().getApplicationContext())
                    .load(iconUrl)
                    .error(R.drawable.default_personimg)
                    .placeholder(R.drawable.default_personimg)
                    .transform(new CircleImageTransformation())
                    .into(videoHolder.videouserImg);

            videoHolder.uservideoLin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null!=videoItemClickListener){
                        videoItemClickListener.itemUserClick(userVideoInfo);
                    }
                }
            });
        }
    }
    private UserVideoItemClickListener videoItemClickListener;

    public void setVideoItemClickListener(UserVideoItemClickListener videoItemClickListener) {
        this.videoItemClickListener = videoItemClickListener;
    }

    public interface UserVideoItemClickListener{
        void itemUserClick(UserVideoInfo userVideoInfo);
    }


    class UserVideoHolder extends BaseHolder{

        @BindView(R.id.recycleview_uservideo_uservideoLin)
        FrameLayout uservideoLin;

        @BindView(R.id.recycleview_uservideo_videoImgView)
        ImageView videoImgView;

        @BindView(R.id.recycleview_uservideo_videoTitleTxt)
        TextView videoTitleTxt;

        @BindView(R.id.recycleview_uservideo_videouserImg)
        ImageView videouserImg;

        @BindView(R.id.recycleview_uservideo_videousernameTxt)
        TextView videousernameTxt;

        public UserVideoHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
        @Override
        public void setData(Object data, int position) {
        }
    }
}
