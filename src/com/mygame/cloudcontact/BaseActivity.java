package com.mygame.cloudcontact;

import android.app.Activity;

import com.mygame.cloudcontact.http.FinalHttp;

public class BaseActivity extends Activity{

	MyCloudContactApplication application;
	
	protected FinalHttp getFinalHttp(){
		FinalHttp http = MyCloudContactApplication.instance().getFinalHttp();
		return http;
	}
}
