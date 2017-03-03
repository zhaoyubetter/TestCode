package better.com.commomlib.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaoyu1 on 2017/2/7.
 */
public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<BaseRecyclerViewHolder> {

    /**
     * 独立维护数据集合
     */
    protected List<T> data;

    public BaseRecyclerViewAdapter() {
    }

    public void replaceData(List<T> list) {
        this.data = new ArrayList<>(list);
        notifyDataSetChanged();
    }

    public BaseRecyclerViewAdapter(List<T> datas) {
        this.data = new ArrayList<>(datas);
    }

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return BaseRecyclerViewHolder.getViewHolder(parent.getContext(), getItemLayoutId(viewType), parent);
    }

    /**
     * 类型对应的布局资源
     *
     * @param viewType
     * @return
     */
    public abstract int getItemLayoutId(final int viewType);

    @Override
    public void onBindViewHolder(BaseRecyclerViewHolder holder, int position) {
        onConvert(holder, data.get(position), position);
    }

    protected abstract void onConvert(final BaseRecyclerViewHolder holder, T item, final int position);

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }
}
