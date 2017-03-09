package groovy.better.com.groovytest;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import better.basenet.base.request.AbsRequest;
import better.basenet.base.request.IRequest;
import better.basenet.base.request.AbsRequestCallBack;
import better.basenet.okhttp.OkHttpRequest;
import groovy.better.com.groovytest.selector.demo.DemoActivity;

public class MainActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            ApplicationInfo ai = getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
            String umeng = (String) ai.metaData.get("UMENG_CHANNEL");
            Toast.makeText(this, umeng, Toast.LENGTH_LONG).show();
        } catch (PackageManager.NameNotFoundException e) {
        }

        Log.e("better", "onCreate");

        findViewById(R.id.test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent intent = new Intent(getApplicationContext(), LivenessCaptureActivity.class);
                // startActivity(intent);

            }
        });

        findViewById(R.id.textureView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> per = new ArrayList<>();
                List<String> names = new ArrayList<>();

                per.add(Manifest.permission.CAMERA);
                per.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);

                names.add("相机");
                names.add("存储");

//                PermissionUtils.checkPermissions(MainActivity.this, per, names, new Runnable() {
//                    @Override
//                    public void run() {
//                        Intent intent = new Intent(getApplicationContext(), LivenessCaptureActivity.class);
//                        startActivity(intent);
//                    }
//                });
            }
        });

        findViewById(R.id.hongbao).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hongbao();
            }
        });

        findViewById(R.id.selector).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DemoActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.net).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // 下载文件测试

                final String absolutePath = Environment.getExternalStorageDirectory().getAbsolutePath();
                File file = new File(absolutePath + "/DCIM/Camera/down.jpg");
                new OkHttpRequest.Builder().url("").callback(new AbsRequestCallBack() {
                    @Override
                    public void onSuccess(Object o) {
                        Log.e("okHttp success", o.toString());
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        Log.e("okHttp failure", e.toString());
                    }

                    @Override
                    public void onProgressUpdate(long contentLength, long bytesRead, boolean done) {
                        super.onProgressUpdate(contentLength, bytesRead, done);
                        Log.e("okHttp success", String.format("total:%s, already:%s, isDone: %s", contentLength, bytesRead, done));
                    }
                }).downFile(new Pair<String, File>(null, file)).build().request();
            }
        });
    }

    private void hongbao() {
//        RedPacketDialog.newInstance().show(getSupportFragmentManager(), "dialog");
    }


    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        PermissionUtils.requestResult(requestCode, permissions, grantResults, new Runnable() {
//            @Override
//            public void run() {
//                Intent intent = new Intent(getApplicationContext(), LivenessCaptureActivity.class);
//                startActivity(intent);
//            }
//        }, null);
    }

    private void showDetailDialog(String msg) {
//        final Dialog dialog = new Dialog(this);
//        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        final View view = inflater.inflate(R.layout.jdme_flow_center_full_screen_dialog, null);
//        TextView subject = (TextView) view.findViewById(R.id.tv_holiday_description_subject);
//        subject.setText("" + msg);


//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//
//        dialog.setContentView(view);
//
//        WindowManager.LayoutParams p = dialog.getWindow().getAttributes(); // 获取对话框当前的参数值
//        DisplayMetrics dm = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(dm);
//        p.height = (int) (dm.heightPixels * 1.0); // 高度设置为屏幕的比例
//        p.width = (int) (dm.widthPixels * 1.0); // 宽度设置为屏幕的比例
//        dialog.getWindow().setAttributes(p); // 设置生效
//
//        dialog.show();
//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (null != dialog && dialog.isShowing()) {
//                    dialog.dismiss();
//                }
//            }
//        });
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("saved", "zhaoyubetter");
        super.onSaveInstanceState(outState);
        Toast.makeText(this, "finish:" + isFinishing() + " destory: " + isDestroyed(), Toast.LENGTH_LONG).show();

        Log.e("better", "onSaveInstanceState " + "finish:" + isFinishing() + " destory: " + isDestroyed());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Toast.makeText(this, savedInstanceState.getString("saved"), Toast.LENGTH_LONG).show();

        Log.e("better", "onRestoreInstanceState");
        Log.e("better", "onRestoreInstanceState " + savedInstanceState.getString("saved"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (progressDialog != null) {
            progressDialog.dismiss();
        }

    }
}
