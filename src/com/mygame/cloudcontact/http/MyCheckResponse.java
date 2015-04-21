package com.mygame.cloudcontact.http;
 
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

/**
 * 通用的头部信息检查
 * 
 * @author Administrator
 * 
 */
public class MyCheckResponse extends BaseCheckResponse/* implements OnFinishListener */{
    public static final int UNAUTHORIZED = 401; // 未授权状态
    public static final int TOKEN_TIMEOUT = 421; // token失效,需重登录
                                                 // msg登录失效（1个月有效期）；
    public static final int LOGIN_INFO_ERROR = 431; // 另外一机上登录，msg:您的帐号已经另一台设备上登录了，如不是本人操作，请修改密码
//    private CommonDialog mDialog;
    private Context mContext;
    private Handler mUIHandler;

    public MyCheckResponse(Context context/*, TuokebaoSettings settings*/)
    {
        super();
        this.mContext = context;
        mUIHandler = new Handler(mContext.getMainLooper());
    }

    @Override
    protected void severRequestFailed(int status, String msg) throws SeverRequestException {
        Log.e("wuzhenlin", "status = " + status + ",  msg = " + msg);
        if (TOKEN_TIMEOUT == status || LOGIN_INFO_ERROR == status) {
            showDialog(msg);
        }

        // 抛出数据错误、
        super.severRequestFailed(status, msg);
    }

    private void showDialog(final String msg) {
        mUIHandler.post(new Runnable()
        {

            @Override
            public void run() {
                
            }
        });
    }

    @Override
    public void onConfirm(String msg) {
         
    }

    @Override
    public void onCancel(String msg) {
    }

//	@Override
//	public boolean onFinish(View view) {
//		return true;
//	}
//
//	@Override
//	public boolean onCancel(View view) {
//		return true;
//	}

}
