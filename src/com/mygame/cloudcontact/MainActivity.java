package com.mygame.cloudcontact;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.mygame.cloudcontact.bean.AccountInfo;
import com.mygame.cloudcontact.bean.Urls;
import com.mygame.cloudcontact.http.AjaxCallBack;

public class MainActivity extends BaseActivity {

	private int UPDATE_DIAL = 100;
	EditText etUserName, etPwd;
	Button btnSubmit;
	private Button btnImport;
	private LinearLayout llLogin;
	protected String userId;
	ProgressDialog dialog;
	private Context context;
	
	private SharedPreferences sp;//保存用户名和密码
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		this.context = this;

		initView();
		setViewVisible(true);
	}

	private void initView() {
		llLogin = (LinearLayout) findViewById(R.id.llLogin);
		btnImport = (Button)findViewById(R.id.btnImport);
		btnImport.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				importContact();
//				startActivity(new Intent(v.getContext(), AccountActivity.class));
			}
		});
		
		 etUserName = (EditText)findViewById(R.id.etUserName);
		 etPwd = (EditText)findViewById(R.id.etPwd);
		 
		 sp = context.getSharedPreferences(context.getString(R.string.userInfo), Context.MODE_PRIVATE);
		 etUserName.setText(sp.getString(context.getString(R.string.userName), ""));  
		 etPwd.setText(sp.getString(context.getString(R.string.passWord), ""));  
		 btnSubmit = (Button)findViewById(R.id.btnSubmit);
		 
		 btnSubmit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String userName = etUserName.getText().toString().replace(" ", "");
				String pwd = etPwd.getText().toString().replace(" ", "");
				if(TextUtils.isEmpty(userName) || TextUtils.isEmpty(pwd)){
					Toast.makeText(v.getContext(), "用户名或密码为空", Toast.LENGTH_SHORT).show();
					return;
				} else {
					login(userName, pwd);
				}
			}
		});
	}
	
	private void setViewVisible(boolean b) {
		 if(b){
			btnImport.setVisibility(View.GONE);
			llLogin.setVisibility(View.VISIBLE);
		 } else {
			 btnImport.setVisibility(View.VISIBLE);
			 llLogin.setVisibility(View.GONE);
		 }
	}

	protected void importContact() {
		 // 请求联系人数据
		String requestUrl = Urls.server + "?buss=getMobile&userId=" + userId;
		dialog = new ProgressDialog(this);
		dialog.setMessage("获取联系人信息中...");
		dialog.show();
		getFinalHttp().get(requestUrl, new AjaxCallBack<String>(){

			@Override
			public void onStart() {
				super.onStart();
			}

			@Override
			public void onSuccess(String t) {
				super.onSuccess(t);
				try {
					Log.i("--tom", "ready ----------11111111111111111111111");
					dialog.setMessage("导入联系人中...");
					dialog.setProgress(0);
					dialog.setMax(100);
					MyImportAsycTask task = new MyImportAsycTask(dialog);
					task.execute(t);
//					importContactToGroup(data);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				// TODO Auto-generated method stub
				super.onFailure(t, errorNo, strMsg);
				dialog.cancel();
			}
		});
		
 	}
	
	
	private Activity getActivity(){
		return this;
	}

	
	Handler mHandler = new Handler(){


		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			if(msg.what == UPDATE_DIAL ){
				int progress = (Integer) msg.obj; 
//				dialog.setProgress(progress);
				dialog.setMessage("导入进度-->" + progress + "%");
			}
		}
		
	};

	
	class MyImportAsycTask extends AsyncTask<String, Integer, List<ContactsInfo>>{
		
				
		private MyImportAsycTask(ProgressDialog dial){
 			Log.i("--tom", "ready ---------- 2222222222222222222222222222222");
		}

		@Override
		protected List<ContactsInfo> doInBackground(String... params) {
			 
			JSONArray array;
			try {
				array = new JSONArray(params[0]);
				if (array != null) {
					List<ContactsInfo> data = new ArrayList<ContactsInfo>();
					for (int i = 0; i < array.length(); i++) {
						JSONObject obj = array.getJSONObject(i);
						ContactsInfo info = new ContactsInfo();
						info.setName(obj.getString("name"));
						info.setPhone(obj.getString("mobile"));
						AccountInfo ainfo = new AccountInfo();
						ainfo.setAccount_name(getActivity().getString(
								R.string.native_machine));
						info.setAccountInfo(ainfo);
						data.add(info);
					}
					Log.i("--tom", "ready import contacts -->" + params[0] );
					Log.i("--tom", "ready import contacts size()-->" + data.size() );

//					String acountName = data.get(0).getAccountInfo()
//							.getAccount_name();
//					long groupId = ContactsUtils.getGroupIdByGroupName(
//							acountName, context);
//					if (groupId == -1) {
//						// 创建群组之后，再添加
//						groupId = ContactsUtils.createGroup(acountName, context);
//					}
					for(int i = 0; i < data.size(); i += 500) {
						ArrayList<ContactsInfo> list = new ArrayList<ContactsInfo>(); 
						for(int j = i; j < i+500 && j < data.size(); j++){
							list.add(data.get(j));
						}
						List<ContactsInfo> list2 = ContactsUtils.addContacts(list, getActivity(), i);
						list.clear();
						Message msg = mHandler.obtainMessage();
						msg.what = UPDATE_DIAL;
						int progress = i / data.size() * 100; 
						Log.i("--tom", "progress ->" + progress);
						msg.obj = progress;
						mHandler.sendMessage(msg);
					}
				}
				} catch (Exception e) {
			}
			return null;
		}

		@Override
		protected void onPostExecute(List<ContactsInfo> result) {
			super.onPostExecute(result);
			if(dialog != null){
				dialog.cancel();
			}
		}
	}
	 
	private void login(String userName, String pwd) {
//		AjaxParams params = new AjaxParams();
//		params.put("buss", "reg");
//		params.put("username", userName);
//		params.put("password", pwd);
		String logRequestUrl = Urls.server + "?" + "buss=getUserid"
		+"&username=" + userName 
		+ "&password=" + pwd;
		
		final ProgressDialog dialog = new ProgressDialog(this);
		dialog.setMessage("登录中...");
		dialog.show();
		getFinalHttp().get(logRequestUrl, new AjaxCallBack<String>() {

			@Override
			public void onSuccess(String t) {
				super.onSuccess(t);
				parseData(t);
				dialog.cancel();
			}

			private void parseData(String t) {
				try {
					JSONObject json = new JSONObject(t);
					if("00".equals(json.get("respCode"))) {
						Tst.toastS("登录成功!", getApplicationContext());
						userId = json.getString("respMsg");
						Log.i("--tom", "userId:" + userId);
						onSaveContent();
						setViewVisible(false);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(Throwable t, int errorNo,
					String strMsg) {
				super.onFailure(t, errorNo, strMsg);
				Toast.makeText(MainActivity.this, strMsg, Toast.LENGTH_SHORT).show();
				dialog.cancel();
			}
			
		});
	}
	 
	//保存用户名和密码
	protected void onSaveContent() 
	{ 
		String usernameContent = etUserName.getText().toString();
		String passwordContent = etPwd.getText().toString();
		sp.edit().putString(context.getString(R.string.userName), usernameContent).commit();
		sp.edit().putString(context.getString(R.string.passWord), passwordContent).commit();
	}
	//清除用户名和密码
	protected void onClearContent() 
	{
		sp.edit().putString(context.getString(R.string.userName), "").commit();
		sp.edit().putString(context.getString(R.string.passWord), "").commit();
	}
}
