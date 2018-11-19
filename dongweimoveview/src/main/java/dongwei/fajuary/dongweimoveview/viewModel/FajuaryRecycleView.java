package dongwei.fajuary.dongweimoveview.viewModel;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created 张朋飞 on 2016/8/18.
 * user 864598192
 */
public class FajuaryRecycleView extends RecyclerView{
    public FajuaryRecycleView(Context context) {
        super(context);
    }

    public FajuaryRecycleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public FajuaryRecycleView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
//        super.onMeasure(widthSpec, heightSpec);
        int expandSpec = View.MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                      View.MeasureSpec.AT_MOST);
        super.onMeasure(widthSpec, expandSpec);
    }
}
