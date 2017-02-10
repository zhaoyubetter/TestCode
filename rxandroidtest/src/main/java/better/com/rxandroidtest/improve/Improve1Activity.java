package better.com.rxandroidtest.improve;

import android.app.ProgressDialog;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import better.com.rxandroidtest.R;
import better.com.rxandroidtest.improve.adapter.MyAdapter1;
import better.com.rxandroidtest.improve.model.AppInfoBean;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by zhaoyu1 on 2017/2/7.
 */
public class Improve1Activity extends AppCompatActivity {

	RecyclerView recyclerView;
	ProgressDialog progressDialog;
	private MyAdapter1 mAdapter;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_improve_1);
		recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		mAdapter = new MyAdapter1();
		recyclerView.setAdapter(mAdapter);
		getData();
	}

	private void getData() {
		Observable.create(new Observable.OnSubscribe<List<ApplicationInfo>>() {
			@Override
			public void call(Subscriber<? super List<ApplicationInfo>> subscriber) {
				subscriber.onStart();
				// 任务1，所有安装的App集合
				List<ApplicationInfo> listAppcations = getPackageManager().getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);
				Collections.sort(listAppcations, new ApplicationInfo.DisplayNameComparator(getPackageManager()));// 排序
				SystemClock.sleep(1500);
				subscriber.onNext(listAppcations);
				subscriber.onCompleted();
			}
		}).map(new Func1<List<ApplicationInfo>, List<AppInfoBean>>() {
				   // 任务2：分组App
				   @Override
				   public List<AppInfoBean> call(List<ApplicationInfo> applicationInfos) {
					   List<AppInfoBean> list = new ArrayList<>();
					   List<AppInfoBean> sysApps = getAppInfoList(AppInfoBean.SYSTEM_APP);
					   List<AppInfoBean> thirdApps = getAppInfoList(AppInfoBean.THIRD_APP);
					   List<AppInfoBean> sdApps = getAppInfoList(AppInfoBean.SDCARD_APP);
					   List<AppInfoBean> otherApps = getAppInfoList(AppInfoBean.OTHER_APP);

					   for (ApplicationInfo info : applicationInfos) {
						   AppInfoBean app = new AppInfoBean();
						   app.iconDrawable = info.loadIcon(getPackageManager());
						   app.appName = (String) info.loadLabel(getPackageManager());
						   app.pgName = info.packageName;

						   // 系统程序
						   if ((info.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
							   app.appType = AppInfoBean.SYSTEM_APP;
							   sysApps.add(app);
						   } else if ((info.flags & ApplicationInfo.FLAG_SYSTEM) <= 0) {
							   app.appType = AppInfoBean.THIRD_APP;
							   thirdApps.add(app);
						   } else if ((info.flags & ApplicationInfo.FLAG_EXTERNAL_STORAGE) != 0) {
							   app.appType = AppInfoBean.SDCARD_APP;
							   sdApps.add(app);
						   } else {
							   app.appType = AppInfoBean.OTHER_APP;
							   otherApps.add(app);
						   }
					   }

					   sysApps.get(0).count = sysApps.size() > 0 ? sysApps.size() - 1 : 0;
					   thirdApps.get(0).count = thirdApps.size() > 0 ? thirdApps.size() - 1 : 0;
					   sdApps.get(0).count = sdApps.size() > 0 ? sdApps.size() - 1 : 0;
					   otherApps.get(0).count = otherApps.size() > 0 ? otherApps.size() - 1 : 0;

					   list.addAll(sysApps);
					   list.addAll(thirdApps);
					   list.addAll(sdApps);
					   list.addAll(otherApps);

					   return list;
				   }
			   }
		).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Subscriber<List<AppInfoBean>>() {
					@Override
					public void onStart() {
						super.onStart();
						if (progressDialog == null) {
							progressDialog = new ProgressDialog(Improve1Activity.this);
						}
						progressDialog.show();
					}

					@Override
					public void onCompleted() {
						//if(Improve1Activity.this != null && !isFinishing()) {
							progressDialog.dismiss();
						//}
					}
					@Override
					public void onError(Throwable e) {
					}
					@Override
					public void onNext(List<AppInfoBean> listAppcations) {
						if (listAppcations != null && listAppcations.size() > 0) {
							mAdapter.replaceData(listAppcations);
						}
					}

				});
	}

	/**
	 * 返回 list 并设置好标题
	 *
	 * @param type
	 * @return
	 */
	private List<AppInfoBean> getAppInfoList(int type) {
		List<AppInfoBean> list = new ArrayList<>(20);
		AppInfoBean headInfo = new AppInfoBean();
		headInfo.appType = AppInfoBean.TITLE_TYPE;
		switch (type) {
			case AppInfoBean.SYSTEM_APP:
				headInfo.appName = "系统应用";
				break;
			case AppInfoBean.THIRD_APP:
				headInfo.appName = "第三方应用";
				break;
			case AppInfoBean.SDCARD_APP:
				headInfo.appName = "SD中应用";
				break;
			case AppInfoBean.OTHER_APP:
				headInfo.appName = "其他应用";
				break;
		}
		list.add(headInfo);
		return list;
	}


	@Override
	protected void onStart() {
		super.onStart();
		if (progressDialog == null) {
			progressDialog = new ProgressDialog(this);
		}
	}
}
