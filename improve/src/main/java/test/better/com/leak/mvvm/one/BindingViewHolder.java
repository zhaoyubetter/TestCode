package test.better.com.leak.mvvm.one;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

/**
 * Created by zhaoyu on 2017/12/17.
 */

public class BindingViewHolder<T extends ViewDataBinding> extends RecyclerView.ViewHolder {

	private T mBinding;

	public BindingViewHolder(T binding) {
		super(binding.getRoot());
		this.mBinding = binding;
	}

	public T getBinding() {
		return this.mBinding;
	}
}
