package test.better.com.leak.custom_view.path;

import android.graphics.Color;
import android.graphics.Path;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import test.better.com.leak.R;

/**
 * Created by zhaoyu on 2017/3/27.
 */

public class PathAnim1Fragment extends Fragment {
	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_path_anim1, container, false);
		PathAnimView pathView1 = (PathAnimView) view.findViewById(R.id.pathAnim1);
		StoreHouseAnimView pathView2 = (StoreHouseAnimView) view.findViewById(R.id.pathAnim2);
		pathView1.setSourcePath(PathParserUtils.getPathFromArrayFloatList(StoreHousePath.getPath("path anim study")));

		PathAnimView pathView3 = (PathAnimView) view.findViewById(R.id.pathAnim3);

		pathView1.setAnimTime(5000).setFgColor(Color.RED).startAnim();//普通可xml预览path动画


		Path sPath = new Path();
		for (int i = 1; i < 20; i = i + 2) {
			sPath.moveTo(5, 5 * i);
			sPath.lineTo(100, 5 * i);
			sPath.moveTo(150, 5 * i);
			sPath.lineTo(300, 5 * i);
		}
		pathView2.setSourcePath(sPath);
		pathView2.setPathMaxLength(1200).setAnimTime(20000).startAnim();

		// view3
		sPath = new Path();
		sPath.moveTo(0, 0);
		//sPath.addCircle(50, 50, 60, Path.Direction.CW);
		sPath.addCircle(50, 50, 40, Path.Direction.CW);
		pathView3.setSourcePath(sPath);
		//代码示例 动态对path加工，通过Helper
		pathView3.setPathAnimHelper(new SysLoadAnimHelper(pathView3, pathView3.getSourcePath(), pathView3.getAnimPath()));
		//设置颜色
		pathView3.setBgColor(Color.WHITE).setFgColor(Color.RED).startAnim();
		//当然你可以自己拿到Paint，然后搞事情，我这里设置线条宽度

		return view;
	}
}
