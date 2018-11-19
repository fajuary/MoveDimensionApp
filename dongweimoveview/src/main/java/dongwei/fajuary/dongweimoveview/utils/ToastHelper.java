package dongwei.fajuary.dongweimoveview.utils;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import dongwei.fajuary.dongweimoveview.R;


/**
 * Created by wang.song on 15/6/19.
 * Toast管理类
 */
public final class ToastHelper {

	private static Toast toast = null;

	final public static void showTextToast(Context ctx, int resId) {
		LayoutInflater inflater = (LayoutInflater) ctx
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rootView = inflater.inflate(R.layout.toast_custom, null);
		TextView text = (TextView) rootView.findViewById(R.id.toast_text);
		text.setTextColor(Color.WHITE);
		text.setText(resId);
		if (toast == null) {
			toast = new Toast(ctx.getApplicationContext());
			toast.setDuration(Toast.LENGTH_SHORT);
			toast.setView(rootView);
			toast.getView().setBackgroundResource(R.drawable.toast_bg);
			toast.getView().setPadding(20, 20, 20, 20);
		} else {
			toast.setView(rootView);
			toast.getView().setBackgroundResource(R.drawable.toast_bg);
			toast.getView().setPadding(20, 20, 20, 20);
		}
		toast.show();
	}

	final public static  void showTextToast(Context ctx, String msg) {
		LayoutInflater inflater = (LayoutInflater) ctx
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rootView = inflater.inflate(R.layout.toast_custom, null);
		TextView text = (TextView) rootView.findViewById(R.id.toast_text);
		text.setTextColor(Color.WHITE);
		text.setText(msg);
		if (toast == null) {
			toast = new Toast(ctx.getApplicationContext());
			toast.setDuration(Toast.LENGTH_LONG);
			toast.setView(rootView);
			toast.getView().setBackgroundResource(R.drawable.toast_bg);
			toast.getView().setPadding(20, 20, 20, 20);
		} else {
			toast.setView(rootView);
			toast.getView().setBackgroundResource(R.drawable.toast_bg);
			toast.getView().setPadding(20, 20, 20, 20);
		}
		toast.show();
	}
	
	final public static  void showTextToastAsShort(Context ctx, String msg) {
		LayoutInflater inflater = (LayoutInflater) ctx
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rootView = inflater.inflate(R.layout.toast_custom, null);
		TextView text = (TextView) rootView.findViewById(R.id.toast_text);
		text.setTextColor(Color.WHITE);
		text.setText(msg);
		if (toast == null) {
			toast = new Toast(ctx.getApplicationContext());
			toast.setDuration(Toast.LENGTH_SHORT);
			toast.setView(rootView);
			toast.getView().setBackgroundResource(R.drawable.toast_bg);
		} else {
			toast.setView(rootView);
			toast.getView().setBackgroundResource(R.drawable.toast_bg);
		}
		toast.show();
	}


}
