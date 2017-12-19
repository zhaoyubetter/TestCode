package test.better.com.leak.mvvm.two;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by zhaoyu on 2017/12/17.
 */
public class BindingAdapterDemo {
	@BindingAdapter({"app:imageUrl", "app:placeHolder"})  // 后者没找到支持 mipmap 的方法
	public static void loadImageFromUrl(ImageView iv, String url, Drawable drawable) {
		Glide.with(iv.getContext()).load(url).placeholder(drawable).into(iv);
	}
}
