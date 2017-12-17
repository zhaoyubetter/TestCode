package test.better.com.leak.mvvm.one;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import test.better.com.leak.BR;
import test.better.com.leak.R;

/**
 * Created by zhaoyu on 2017/12/17.
 */

public class EmployeeAdapter extends RecyclerView.Adapter<BindingViewHolder> {

	private static final int VIEW_TYPE_ON = 1;
	private static final int VIEW_TYPE_OFF = 2;

	private final LayoutInflater inflater;
	private OnItemClickListener itemClickListner;
	private List<Employee> list;

	public interface OnItemClickListener {
		void onItemClick(Employee employee);
	}

	public EmployeeAdapter(Context context, OnItemClickListener itemClickListner) {
		this.inflater = LayoutInflater.from(context);
		this.itemClickListner = itemClickListner;
		list = new ArrayList<>();
	}

	@Override
	public BindingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		ViewDataBinding binding;
		if (viewType == VIEW_TYPE_ON) {
			binding = DataBindingUtil.inflate(inflater, R.layout.mvvm_item_employee_on, parent, false);
		} else {
			binding = DataBindingUtil.inflate(inflater, R.layout.mvvm_item_employee_off, parent, false);
		}
		return new BindingViewHolder(binding);
	}

	@Override
	public void onBindViewHolder(BindingViewHolder holder, int position) {
		final Employee ee = list.get(position);
		holder.getBinding().setVariable(BR.item, ee);
		holder.getBinding().executePendingBindings();        // 马上生效
		holder.itemView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (itemClickListner != null) {
					itemClickListner.onItemClick(ee);
				}
			}
		});
	}

	@Override
	public int getItemViewType(int position) {
		final Employee employee = list.get(position);
		return employee.isFired() ? VIEW_TYPE_OFF : VIEW_TYPE_ON;
	}

	@Override
	public int getItemCount() {
		return list.size();
	}

	public void add(Employee e) {
		int pos = new Random().nextInt(list.size()) + 1;
		list.add(pos, e);
		notifyItemInserted(pos);
	}

	public void addAll(List<Employee> list) {
		this.list.addAll(list);
	}

	public void remove() {
		if (!list.isEmpty()) {
			int pos = new Random().nextInt(list.size());
			list.remove(pos);
			notifyItemRemoved(pos);
		}
	}
}
