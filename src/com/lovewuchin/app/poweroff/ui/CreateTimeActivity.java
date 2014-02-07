package com.lovewuchin.app.poweroff.ui;
import android.annotation.TargetApi;
import android.app.*;
import android.support.v4.app.*;
import android.os.*;
import com.lovewuchin.*;
import android.view.*;
import android.widget.*;
import android.content.*;
import android.preference.*;
import com.lovewuchin.app.poweroff.dbhelpers.*;
import java.io.*;
import com.lovewuchin.app.poweroff.adapter.*;
import com.lovewuchin.app.poweroff.*;
import android.text.format.*;
import java.util.*;

public class CreateTimeActivity extends PreferenceActivity implements Preference.OnPreferenceChangeListener,Preference.OnPreferenceClickListener
{	
private Menu mMenu;
private TimePicker mPicker;
private CheckBoxPreference mSwitch;
private ListPreference mList;
private EditTextPreference mEdit;
private CheckBoxPreference mCheck;
private SharedPreferences mPrefs;
private TimedItem task;
private Boolean isUpdate=false;
private Context mContext;
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_item_layout);
		addPreferencesFromResource(R.xml.create_item_preference);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		initAllFields();
	}

	@Override
	protected void onStart()
	{
		// TODO: Implement this method
		super.onStart();
		Intent fromIntent =getIntent();
		Serializable serializableTask=fromIntent.getSerializableExtra("task");
		task =(TimedItem) serializableTask;
		if(task!=null){
			mPicker.setCurrentHour(task.getHour());
			mPicker.setCurrentMinute(task.getMinute());
			isUpdate=true;
		}
	}
	

	private void initAllFields()
	{
		// TODO: Implement this method
		mPrefs=getSharedPreferences("CreateTimeActivity",Context.MODE_WORLD_READABLE);
	    mPicker=(TimePicker)findViewById(R.id.time_picker);
		mSwitch=(CheckBoxPreference)findPreference("pref_is_checked");
		mList=(ListPreference)findPreference("pref_week_states");
		mEdit=(EditTextPreference)findPreference("pref_norma_value");
		mCheck=(CheckBoxPreference)findPreference("pref_show_dialog");
		mList.setValue(String.valueOf(mPrefs.getInt("pref_week_states", 0)));
		mList.setSummary(mList.getEntry());
		mList.setOnPreferenceChangeListener(this);
		
		mSwitch.setChecked(mPrefs.getBoolean("pref_is_checked",true));
		mSwitch.setOnPreferenceClickListener(this);
		if(mSwitch.isChecked()){mSwitch.setSummary("开启");}
		else {mSwitch.setSummary("关闭");}
		
		mCheck.setChecked(mPrefs.getBoolean("pref_show_dialog",true));
		mCheck.setOnPreferenceClickListener(this);
		if(mCheck.isChecked()){mCheck.setSummary("开启");}
		else {mCheck.setSummary("关闭");}
		
		mPicker.setIs24HourView(true);
		
		mEdit.setDefaultValue(mPrefs.getString("pref_norma_value","请输入备注"));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// TODO: Implement this method
		MenuInflater inflater=getMenuInflater();
		inflater.inflate(R.menu.new_time_task,menu);
		mMenu=menu;
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// TODO: Implement this method
		boolean ret=false;
		switch(item.getItemId()){
			case android.R.id.home:toMainActivity();finish();;break;
			case R.id.ok:{
				int hour=mPicker.getCurrentHour();
				int minute=mPicker.getCurrentMinute();
				boolean avaliable=mSwitch.isChecked();
				String note=mPrefs.getString("pref_norma_value","请输入备注");
					long time=System.currentTimeMillis();
					final Calendar mCalendar=Calendar.getInstance();
					mCalendar.setTimeInMillis(time);
					long chour=mCalendar.get(Calendar.HOUR);
					long cminute=mCalendar.get(Calendar.MINUTE);
			    long exTime=(hour*60+minute)*60*1000-(chour*60+cminute)*60*1000;
				TimedDataSources data=new SQLiteDatabaseSource(getApplicationContext());
				if(isUpdate){
				task=new TimedItem(task.getId(),hour,minute,avaliable,note);
				data.updateTimed(task);
				}else{
					task=new TimedItem(hour,minute,avaliable,note);
					data.addTimed(task);
				TimeExHelper mtask=	new TimeExHelper();
				mtask.setTime(exTime);
				}			
				data.close();
				toMainActivity();
				break;
				
			}
		}
		return super.onOptionsItemSelected(item);
	}

	private void toMainActivity()
	{
		// TODO: Implement this method
		Intent intent=new Intent(this,PowerOffActivity.class);
		startActivity(intent);
		finish();
	}
	@Override
	public boolean onPreferenceChange(Preference p1, Object p2)
	{
		// TODO: Implement this method
		if(p1==mList){
			int value=Integer.parseInt((String)p2);
			int index=mList.findIndexOfValue(String.valueOf(value));
			mPrefs.edit().putInt("pref_week_states",value).commit();
			mList.setSummary(mList.getEntries()[index]);
		}
		return true;
	}
	@Override
	public boolean onPreferenceClick(Preference p1)
	{
		// TODO: Implement this method
		String key=p1.getKey();
		if(key.equals("pref_is_checked")){
			mPrefs.edit().putBoolean("pref_is_checked",mSwitch.isChecked()).commit();
			if(mSwitch.isChecked()){mSwitch.setSummary("开启");}
			else {mSwitch.setSummary("关闭");}
			return true;
		}else if(key.equals("pref_show_dialog")){
			mPrefs.edit().putBoolean("pref_show_dialog",mCheck.isChecked()).commit();
			if(mCheck.isChecked()){mCheck.setSummary("开启");}
			else {mCheck.setSummary("关闭");}
			return true;
		}else if(key.equals("pref_norma_value")){
			mPrefs.edit().putString("pref_norma_value","请输入备注");
		}
		return false;
	}
	
}
