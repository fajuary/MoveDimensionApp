package dongwei.fajuary.movedimensionapp.personInfoModel.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import dongwei.fajuary.dongweimoveview.base.BaseHolder;
import dongwei.fajuary.dongweimoveview.base.DefaultAdapter;
import dongwei.fajuary.movedimensionapp.R;
import dongwei.fajuary.movedimensionapp.personInfoModel.bean.SpotFabuloussInfo;

/**
 * 作者：${神游风云（fajuary）} on 2018/2/5 16:07
 * 邮箱：fajuary@foxmail.com
 * 赞列表
 */

public class PraiseListAdapter extends DefaultAdapter {

    public PraiseListAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    public BaseHolder getHolder(View contentView, int viewType) {
        PraiseListHolder viewHolder=new PraiseListHolder(contentView);
        return viewHolder;
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.recycleview_praiselist_itemlayout;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof PraiseListHolder){
            PraiseListHolder viewHolder= (PraiseListHolder) holder;

            SpotFabuloussInfo fabuloussInfo= (SpotFabuloussInfo) mInfos.get(position);

            if (null!=fabuloussInfo){

                int number=fabuloussInfo.getNumber();//数量
                String spotFabulous=fabuloussInfo.getSpotFabulous();//点赞者

                if (TextUtils.isEmpty(spotFabulous)){
                    spotFabulous="匿名";
                }

                viewHolder.sendnameTxt.setText(spotFabulous+" 送 ");

                viewHolder.praiseTypeNumTxt.setText(number+" 个 ");


            }
        }
    }

    class  PraiseListHolder extends BaseHolder{


        @BindView(R.id.recycleview_praiselistitem_sendnameTxt)
        TextView sendnameTxt;
        @BindView(R.id.recycleview_praiselistitem_praiseTypeNumTxt)
        TextView praiseTypeNumTxt;

        public PraiseListHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
        @Override
        public void setData(Object data, int position) {
        }
    }
}
