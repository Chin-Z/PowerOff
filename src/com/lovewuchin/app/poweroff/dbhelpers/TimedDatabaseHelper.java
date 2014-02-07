package com.lovewuchin.app.poweroff.dbhelpers;
import android.database.sqlite.*;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.content.*;
import com.lovewuchin.app.poweroff.utils.*;
import android.database.*;
import android.util.*;

public class TimedDatabaseHelper extends SQLiteOpenHelper
{
private SQLiteDatabase db;
public static final String DB_NAME="Timed.db";
	public static final String CREATE_TBL="create table"+" TimeTable(_id integer primary key autoincrement,thour integer,tminute integer,tavaliable integer,tnote text)"; 
	public TimedDatabaseHelper(Context context){
		super(context,DB_NAME,null,1);
	}
	public TimedDatabaseHelper(){
		this(MyApplication.appContext,DB_NAME,null,1);
	}
	public TimedDatabaseHelper(Context context, String name,
	CursorFactory factory, int version){
		super(context, name, factory, version);
	}
	@Override
	public void onCreate(SQLiteDatabase db)
	{
		// TODO: Implement this method
		this.db=db;
		db.execSQL(CREATE_TBL);
	}

	@Override
	public void onUpgrade(SQLiteDatabase p1, int p2, int p3)
	{
		// TODO: Implement this method
	}
	public Cursor query(String sql, String[] args) {
		SQLiteDatabase db = this.getWritableDatabase();
		Log.d("TAG", "Query SQL " + sql + " being excuted....");
		Cursor cursor = db.rawQuery(sql, args);
		return cursor;
	}
}
