package com.lovewuchin.app.poweroff.ui;
import android.os.*;
import java.util.*;
import com.lovewuchin.app.poweroff.utils.*;


public class TimeExHelper 
{
    Timer mTimer=new Timer();
	Handler handler=new Handler(){

		@Override
		public void handleMessage(Message msg)
		{
			// TODO: Implement this method
			CMDsupport cmd=new CMDsupport();
			cmd.su.run("reboot");
			super.handleMessage(msg);
		}
		
	};
	TimerTask mTask=new TimerTask(){

		@Override
		public void run()
		{
			// TODO: Implement this method
			Message message=new Message();
			handler.sendMessage(message);
		}
		
		
	};
	public void setTime(long time){
		mTimer.schedule(mTask,time);
	}

}
