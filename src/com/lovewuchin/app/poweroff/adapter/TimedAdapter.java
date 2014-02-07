package com.lovewuchin.app.poweroff.adapter;
import android.widget.*;
import android.view.View.*;
import android.view.*;
import java.util.*;
import android.content.*;
import com.lovewuchin.app.poweroff.*;
import com.fortysevendeg.swipelistview.*;
import com.lovewuchin.app.poweroff.dbhelpers.*;
import android.text.*;
import android.database.sqlite.*;
import android.database.*;
import com.lovewuchin.app.poweroff.ui.*;
import com.lovewuchin.app.poweroff.utils.*;
import java.io.*;
import android.text.style.*;

public class TimedAdapter extends BaseAdapter implements OnClickListener
{
	public static final String SELECT_ALL="select*from";
	public static final String ORDER_BY=" order by thour,tminute";
	public static final String TBL_NAME=" TimeTable";
List<TimedItem> mData;
Context mContext;
SQLiteDatabase db;
TimedDatabaseHelper helper;
Cursor cursor;
LayoutInflater inflater;
SharedPreferences mPrefs;
TimedItem task;

    public TimedAdapter(Context mContext){
		super();
		this.mContext=mContext;
	}
    public TimedAdapter(Context mContext,List<TimedItem> mData){
		super();
		this.mContext=mContext;
		this.mData=mData;
	}
	public TimedAdapter(Context mContext,Cursor cursor){
		super();
		this.mContext=mContext;
		this.cursor=cursor;
		inflater=LayoutInflater.from(mContext);
		updateDataFromDatabase();
	}

	private void updateDataFromDatabase()
	{
		SQLiteUtils sql=new SQLiteUtils(mContext);
		mData=sql.getAllTimed();
		sql.close();
		// TODO: Implement this method
	}
	@Override
	public void onClick(View p1)
	{
		// TODO: Implement this method
	
		
	}

	private void deleteTimedTask(View arg)
	{
		// TODO: Implement this method
		int position=arg.getId();
	
	}

	@Override
	public int getCount()
	{
		// TODO: Implement this method
		return cursor.getCount();
	}

	@Override
	public Object getItem(int p1)
	{
		// TODO: Implement this method
		return p1;
	}

	@Override
	public long getItemId(int p1)
	{
		// TODO: Implement this method
		return p1;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		// TODO: Implement this method
		cursor.moveToPosition(position);
		MyViewHolder mViewHolder;
		if(convertView==null){
			LayoutInflater layout=(LayoutInflater)mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
			convertView=layout.inflate(R.layout.list_item_layout,parent,false);
			mViewHolder=new MyViewHolder();
			mViewHolder.mClock=(TextView)convertView.findViewById(R.id.text_time);
			mViewHolder.mState=(TextView)convertView.findViewById(R.id.text_on);
			mViewHolder.mNote=(TextView)convertView.findViewById(R.id.text_note);
			mViewHolder.mEdit=(Button)convertView.findViewById(R.id.bt_edit);
			mViewHolder.mDelete=(Button)convertView.findViewById(R.id.bt_delected);
			mViewHolder.mDelete.setTag(position);
			mViewHolder.mEdit.setTag(position);
			convertView.setTag(mViewHolder);	
		}else{
			mViewHolder=(MyViewHolder)convertView.getTag();
		}
		mViewHolder.mClock.setText(cursor.getString(1)+":"+cursor.getString(2));
		mViewHolder.mNote.setText(cursor.getString(4));
		if(cursor.getInt(3)==1){
		mViewHolder.mState.setText("开启");}
		else{mViewHolder.mState.setText("关闭");}
	   int mid=cursor.getInt(0);
		mViewHolder.mEdit.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					// TODO: Implement this method
					int position = (Integer) p1.getTag();
		
						Intent toCreateIntent= new Intent(mContext,
														   CreateTimeActivity.class);
						Serializable serializableTask = mData.get(position);
						toCreateIntent.putExtra("task",
												serializableTask);
						mContext.startActivity(toCreateIntent);
						MyApplication.exit(true);
				}
				
			
		});
		mViewHolder.mDelete.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					// TODO: Implement this method
				
			/*	updateDataFromDatabase();*/
			/*	deleteTimedTask(p1);*/
			   	SQLiteUtils sql=new SQLiteUtils(mContext);
				int position=(Integer)p1.getTag();
                sql.delete(mData.get(position));
				sql.close();
	      	   updateDataFromDatabase();
			  Intent intentRefresh=new Intent();
			   intentRefresh.setClass(mContext,mContext.getClass());
			   mContext.startActivity(intentRefresh);
			   MyApplication.exit(true);
			   }
				
			
		});
		return convertView;
	}
	  
	static class MyViewHolder{
		TextView mClock;
		TextView mState;
		TextView mNote;
		Button mDelete;
		Button mEdit;
	}
	
}
