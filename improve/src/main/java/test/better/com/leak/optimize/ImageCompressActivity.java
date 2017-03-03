package test.better.com.leak.optimize;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import better.com.commomlib.utils.BitmapUtils;
import test.better.com.leak.R;

/**
 * 压缩图片 BitmapFactory
 * Created by zhaoyu on 2017/2/10.
 */
public class ImageCompressActivity extends AppCompatActivity {


	private ImageView zoom;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_zoom_image);
		zoom = (ImageView) findViewById(R.id.zoom);

		final Bitmap bitmap = BitmapUtils.compressBitmap(getApplicationContext(), R.mipmap.image, 200, 200);
		zoom.setImageBitmap(bitmap);
	}
}
