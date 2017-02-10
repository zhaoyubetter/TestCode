package better.com.rxandroidtest.improve.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import better.com.rxandroidtest.R;
import better.com.rxandroidtest.improve.model.AppInfoBean;

/**
 * Created by zhaoyu1 on 2017/2/7.
 */
public class MyAdapter1 extends BaseRecyclerViewAdapter<AppInfoBean> {
    @Override
    public int getItemLayoutId(int viewType) {
        if (viewType == AppInfoBean.TITLE_TYPE) {
            return R.layout.item_title_info;
        }
        return R.layout.item_app_info;
    }

    @Override
    public int getItemViewType(int position) {
        return data.get(position).appType;
    }

    @Override
    protected void onConvert(BaseRecyclerViewHolder holder, AppInfoBean item, int position) {

        final int itemViewType = holder.getItemViewType();
        if (itemViewType == AppInfoBean.TITLE_TYPE) {
            TextView title = holder.getView(R.id.title);
            title.setText(item.appName + "（" + item.count + "）");
        } else {
            ImageView icon = holder.getView(R.id.icon);
            TextView name = holder.getView(R.id.name);
            TextView pgName = holder.getView(R.id.packageName);

            icon.setImageDrawable(item.iconDrawable);
            name.setText(item.appName);
            pgName.setText(item.pgName);
        }
    }
}
