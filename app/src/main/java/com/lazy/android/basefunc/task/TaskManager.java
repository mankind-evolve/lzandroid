package com.lazy.android.basefunc.task;



import com.lazy.android.config.ConfigSystem;

import java.util.ArrayList;

/**
 * @ClassName: TaskManager
 * @Description: 任务管理器
 * @author
 * @date
 * 
 */
public class TaskManager {
	public final static String TAG = "TaskManager";

	private static TaskManager instance;
	// Task队列
	private ArrayList<ITask> mTaskList;
	private int mMaxTaskSize = ConfigSystem.MAX_TASK_SIZE;
	private int mRunningTaskNumber = 0;

	public TaskManager(int taskSize) {
		if (taskSize < 0) {
			mMaxTaskSize = ConfigSystem.MAX_TASK_SIZE;
		} else {
			mMaxTaskSize = taskSize;
		}
		mTaskList = new ArrayList<ITask>();
	}

	/**
	 * @Title: addTask
	 * @Description: 加入任务队列
	 * @param task
	 * @return void
	 */
	public void addTask(ITask task) {
		synchronized (mTaskList) {
			mTaskList.add(task);
		}
        runTask();
	}

	public synchronized ArrayList<ITask> getTaskList() {
		return mTaskList;
	}

	/**
	 * @Title: removeTask
	 * @Description: 移走指定的任务
	 * @param task
	 * @return void
	 */
	public void removeTask(ITask task) {
		synchronized (mTaskList) {
			for (int index = mTaskList.size() - 1; index >= 0; --index) {
				if (mTaskList.get(index).isEqual(task)) {
					mTaskList.remove(index);
					if (index < mRunningTaskNumber) {
						--mRunningTaskNumber;
					}
				}
			}
			if (mRunningTaskNumber < 0) {
				mRunningTaskNumber = 0;
			}
		}
		runTask();
	}

	/**
	 * @Title: runTask
	 * @Description: 从队列头取出一个任务运行
	 * @return void
	 */
	public void runTask() {
		synchronized (mTaskList) {
			if (mTaskList.isEmpty()) {
				return;
			}
			if (mRunningTaskNumber < mMaxTaskSize) {
				int count = Math.min(mMaxTaskSize, mTaskList.size());
				for (int index = mRunningTaskNumber; index < count; ++index) {
					mTaskList.get(index).execute();
					++mRunningTaskNumber;
				}
			}
		}
	}

	/**
	 * @Title: clear
	 * @Description: 清空所有任务
	 * @return void
	 */
	public void clear() {
		synchronized (mTaskList) {
			mTaskList.clear();
			mRunningTaskNumber = 0;
		}
	}

	/**
	 * @Title: onDestroy
	 * @Description: 销毁对象(先清空数据)
	 * @return void
	 */
	public void onDestroy() {
		clear();
		mTaskList = null;
		System.gc();
	}
}
