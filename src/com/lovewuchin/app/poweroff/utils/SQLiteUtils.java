package com.lovewuchin.app.poweroff.utils;
import com.lovewuchin.app.poweroff.dbhelpers.*;
import android.database.sqlite.*;
import android.database.*;
import android.content.*;
import com.lovewuchin.app.poweroff.adapter.*;
import java.util.*;

public class SQLiteUtils
{
	private TimedDatabaseHelper dbHelper;
	private SQLiteDatabase db;
	Context context;
	public static final String SELECT_ALL="select*from ";
	public static final String ORDER_BY=" order by thour,tminute";
	public static final String TBL_NAME=" TimeTable";
	public static final int _id=0;
	public static final int THOUR=1;
	public static final int TMINUTE=2;
	public SQLiteUtils(Context context) throws SQLException {
		this.dbHelper = new TimedDatabaseHelper(context);
		this.db=dbHelper.getWritableDatabase();
	}

	public List<TimedItem> getAllTimed()
	{
		// TODO: Implement this method
		List<TimedItem> tasks=new ArrayList<TimedItem>();
		Cursor cursor=db.rawQuery(SELECT_ALL+TBL_NAME+ORDER_BY,null);
		while(cursor.moveToNext()){
			TimedItem task=new TimedItem();
			task.setId(cursor.getInt(_id));
			task.setHour(cursor.getInt(THOUR));
			task.setMinute(cursor.getInt(TMINUTE));
			task.setAvaliable(cursor.getInt(3));
			task.setNote(cursor.getString(4));
			tasks.add(task);
		}
		return tasks;
	}
	public void delected(int value){
		db.execSQL("delete from "+TBL_NAME+" where _id="+value);
		db.close();
	}
	public boolean delete(TimedItem timedTask) {
		// TODO Auto-generated method stub
		String deleteSql = "delete from " + TBL_NAME + " where _id=";
		int args= timedTask.getId() ;
		db.execSQL(deleteSql+args);
		return false;
	}
	public boolean close(){
		if(db!=null){
			db.close();
			return true;
		}
		return false;
	}
	public Cursor rawQuery(){
		Cursor cursor;
		cursor=db.rawQuery(SELECT_ALL+TBL_NAME+ORDER_BY,null);
		return cursor;
	}
}
