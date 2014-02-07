package com.lovewuchin.app.poweroff.dbhelpers;
import com.lovewuchin.app.poweroff.adapter.*;
import java.util.*;
import android.database.sqlite.*;
import android.content.*;
import android.database.*;
import android.support.v4.app.*;

public class SQLiteDatabaseSource implements TimedDataSources
{
	public static final String SELECT_ALL="select*form";
	public static final String ORDER_BY=" order by thour,tminute,tavaliable,tnote";
	public static final String TBL_NAME=" TimeTable";
	public static final int _id=0;
	public static final int THOUR=1;
	public static final int TMINUTE=2;
	private SQLiteDatabase db;
	private TimedDatabaseHelper helper;

	public SQLiteDatabaseSource(Context context){
		helper=new TimedDatabaseHelper(context);
		db=helper.getWritableDatabase();
	}
	public SQLiteDatabaseSource(){
		helper=new TimedDatabaseHelper();
		db=helper.getWritableDatabase();
	}
	@Override
	public List<TimedItem> getAllTimed()
	{
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
		// TODO: Implement this method
		return tasks;
	}

	@Override
	public void setAllTimed(List<TimedItem> timedTasks)
	{
		// TODO: Implement this method
		for (int i = 0; i < timedTasks.size(); i++) {
			addTimed(timedTasks.get(i));
		}
	}

	@Override
	public void addTimed(TimedItem timedTask)
	{
		// TODO: Implement this method
		Integer ids = timedTask.getId()==-1? null : timedTask.getId();
		Object args[] = new Object[] { ids, timedTask.getHour(),
			timedTask.getMinute(),
			timedTask.isAvaliable()?1:0,
			timedTask.getNote()};
		String insertSql = "insert into " + TBL_NAME
			+ " values (?,?,?,?,?)";
		db.execSQL(insertSql, args);
	}

	@Override
	public boolean deleteTimed(TimedItem timedTask)
	{
		// TODO: Implement this method
		String deleteSql = "delete from " + TBL_NAME+ " where _id=?";
		Object args[] = new Object[] { timedTask.getId() };
		db.execSQL(deleteSql, args);
		return false;
	}

	@Override
	public boolean deleteAllTimed()
	{
		// TODO: Implement this method
		String deleteAllSql = "delete * from " + TBL_NAME;
		db.execSQL(deleteAllSql);
		return false;
	}

	@Override
	public boolean updateTimed(TimedItem timedTask)
	{
		// TODO: Implement this method
		deleteTimed(timedTask);
		addTimed(timedTask);
		return false;
	}

	@Override
	public boolean close()
	{
		// TODO: Implement this method
		if(db!=null){
			db.close();
			return true;
		}
		return false;
	}

	@Override
	public int count()
	{
		// TODO: Implement this method
		Cursor cursor = db.rawQuery(SELECT_ALL+TBL_NAME+ORDER_BY, null);
		return cursor.getCount();
	}
	
}
