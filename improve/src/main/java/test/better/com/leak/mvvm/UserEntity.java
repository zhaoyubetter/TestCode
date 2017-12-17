package test.better.com.leak.mvvm;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class UserEntity {
	private String username;
	private String nickname;
	private int age;
	public String icon;

	public UserEntity() {
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public UserEntity(int age, String nickname, String username) {
		this.age = age;
		this.nickname = nickname;
		this.username = username;
	}

	@BindingAdapter("bind:icon")
	public static void getInternetImage(ImageView iv, String userface) {
		Glide.with(iv.getContext()).load(userface).into(iv);
	}
}