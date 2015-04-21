package com.mygame.cloudcontact.http;

import org.apache.http.HttpRequest;

import android.content.Context;
import android.text.TextUtils;


public class MyRequestHeader extends BaseReqeustHeader{
//    private TuokebaoSettings settings;

    public MyRequestHeader(Context context/*, TuokebaoSettings settings*/)
    {
        super(context);
//        this.settings = settings;
    }

    @Override
    public String getUid() {
		return null;
    }

    public static String getToken() {
//		return TuokebaoApplication.getInstance().getUser().getToken();
    	return null;
    }

    @Override
    public String getLongitude() {
		return null;
    }

    @Override
    public String getLatitude() {
		return null;
    }

    @Override
    public String getPushToken() {
		return null;
    }

    @Override
    public String getSex() {
		return null;
    }

    @Override
    public String getLang() {
		return null;
    }
    
	public static String getUserName() {
//		return TuokebaoApplication.getInstance().getUser().getUserName();
		return null;
	}
    
    public static void addHeaders(HttpRequest request, AbsReqeustHeader headers) {
		if (!TextUtils.isEmpty(getUserName())) {
			request.addHeader("p", getUserName());
		}
		if (!TextUtils.isEmpty(getToken())) {
			request.addHeader("token", getToken());
		}
    }

}
