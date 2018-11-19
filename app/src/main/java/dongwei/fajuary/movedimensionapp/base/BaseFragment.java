package dongwei.fajuary.movedimensionapp.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dongwei.fajuary.dongweimoveview.utils.PreSaveUtil;


/**
 * Created by Administrator on 2017/5/4.
 */

public abstract class BaseFragment extends Fragment implements View.OnClickListener {
    private boolean isVisible = false;
    private boolean isInitView = false;
    private boolean isFirstLoad = true;

    private View convertView;
    private SparseArray<View> mViews;

    public abstract int getLayoutId();

    public abstract void initViews(View view);

    public abstract void initListener();

    public abstract void initData();

    public abstract void processClick(View view);
    public Context mContext;
    public Activity activity;
    public PreSaveUtil preSaveUtil;
    public String token;
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            isVisible = true;
            lazyLoad();
        } else {
            isVisible = false;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext=getActivity();
        activity= (Activity) mContext;
        preSaveUtil=new PreSaveUtil(mContext);
        token=preSaveUtil.getString("token");
        mViews = new SparseArray<>();
        convertView = inflater.inflate(getLayoutId(), container, false);

        initViews(convertView);

        isInitView = true;
        lazyLoad();
        return convertView;
    }

    @Override
    public void onClick(View view) {
        processClick(view);
    }


    private void lazyLoad() {
        if (!isFirstLoad || !isVisible || !isInitView) {
            //不加载数据
            return;
        }
        //加载数据
        initListener();
        initData();
        isFirstLoad = false;
    }


}
