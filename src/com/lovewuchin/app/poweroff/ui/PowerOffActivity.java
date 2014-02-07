package com.lovewuchin.app.poweroff.ui;
import android.app.*;
import android.view.*;
import com.lovewuchin.*;
import android.os.*;
import android.content.*;
import com.lovewuchin.app.poweroff.*;
import com.lovewuchin.app.poweroff.adapter.*;
import java.util.*;
import com.fortysevendeg.swipelistview.*;
import android.util.*;
import com.lovewuchin.app.poweroff.dbhelpers.*;
import android.database.*;
import android.database.sqlite.*;

public class PowerOffActivity extends Activity
{
	public static final String SELECT_ALL="select * from ";
	public static final String ORDER_BY=" order by thour,tminute";
	public static final String TBL_NAME=" TimeTable";
	private Menu mMenu;
	private TimedAdapter adapter;
	private List<TimedItem> data;
	private SwipeListView mSwipeListView;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.poweroff_main_list);
		TimedDatabaseHelper helper=new TimedDatabaseHelper(this);
		Cursor cursor=helper.query(SELECT_ALL+TBL_NAME+ORDER_BY,null);
		adapter=new TimedAdapter(this,cursor);
		mSwipeListView=(SwipeListView)findViewById(R.id.timed_list);
		mSwipeListView.setSwipeListViewListener(new BaseSwipeListViewListener(){
				@Override
				public void onOpened(int position, boolean toRight) {
				}

				@Override
				public void onClosed(int position, boolean fromRight) {
				}

				@Override
				public void onListChanged() {
				}

				@Override
				public void onMove(int position, float x) {
				}

				@Override
				public void onStartOpen(int position, int action, boolean right) {
					Log.d("swipe", String.format("onStartOpen %d - action %d", position, action));
				}

				@Override
				public void onStartClose(int position, boolean right) {
					Log.d("swipe", String.format("onStartClose %d", position));
				}

				@Override
				public void onClickFrontView(int position) {
					Log.d("swipe", String.format("onClickFrontView %d", position));
				}

				@Override
				public void onClickBackView(int position) {
					Log.d("swipe", String.format("onClickBackView %d", position));
				}

				@Override
				public void onDismiss(int[] reverseSortedPositions) {
					for (int position : reverseSortedPositions) {
						data.remove(position);
					}
					adapter.notifyDataSetChanged();
				}
				
		});
		mSwipeListView.setAdapter(adapter);
		loadMod();
	}

	private void loadMod()
	{
		// TODO: Implement this method
		mSwipeListView.setSwipeMode(SwipeListView.SWIPE_MODE_LEFT);
		mSwipeListView.setOffsetLeft(600);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// TODO: Implement this method
		MenuInflater inflater=getMenuInflater();
		inflater.inflate(R.menu.action_bar,menu);
		mMenu=menu;
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// TODO: Implement this method
		boolean ret=false;
		switch(item.getItemId()){
			case R.id.new_time_task:{
				Intent toCreateIntent=new Intent(this,CreateTimeActivity.class);
				startActivity(toCreateIntent);
				finish();
			}
		}
		return super.onOptionsItemSelected(item);
	}
	
}
