package better.com.commomlib.utils;

import android.app.Activity;
import android.view.View;

import java.lang.reflect.Field;

import better.com.commomlib.view.annotation.ViewInject;

/**
 * 注入工具类
 * Created by zhaoyu on 2017/2/24.
 */
public final class ViewUtils {

	public static void inject(Object object, View view) {
		injectObject(object, new ViewFinder(view));
	}

	public static void inject(Activity activity) {
		injectObject(activity, new ViewFinder(activity));
	}

	private static void injectObject(Object object, ViewFinder finder) {
		try {
			final Field[] fields = object.getClass().getDeclaredFields();
			for(Field f : fields) {
				final ViewInject viewInject = f.getAnnotation(ViewInject.class);
				if(viewInject != null) {
					View view = finder.findViewById(viewInject.value());
					if(view != null) {
						f.setAccessible(true);
						f.set(object, view);
					}
				}
			}
		} catch (Throwable e) {
			LogUtils.e(e.toString(), e);
		}
	}


	private static class ViewFinder {

		private View mView;
		private Activity mActivity;

		public ViewFinder(View view) {
			this.mView = view;
		}

		public ViewFinder(Activity activity) {
			this.mActivity = activity;
		}

		public View findViewById(int resId) {
			return mActivity != null ? mActivity.findViewById(resId) : mView.findViewById(resId);
		}
	}
}
