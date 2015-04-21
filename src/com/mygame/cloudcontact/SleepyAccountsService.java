package com.mygame.cloudcontact;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class SleepyAccountsService extends Service
{
	private SleepyAccountAuthenticator	_saa;
	@Override
	public IBinder onBind(Intent intent)
	{
		IBinder ret = null;
		/*
		 * 在intent.getAction()的动作为android.accounts. AccountManager. ACTION_AUTHENTICATOR_INTENT时，
		 * 通过AccountAuthenticator的getIBinder方法返回一个IBinder
		 */
		if (intent.getAction().equals(android.accounts.AccountManager.ACTION_AUTHENTICATOR_INTENT))
			ret = getSleepyAuthenticator().getIBinder();
		return ret;
	}
	private SleepyAccountAuthenticator getSleepyAuthenticator()
	{
		if (_saa == null)
			_saa = new SleepyAccountAuthenticator(this);
		return _saa;
	}
}
