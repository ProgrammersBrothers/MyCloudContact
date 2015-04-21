package com.mygame.cloudcontact;

import android.content.Context;
import android.widget.Toast;

public class Tst {

	
	public static void toastL(String msg, Context context){
		Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
	}
	
	public static void toastS(String msg, Context context){
		Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
	}
}
