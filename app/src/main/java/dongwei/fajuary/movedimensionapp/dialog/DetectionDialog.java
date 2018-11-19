package dongwei.fajuary.movedimensionapp.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aliyun.common.utils.DeviceUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import dongwei.fajuary.movedimensionapp.R;

/**
 * 作者：${神游风云（fajuary）} on 2018/2/3 14:09
 * 邮箱：fajuary@foxmail.com
 * 检测是否登录了
 */

public class DetectionDialog {


    public static class Builder {
        private AlertDialog.Builder alertBuilder;
        private AlertDialog alertDialog;
        private View contentView;

        private Activity mActivity;
        private ViewHolder mViewHolder;

        public Builder(Activity mActivity) {
            this.mActivity = mActivity;
            initView();
        }

        public Builder setTitle(String titleMsg) {//标题
            mViewHolder.titleTxt.setText(titleMsg);
            return this;
        }

        public Builder setMessage(String message) {//描述
            mViewHolder.messageTxt.setText(message);
            return this;
        }

        public Builder setCancleMsg(String cancleMsg, final View.OnClickListener listener) {
            mViewHolder.cancleTxt.setText(cancleMsg);
            mViewHolder.cancleTxt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismissDialog();
                    if (listener != null) {
                        listener.onClick(view);
                    }
                }
            });
            return this;
        }

        public Builder setConfimMsg(String confirmMsg, final View.OnClickListener listener) {
            mViewHolder.confirmTxt.setText(confirmMsg);
            mViewHolder.confirmTxt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismissDialog();
                    if (listener != null) {
                        listener.onClick(view);
                    }
                }
            });
            return this;
        }

        private void initView() {
            alertBuilder = new AlertDialog.Builder(mActivity, R.style.AlertDialog);
            contentView = LayoutInflater.from(mActivity).inflate(R.layout.dialog_whether_itemlayout, null);
            mViewHolder = new ViewHolder(contentView);
//            alertBuilder.setb
            alertBuilder.setView(contentView);
            alertBuilder.setCancelable(true);
            alertDialog = alertBuilder.create();

        }

        public AlertDialog create() {
            return alertDialog;
        }

        /**
         * 显示
         */
        public void showDialog() {
            if (null != alertDialog) {
                alertDialog.show();
                //此处设置位置窗体大小
                alertDialog.getWindow().setLayout(DeviceUtils.dip2px(mActivity,300), LinearLayout.LayoutParams.WRAP_CONTENT);
            }
        }

        /**
         * 消失
         */
        public void dismissDialog() {
            if (null != alertDialog && alertDialog.isShowing()) {
                alertDialog.dismiss();
            }
        }

        class ViewHolder {
            @BindView(R.id.dialog_whetherlayout_titleTxt)
            TextView titleTxt;//标题
            @BindView(R.id.dialog_whetherlayout_messageTxt)
            TextView messageTxt;//描述消息
            @BindView(R.id.dialog_whetherlayout_cancleTxt)
            TextView cancleTxt;//取消
            @BindView(R.id.dialog_whetherlayout_confirmTxt)
            TextView confirmTxt;//确定

            public ViewHolder(View contentView) {
                ButterKnife.bind(this, contentView);
            }
        }
    }
}
