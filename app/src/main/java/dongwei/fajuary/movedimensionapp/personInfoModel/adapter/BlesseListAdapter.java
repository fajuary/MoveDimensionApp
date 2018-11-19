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
import dongwei.fajuary.movedimensionapp.personInfoModel.bean.GiftgiveVoinfo;

/**
 * 作者：${神游风云（fajuary）} on 2018/2/5 16:06
 * 邮箱：fajuary@foxmail.com
 * 福列表适配器
 */

public class BlesseListAdapter extends DefaultAdapter {

    public BlesseListAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    public BaseHolder getHolder(View contentView, int viewType) {
        BlesseListHolder viewHolder=new BlesseListHolder(contentView);
        return viewHolder;
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.recycleview_blesselist_itemlayout;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof BlesseListHolder){
            BlesseListHolder viewHolder= (BlesseListHolder) holder;

            GiftgiveVoinfo giftgiveVoinfo= (GiftgiveVoinfo) mInfos.get(position);

            if (null!=giftgiveVoinfo){
                String giverName=giftgiveVoinfo.getGiverName();
                String giftName=giftgiveVoinfo.getGiftName();
                int number=giftgiveVoinfo.getNumber();
                int spotLaud=giftgiveVoinfo.getSpotLaud();

                if (TextUtils.isEmpty(giverName)){
                    giverName="匿名";
                }
                if (TextUtils.isEmpty(giftName)){
                    giftName="礼物";
                }
                viewHolder.sendnameTxt.setText(giverName+"  赠送");
                viewHolder.blesseTypeNumTxt.setText(number+"个"+giftName);
                viewHolder.blesseNumTxt.setText(" = "+spotLaud+"个祝福");

            }
        }

    }

    class  BlesseListHolder extends BaseHolder{


        @BindView(R.id.recycleview_blesselistitem_sendnameTxt)
        TextView sendnameTxt;
        @BindView(R.id.recycleview_blesselistitem_blesseTypeNumTxt)
        TextView blesseTypeNumTxt;
        @BindView(R.id.recycleview_blesselistitem_blesseNumTxt)
        TextView blesseNumTxt;

        public BlesseListHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
        @Override
        public void setData(Object data, int position) {
        }
    }
}
