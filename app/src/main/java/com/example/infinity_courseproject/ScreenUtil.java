package com.example.infinity_courseproject;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import java.lang.reflect.Field;

public class ScreenUtil {

	public static DisplayMetrics getDisPlayMetrics(Context context) {
		DisplayMetrics metric = new DisplayMetrics();
		if (null != context) {
			((Activity) context).getWindowManager().getDefaultDisplay()
					.getMetrics(metric);
		}
		return metric;
	}

	public static int getScreenWidth(Context context) {
		int width = getDisPlayMetrics(context).widthPixels;
		return width;
	}

	public static int getScreenHeight(Context context) {
		int height = getDisPlayMetrics(context).heightPixels;
		return height;
	}


	public static float getDensity(Context context) {
		float density = getDisPlayMetrics(context).density;
		return density;
	}

	public static int getDensityDpi(Context context) {
		int densityDpi = getDisPlayMetrics(context).densityDpi;
		return densityDpi;
	}

	public static int getStatusBarHeight(Context context){
		Class<?> c = null;
		Object obj = null;
		Field field = null;
		int x = 0, statusBarHeight = 0;
		try {
			c = Class.forName("com.android.internal.R$dimen");
			obj = c.newInstance();
			field = c.getField("status_bar_height");
			x = Integer.parseInt(field.get(obj).toString());
			statusBarHeight = context.getResources().getDimensionPixelSize(x);
			Log.v("@@@@@@", "the status bar height is : " + statusBarHeight);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return statusBarHeight;
	}

	public static void setBackgroundAlpha(Activity activity, float bgAlpha) {
		WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
		lp.alpha = bgAlpha; //0.0f-1.0f
		activity.getWindow().setAttributes(lp);
	}
}
