package dongwei.fajuary.dongweimoveview.viewModel;

import android.content.Context;
import android.graphics.Paint;
import android.text.Html;
import android.util.AttributeSet;


/**
 * Created by Administrator on 2017/6/17.
 */

public class TimeTextView extends android.support.v7.widget.AppCompatTextView implements Runnable {
    Paint mPaint; //画笔,包含了画几何图形、文本等的样式和颜色信息

    private long[] times;

    private long mhour, mmin, msecond;//天，小时，分钟，秒

    private boolean run=false; //是否启动了

    public TimeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint=new Paint();
//        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TimeTextView);
//        array.recycle(); //一定要调用，否则这次的设定会对下次的使用造成影响
    }

    public TimeTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mPaint=new Paint();
//        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TimeTextView);
//        array.recycle(); //一定要调用，否则这次的设定会对下次的使用造成影响
    }

    public TimeTextView(Context context) {
        super(context);
    }

    public long[] getTimes() {
        return times;
    }

    public void setTimes(long[] times) {
        mmin = times[0];
        msecond = times[1];
    }

    /**
     * 倒计时计算
     */
    private void ComputeTime() {
        msecond--;
        if (msecond < 0) {
            mmin--;
            msecond = 59;
            if (mmin < 0) {
                mmin = 59;
            }

        }

    }

    public boolean isRun() {
        return run;
    }

    public void setRun(boolean run) {
        this.run = run;
    }

    @Override
    public void run() {
        //标示已经启动
        run=true;

        ComputeTime();

        String strTime= mmin+"分"+msecond+"秒";
        this.setText(Html.fromHtml(strTime));

        postDelayed(this, 1000);

    }

}
