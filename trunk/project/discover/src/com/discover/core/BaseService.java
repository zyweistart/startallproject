package com.discover.core;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.discover.utils.SharedPreferencesUtils;

public abstract class BaseService {
	
	private Context context;
	
	private SQLiteDatabase sqlDb;
	
	private SQLiteDBHelper dbHelper;

	private SharedPreferencesUtils preferences;
	
	public BaseService(Context context){
		this.context=context;
	}
	
	public Context getContext() {
		return context;
	}

	public SharedPreferencesUtils getSharedPreferencesUtils(){
		if(preferences==null){
			preferences=new SharedPreferencesUtils(getContext(),Constant.PreferencesName);
		}
		return preferences;
	}
	
	public SQLiteDBHelper getDbHelper() {
		if(this.dbHelper==null){
			this.dbHelper = new SQLiteDBHelper(getContext());
			getSQLiteDatabase();
		}
		return dbHelper;
	}

	public SQLiteDatabase getSQLiteDatabase(){
		if(this.sqlDb==null){
			this.sqlDb=this.getDbHelper().getWritableDatabase();
		}
		return this.sqlDb;
	}

	public void closeSQLiteDBHelper(){
		if(this.dbHelper!=null){
			this.getDbHelper().close();
			this.dbHelper=null;
		}
	}
}
