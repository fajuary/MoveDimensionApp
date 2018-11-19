package dongwei.fajuary.movedimensionapp.personInfoModel.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import dongwei.fajuary.dongweimoveview.base.BaseHolder;
import dongwei.fajuary.dongweimoveview.base.DefaultAdapter;
import dongwei.fajuary.dongweimoveview.utils.SmallFeatureUtils;
import dongwei.fajuary.movedimensionapp.R;
import dongwei.fajuary.movedimensionapp.personInfoModel.bean.MyYearVideoInfo;
import dongwei.fajuary.movedimensionapp.util.PictureLoadUtil;

/**
 * 作者：${神游风云（fajuary）} on 2018/2/1 18:54
 * 邮箱：fajuary@foxmail.com
 */

public class VideoListAdapter  extends DefaultAdapter {

    public VideoListAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    public BaseHolder getHolder(View contentView, int viewType) {
        VideoListHolder viewHolder=new VideoListHolder(contentView);
        return viewHolder;
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.recycleview_videolist_itemlayout;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final MyYearVideoInfo yearVideoInfo= (MyYearVideoInfo) mInfos.get(position);
        Logger.e("yearVideoInfo"+yearVideoInfo.toString());

        if (holder instanceof VideoListHolder){
            VideoListHolder viewHolder= (VideoListHolder) holder;
            long addtimeLon=yearVideoInfo.getAddtime();


            String imgeUrl=yearVideoInfo.getImgeUrl();

            if (TextUtils.isEmpty(imgeUrl)){
                imgeUrl="no";
            }
            PictureLoadUtil.loadDefaultImg(viewHolder.videoshowImg,imgeUrl,R.mipmap.video_listbg);
            String addtimeStr= SmallFeatureUtils.getTimeYMDHMStr(String.valueOf(addtimeLon),"yyyy-MM-dd HH:mm:ss");
            viewHolder.videotimeTxt.setText(addtimeStr);

            String blessing=yearVideoInfo.getBlessing();
            if (TextUtils.isEmpty(blessing)){
                blessing="无";
            }
            viewHolder.videodescripTxt.setText(blessing);

            viewHolder.itemclickLin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null!=videoItemClickListener){
                        videoItemClickListener.itemClick(yearVideoInfo);
                    }
                }
            });
        }
    }

    public void setVideoItemClickListener(VideoItemClickListener videoItemClickListener) {
        this.videoItemClickListener = videoItemClickListener;
    }

    private VideoItemClickListener videoItemClickListener;
    public interface VideoItemClickListener{
        void itemClick(MyYearVideoInfo yearVideoInfo);
    }

    class VideoListHolder extends BaseHolder{

         @BindView(R.id.recycleview_videolist_videoshowImg)
         ImageView videoshowImg;
         @BindView(R.id.recycleview_videolist_videotimeTxt)
         TextView videotimeTxt;
         @BindView(R.id.recycleview_videolist_videodescripTxt)
         TextView videodescripTxt;
         @BindView(R.id.recycleview_videolist_itemclickLin)
         LinearLayout itemclickLin;

        public VideoListHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
        @Override
        public void setData(Object data, int position) {

        }
    }
}
