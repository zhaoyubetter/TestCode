package test.better.com.leak.mvvm.one;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

/**
 * Created by zhaoyu on 2017/12/17.
 */

public class Adapter extends RecyclerView.Adapter<BindingViewHolder> {

	private static final int VIEW_TYPE_1 = 1;
	private static final int VIEW_TYPE_2 = 2;

	private final LayoutInflater inflater;

	public interface OnItemClickListener {
		void onItemClick();
	}

	public Adapter(Context context) {
		this.inflater = LayoutInflater.from(context);
	}

	@Override
	public BindingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		return null;
	}

	@Override
	public void onBindViewHolder(BindingViewHolder holder, int position) {

	}

	@Override
	public int getItemCount() {
		return 0;
	}
}
