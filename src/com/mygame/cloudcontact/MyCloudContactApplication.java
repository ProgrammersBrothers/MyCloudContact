package com.mygame.cloudcontact;

import android.app.Application;

import com.mygame.cloudcontact.http.FinalHttp;

public class MyCloudContactApplication extends Application {
	private FinalHttp mFinalHttp;
	private static MyCloudContactApplication instance;

	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
		// 初始化事件分发器
		mFinalHttp = new FinalHttp();
		// mFinalHttp.setReqeustHeader(new BaseReqeustHeader(instance));
		// mFinalHttp.setCheckResponseInterface(new BaseCheckResponse());
	}
	
	public static MyCloudContactApplication instance(){
		if(instance == null){
			instance = new MyCloudContactApplication();
		}
		return instance;
	}

	public FinalHttp getFinalHttp() {
		if(mFinalHttp == null){
			mFinalHttp = new FinalHttp();
		}
		return mFinalHttp;
	}
}
