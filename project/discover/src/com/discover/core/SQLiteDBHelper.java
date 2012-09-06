package com.discover.core;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteDBHelper extends SQLiteOpenHelper {

	private static final int DATABASEVERSION = 1;
	
	public SQLiteDBHelper(Context context) {
		super(context, Constant.SQLiteDataBaseName, null, DATABASEVERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		if(oldVersion==1){
			if(newVersion==2){
//				//1把数据表重名为临时表
//				db.execSQL("ALTER TABLE "+TABLENAME+" RENAME TO __temp__"+TABLENAME);
//				//2创建当前版本对应的数据表
//				onCreate(db);
//				//3把临时表中的数据迁移到新创建的表中
//				db.execSQL("INSERT INTO "+TABLENAME+"() SELECT field1,field2,field3 FROM __temp__"+TABLENAME);
//				//4删除临时表完成数据迁移
//				db.execSQL("DROP TABLE IF EXISTS __temp__"+TABLENAME);
//				//注:如果只是添加字段列则可以使用
//				//ALTER TABLE tableName ADD COLUMN fieldName BLOB;该格式进行添加
			}
		}else if(oldVersion==2){
			
		}
	}

}