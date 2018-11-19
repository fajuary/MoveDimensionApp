package dongwei.fajuary.movedimensionapp.dialog;

/**
 * 作者：${神游风云（fajuary）} on 2018/2/12 08:20
 * 邮箱：fajuary@foxmail.com
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import dongwei.fajuary.movedimensionapp.R;

/**
 * 加载提醒对话框
 */
public class CustomDialog extends ProgressDialog
{
    public CustomDialog(Context context)
    {
        super(context);
    }

    public CustomDialog(Context context, int theme)
    {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        init(getContext());
    }

    private void init(Context context)
    {
        //设置不可取消，点击其他区域不能取消，实际中可以抽出去封装供外包设置
        setCancelable(false);
        setCanceledOnTouchOutside(false);

        setContentView(R.layout.load_dialog);
        TextView titleTxt= (TextView) findViewById(R.id.tv_load_dialog);//标题
        titleTxt.setText(loadTitle);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(params);
    }
    private String loadTitle="加载中";
    public void setLoadTitle(String loadTitle) {
        this.loadTitle = loadTitle;
    }

    @Override
    public void show()
    {
        super.show();
    }
}

