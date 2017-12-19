package test.better.com.leak.mvvm.one;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableBoolean;

import test.better.com.leak.BR;


/**
 * Created by zhaoyu on 2017/12/17.
 */
public class Employee extends BaseObservable {
	private String lastName;
	private String firstName;
	public String icon;
	/**
	 * 使用属性名绑定
	 */
	private ObservableBoolean isFired = new ObservableBoolean(false);

	public Employee(String lastName, String firstName, boolean isFired) {
		this.lastName = lastName;
		this.firstName = firstName;
		this.isFired = new ObservableBoolean(isFired);
	}

	@Bindable
	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
		notifyPropertyChanged(BR.lastName);
	}

	@Bindable
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
		notifyPropertyChanged(BR.firstName);
	}

	public boolean isFired() {
		return isFired.get();
	}

	public void setFired(boolean fired) {
		isFired = new ObservableBoolean(fired);
		notifyChange();     // 刷新所有
	}
}
