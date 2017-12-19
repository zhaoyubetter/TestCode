package test.better.com.leak.mvvm.three;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import test.better.com.leak.BR;

/**
 * Created by zhaoyu on 2017/12/19.
 */

public class FormModel extends BaseObservable {
	@Bindable
	private String name;
	@Bindable
	private String password;

	public FormModel(String name, String password) {
		this.name = name;
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		notifyPropertyChanged(BR.name);
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
		notifyPropertyChanged(BR.password);
	}
}
