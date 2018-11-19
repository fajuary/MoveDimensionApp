package dongwei.fajuary.movedimensionapp.videoModel.adapter;

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
import dongwei.fajuary.movedimensionapp.R;
import dongwei.fajuary.movedimensionapp.util.PictureLoadUtil;
import dongwei.fajuary.movedimensionapp.videoModel.bean.UsersInfo;

/**
 * 作者：${神游风云（fajuary）} on 2018/2/1 14:48
 * 邮箱：fajuary@foxmail.com
 */

public class RankeListAdapter extends DefaultAdapter {

    public RankeListAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    public BaseHolder getHolder(View contentView, int viewType) {
        RankeListHolder viewHolder=new RankeListHolder(contentView);

        return viewHolder;
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.recycleview_rankelist_itemlayout;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//        PrizesInfo
        if (holder instanceof RankeListHolder){
            RankeListHolder viewHolder= (RankeListHolder) holder;
            UsersInfo usersInfo= (UsersInfo) mInfos.get(position);
            if (null!=usersInfo){
                String nickname=usersInfo.getNickname();

                if (TextUtils.isEmpty(nickname)){
                    nickname="匿名";
                }
                String imgUrl=usersInfo.getIcon();
                if (TextUtils.isEmpty(imgUrl)){
                    imgUrl="no";
                }
                viewHolder.personNameTxt.setText(nickname);
                viewHolder.rankingNumTxt.setText("NO."+(position+1));

                PictureLoadUtil.loadDefaultImg(viewHolder.personImg,imgUrl,R.mipmap.head_sortbg);
            }
        }
    }

    class RankeListHolder extends BaseHolder{

        @BindView(R.id.recycleview_rankelist_rankeitemlayout)
        LinearLayout rankeitemlayout;

        @BindView(R.id.recycleview_rankelist_personImg)
        ImageView personImg;
        @BindView(R.id.recycleview_rankelist_rankingNumTxt)
        TextView rankingNumTxt;
        @BindView(R.id.recycleview_rankelist_personNameTxt)
        TextView personNameTxt;

        public RankeListHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
        @Override
        public void setData(Object data, int position) {
        }
    }
}
