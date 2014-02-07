package com.lovewuchin.app.poweroff.dbhelpers;

import java.util.List;
import com.lovewuchin.app.poweroff.adapter.*;


public interface TimedDataSources {
	public List<TimedItem> getAllTimed();
	public void setAllTimed(List<TimedItem> timedTasks);
	public void addTimed(TimedItem timedTask);
	public boolean deleteTimed(TimedItem timedTask);
	public boolean deleteAllTimed();
	public boolean updateTimed(TimedItem timedTask);
	public boolean close();
	public int count();
}
