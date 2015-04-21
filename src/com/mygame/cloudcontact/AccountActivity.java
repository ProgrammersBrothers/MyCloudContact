package com.mygame.cloudcontact;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class AccountActivity extends Activity
{
	private TextView		_accountList;
	private AccountManager	_am;

	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.manage_accounts);

		_accountList = (TextView) findViewById(R.id.manage_accounts_accountlist);
		_am = AccountManager.get(this);

		Button newacc = (Button) findViewById(R.id.manage_accounts_newaccount);
		final Activity self = this;
		newacc.setOnClickListener(new OnClickListener() {
			public void onClick(View v)
			{
				_am.addAccount(getString(R.string.ACCOUNT_TYPE), null, null, null, self, new AccountManagerCallback<Bundle>() {
					public void run(AccountManagerFuture<Bundle> amfuture)
					{
						listAccounts();
					}
				}, null);
			}
		});
		listAccounts();
	}

	/* 列出所有  */
	private void listAccounts()
	{
		/* �õ�ָ�����͵��˻� */
		Account[] accounts = _am.getAccountsByType(getString(R.string.ACCOUNT_TYPE));
		_accountList.setText("联系人");
		for (Account account : accounts)
		{
			_accountList.setText(_accountList.getText().toString() + '\n' + account.name + " - " + account.type);
		}
	}
}
