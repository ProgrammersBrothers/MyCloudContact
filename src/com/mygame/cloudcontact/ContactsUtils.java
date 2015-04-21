package com.mygame.cloudcontact;

import java.util.ArrayList;
import java.util.List;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.net.Uri;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.text.TextUtils;
import android.util.Log;

public class ContactsUtils {

	

	/**
	 * 添加群组联系人
	 * @param c 上下文
	 * @param groupid 群组id
	 * @param list 添加的联系人列表，必须包含raw_contact_id
	 */
	public static void addGroupContact(Context c, String groupid, List<ContactsInfo> list) {
        ContentValues values = new ContentValues();
        ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
        for (ContactsInfo item : list) {
            long raw_contact_id = item.getRaw_contact_id();
            values.put("raw_contact_id", raw_contact_id);
            values.put("mimetype", "vnd.android.cursor.item/group_membership");
            values.put("data1", groupid);
            ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValues(values).build());
        }
        try {
            c.getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (OperationApplicationException e) {
            e.printStackTrace();
        }
    }

	

	/**
	 * 判断账户类型getString(R.string.ACCOUNT_TYPE)账户中是否有指定账户名
	 * @param accountName
	 * @param c
	 * @return
	 */
	public static boolean hasAccount(String accountName, Context c) {
		AccountManager _am = AccountManager.get(c);
		Account[] accounts = _am.getAccountsByType(c.getString(R.string.ACCOUNT_TYPE));
		if(accounts != null && accounts.length > 0){
			for(Account acount : accounts){
				if(accountName.equals(acount.name)){
					return true;
				}
			}
		}
		return false;
	}
	
	
	// 创建新联系人账户
	public static boolean createAccount(String accountName, Context c){
		
        return true;
	}


	public static boolean hasGroup(String accountName, Context c) {
		 
		return false;
	}
		
	 
	/**
	 * 添加联系人列表
	 * @param infos 要添加的联系人列表
	 * @param context
	 * @param myImportAsycTask 
	 * @return 包含raw_contact_id的联系人列表
	 */
	public static List<ContactsInfo> addContacts(List<ContactsInfo> infos, Context context, int i) {
		long time = System.currentTimeMillis();
		
		ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
		
//		ContentValues values = new ContentValues();
		ContentResolver resolver = context.getContentResolver();
//		Uri rawContactUri = ContactsContract.RawContacts.CONTENT_URI;
//		Uri dataUri = ContactsContract.Data.CONTENT_URI;
		
		Log.i("--tom", "ready append-- length:" + infos.size());
		int rawContactInsertIndex = i;
		for(ContactsInfo info : infos){
  
			rawContactInsertIndex = i + ops.size();

			ops.add(ContentProviderOperation
					.newInsert(ContactsContract.RawContacts.CONTENT_URI)

					.withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)

					.withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)

					.withYieldAllowed(true)

					.build());

			ops.add(ContentProviderOperation
					.newInsert(ContactsContract.Data.CONTENT_URI)

					.withValueBackReference(
							ContactsContract.Data.RAW_CONTACT_ID,

							rawContactInsertIndex)

					.withValue(
							ContactsContract.Data.MIMETYPE,

							ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)

					.withValue(
							ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,
							info.getName())

					.withYieldAllowed(true)

					.build());

			ops.add(ContentProviderOperation
					.newInsert(ContactsContract.Data.CONTENT_URI)

					.withValueBackReference(
							ContactsContract.Data.RAW_CONTACT_ID,
							rawContactInsertIndex)

					.withValue(ContactsContract.Data.MIMETYPE,

					ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)

					.withValue(ContactsContract.CommonDataKinds.Phone.NUMBER,
							info.getPhone())

					.withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
							Phone.TYPE_MOBILE)

					.withYieldAllowed(true)

					.build());
			
			Log.i("--tom", "add contact:-->" + info.getPhone());
			 
 			Log.i("--tom", "add contact:" + infos.indexOf(info));
		}
		try {
			ContentProviderResult[] results = resolver.applyBatch(ContactsContract.AUTHORITY, ops); 				
			Log.i("--tom", "insert contacts-->"  + results);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		 
		return infos;
	}
	
	 
	/**
     * 根据组名获取分组id
     * 
     * @param groupname
     * @param context
     * @return
     */
    public static long getGroupIdByGroupName(String groupname, Context context) {
        Cursor cursor = context.getContentResolver().query(ContactsContract.Groups.CONTENT_URI,
                new String[] {
                    "_id"
                }, "deleted = 0 and title = ?", new String[] {
                    groupname
                }, null);
        long group_id = -1;
        if (cursor != null && cursor.moveToFirst()) {
            group_id = cursor.getLong(0);
        }
        cursor.close();
        return group_id;
    }
	

	public static long createGroup(String groupname, Context context) {
	        // if (isGroupExist(groupname, context)) {
	        // return getGroupIdByGroupName(groupname, context);
	        // }
	    	String native_machine=context.getResources().getString(R.string.native_machine);
	        ContentValues values = new ContentValues();
	        values.put(ContactsContract.Groups.TITLE, groupname);
	        values.put("account_name",native_machine );
	        values.put("account_type", "sprd.com.android.account.phone");
	        Uri uri = null;
	        try {
	            uri = context.getContentResolver().insert(ContactsContract.Groups.CONTENT_URI, values);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return uri != null ? ContentUris.parseId(uri) : -1;
	}	   
	

}
