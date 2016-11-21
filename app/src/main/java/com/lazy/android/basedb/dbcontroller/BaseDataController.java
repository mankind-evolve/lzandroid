package com.lazy.android.basedb.dbcontroller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;


import com.lazy.android.config.ConfigSystem;
import com.lazy.android.basefunc.LZLogger.Logger;
import com.lazy.android.basefunc.task.ITask;
import com.lazy.android.basefunc.task.TaskManager;
import com.lazy.android.basemodel.LZBaseData;
import com.lazy.android.basedb.provider.LZAndroidDatabase.BaseTable;
import com.lazy.android.basedb.provider.LZAndroidDatabase.CMBaseTable;
import com.lazy.android.basedb.utils.DatabaseUtil;

import java.util.ArrayList;
import java.util.TreeMap;


public abstract class BaseDataController<T extends LZBaseData> {
	private final static String TAG = "BaseDataController";
	public static TaskManager mTaskManager = new TaskManager(ConfigSystem.MAX_TASK_SIZE);
	protected CMBaseTable mDatabaseTable;
	protected long mParentId = -1;
	protected Context mContext = null;

	public final static int UPDATE_OBJECT = 1;
	public final static int UPDATE_OBJECT_LIST = UPDATE_OBJECT + 1;
	public final static int UPDATE_OBJECT_WITHOUTORDER = UPDATE_OBJECT_LIST + 1;
	public final static int DELETE_OBJECT_LIST = UPDATE_OBJECT_WITHOUTORDER + 1;
	public final static int DELETE_OBJECT_ALL = DELETE_OBJECT_LIST + 1;

	public static class DatabaseTask implements ITask {
		private Object mObject;
		private BaseDataController<?> mDbController;
		private int mUpdateType;
		private AsyncTask<Object, Void, Void> mTask;

		public DatabaseTask(BaseDataController<?> controller, Object object, int updateType) {
			mDbController = controller;
			mObject = object;
			mUpdateType = updateType;
		}

		public void execute() {
			synchronized (DatabaseTask.class) {
				mTask = mDbController.getAsyncTask(mUpdateType, this);
				mTask.execute(mObject);
			}
		}
		
		public AsyncTask<Object, Void, Void> getTask() {
			return mTask;
		}

		@Override
		public boolean isEqual(Object object) {
			if (object instanceof DatabaseTask) {
				DatabaseTask temp = (DatabaseTask)object;
				return this == temp;//mTask == temp.getTask();
			} else {
				return false;
			}
		}
	}

	public BaseDataController(Context c) {
		mContext = c;
	}

	public void setParentId(long id) {
		mParentId = id;
	}

	public abstract T createData(Cursor c);

	public abstract ContentValues getContentValues(T item);

	public abstract String[] getResultColumns();

	public int getMaxStoreSize() {
		return 0;
	}

	public CMBaseTable getDatabaseTable() {
		return mDatabaseTable;
	}

	public String[] getResultColumnWithOrder() {
		return getResultColumns();
	}

	public ContentValues createBaseDataContentValues(T item) {
		ContentValues values = new ContentValues();
		values.put(BaseTable.ID, item.getId());
		return values;
	}

	public ContentValues getContentValuesWithOrder(T item, int order) {
		ContentValues values = getContentValues(item);
		return values;
	}

	public TreeMap<Long, T> reloadAllData() {
		TreeMap<Long, T> array = new TreeMap<Long, T>();

		String where = null;
		Cursor cursor = DatabaseUtil.query(mContext, getDatabaseTable().getContentUri(),
				getResultColumns(), where, null, null);

		if (cursor != null) {
			while (cursor.moveToNext()) {
				T obj = createData(cursor);
				if (obj != null) {
					array.put(obj.getId(), obj);
				}
			}
			cursor.close();
		}
		return array;
	}
	
	public ArrayList<T> reloadAllDataWithOrderAsc() {
		return reloadAllDataWithOrder(BaseTable.SORT_ORDER_ASC);
	}
	public ArrayList<T> reloadAllDataWithOrder() {
		return reloadAllDataWithOrder(BaseTable.SORT_ORDER);
	}
	
	private ArrayList<T> reloadAllDataWithOrder(String orderBy) {
		ArrayList<T> array = new ArrayList<T>();

		String where = null;
		String[] columns = getResultColumnWithOrder();
		Cursor cursor = DatabaseUtil.query(mContext, getDatabaseTable().getContentUri(),
				columns, where, null, orderBy);

		if (cursor != null) {
			while (cursor.moveToNext()) {
				T obj = createData(cursor);
				if (obj != null) {
					array.add(obj);
				}
			}
			cursor.close();
		}
		return array;
	}
	
	public ArrayList<T> reloadAllDataWithOrder(long parentId) {
		return reloadAllDataWithOrder(parentId, BaseTable.SORT_ORDER);
	}
	public ArrayList<T> reloadAllDataWithOrderAsc(long parentId) {
		return reloadAllDataWithOrder(parentId, BaseTable.SORT_ORDER_ASC);
	}
	
	public ArrayList<T> reloadAllDataWithOrder(long parentId, String order) {
		ArrayList<T> array = new ArrayList<T>();

		String where = BaseTable.PARENT_ID + "=?";// + parentId;
		String[] columns = getResultColumnWithOrder();
		Cursor cursor = DatabaseUtil.query(mContext, getDatabaseTable().getContentUri(),
				columns, where, new String[]{String.valueOf(parentId)}, order);

		if (cursor != null) {
			while (cursor.moveToNext()) {
				T obj = createData(cursor);
				if (obj != null) {
					array.add(obj);
				}
			}
			cursor.close();
		}
		return array;
	}
	
	public T getData(long id) {
		String where = BaseTable.ID + "=?";//+ id;
		Cursor cursor = DatabaseUtil.query(mContext, getDatabaseTable().getContentUri(),
				getResultColumns(), where, new String[]{String.valueOf(id)}, null);
		
		if (cursor == null) {
			return null;
		}
		T obj = null;
		if (cursor.moveToNext()) {
			obj = createData(cursor);
		}
		cursor.close();
		return obj;
	}

	public AsyncTask<Object, Void, Void> getAsyncTask(int updateType,
			DatabaseTask taskWrapper) {
		switch (updateType) {
		case UPDATE_OBJECT:
			return new UpdateDataTask(taskWrapper);
		case UPDATE_OBJECT_LIST:
			return new UpdateDataListTask(taskWrapper);
		case UPDATE_OBJECT_WITHOUTORDER:
			return new UpdateDataListWithoutOrderTask(taskWrapper);
		case DELETE_OBJECT_LIST:
			return new DeleteDataListTask(taskWrapper);
		case DELETE_OBJECT_ALL:
			return new DeleteAllDataTask(taskWrapper);
		}
		return null;
	}
	
	private class DeleteDataListTask extends AsyncTask<Object, Void, Void> {
		private DatabaseTask mParent;

		public DeleteDataListTask(DatabaseTask parent) {
			mParent = parent;
		}

		@Override
		protected Void doInBackground(Object... items) {
			synchronized (BaseDataController.class) {
				for (Object obj : items) {
					@SuppressWarnings("unchecked")
					T item = (T) obj;
					if (item.getId() >= 0) {
						Uri uri = getDatabaseTable().getContentUri();
						String where = BaseTable.ID + "=?";// + item.getId();
						String[] params = new String[]{String.valueOf(item.getId())};
						int count = DatabaseUtil.delete(mContext, uri, where, params);
					}
				}
			}
			return null;
		}
		@Override
		protected void onPostExecute(Void result) {
			mTaskManager.removeTask(mParent);
		}
	};
	
	private class DeleteAllDataTask extends AsyncTask<Object, Void, Void> {
		private DatabaseTask mParent;

		public DeleteAllDataTask(DatabaseTask parent) {
			mParent = parent;
		}

		@Override
		protected Void doInBackground(Object... items) {
			synchronized (BaseDataController.class) {
				Uri uri = getDatabaseTable().getContentUri();
				int count = DatabaseUtil.delete(mContext, uri, null, null);
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			mTaskManager.removeTask(mParent);
		}
	};
	/**
	* @Title: delete
	* @Description: 删除一条数据
	* @param id
	* @return boolean
	*/
	public boolean delete(long id) {
		String where = BaseTable.ID + " = ?";// + id;
		synchronized (BaseDataController.class) {
			return DatabaseUtil.delete(mContext, getDatabaseTable().getContentUri(), where, new String[]{String.valueOf(id)}) >= 0;
		}
	}
	/**
	* @Title: deleteAll
	* @Description: 删除所有数据(使用异步任务)
	* @return void
	*/
	public void deleteAll() {
		mTaskManager.addTask(new DatabaseTask(this, null, DELETE_OBJECT_ALL));
	}
	/**
	* @Title: deleteDataList
	* @Description: 删除列表数据(使用异步任务)
	* @param dataList
	* @return void
	*/
	public void deleteDataList(ArrayList<T> dataList) {
		ArrayList<T> list = new ArrayList<T>();
		list.addAll(dataList);
		mTaskManager.addTask(new DatabaseTask(this, list, DELETE_OBJECT_LIST));
	}
	
	private class UpdateDataTask extends AsyncTask<Object, Void, Void> {
		private DatabaseTask mParent;

		public UpdateDataTask(DatabaseTask parent) {
			mParent = parent;
		}

//        没有数据插入数据，有数据就更新数据，单行操作
		@Override
		protected Void doInBackground(Object... items) {
			synchronized (BaseDataController.class) {
				for (Object obj : items) {
					@SuppressWarnings("unchecked")
					T item = (T) obj;
					if (item.getId() >= 0) {
						Uri uri = getDatabaseTable().getContentUri();
						String[] column = { BaseTable.ID };
						String where = BaseTable.ID + "=?";// + item.getId();
						Cursor cursor = DatabaseUtil.query(mContext, uri, column,
								where, new String[]{String.valueOf(item.getId())}, null);
						if (cursor == null || cursor.getCount() == 0) {
							DatabaseUtil.insert(mContext, uri, getContentValues(item));
						} else if (cursor.moveToNext()) {
							DatabaseUtil.update(mContext, uri, getContentValues(item), where, null);
						}
						if (cursor != null) {
							cursor.close();
						}
					}
				}
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			mTaskManager.removeTask(mParent);
		}
	};

	public void updateData(T obj) {
		if (obj != null) {
			mTaskManager.addTask(new DatabaseTask(this, obj, UPDATE_OBJECT));
		}
	}

	private class UpdateDataListTask extends AsyncTask<Object, Void, Void> {
		private DatabaseTask mParent;

		public UpdateDataListTask(DatabaseTask parent) {
			mParent = parent;
		}

		@Override
		protected Void doInBackground(Object... items) {
			synchronized (BaseDataController.class) {
				try {
					@SuppressWarnings("unchecked")
					ArrayList<T> array = (ArrayList<T>) items[0];
					Uri uri = getDatabaseTable().getContentUri();
					int maxsize = getMaxStoreSize() == 0 ? array.size() : Math.min(array.size(), getMaxStoreSize());
					for (int index = 0; index < maxsize; ++index) {
						T item = array.get(index);
						if (item.getId() >= 0) {
							String[] column = { BaseTable.ID };
							String where = BaseTable.ID + "=?";// + item.getId();
							Cursor cursor = DatabaseUtil.query(mContext, uri,
									column, where, new String[]{String.valueOf(item.getId())}, null);
	
							if (cursor == null || cursor.getCount() == 0) {
								DatabaseUtil.insert(mContext, uri,
										getContentValuesWithOrder(item, index));
							} else if (cursor.moveToNext()) {
								DatabaseUtil.update(mContext, uri,
										getContentValuesWithOrder(item, index),
										where, null);
							}	
							if (cursor != null) {
								cursor.close();
							}
						}
					}
				} catch (Exception e) {
					Logger.getInstance(TAG).debug(e.getMessage(), e);
				}
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			mTaskManager.removeTask(mParent);
		}
	};	

	private class UpdateDataListWithoutOrderTask extends
			AsyncTask<Object, Void, Void> {
		private DatabaseTask mParent;

		public UpdateDataListWithoutOrderTask(DatabaseTask parent) {
			mParent = parent;
		}

		@Override
		protected Void doInBackground(Object... items) {
			synchronized (BaseDataController.class) {
				@SuppressWarnings("unchecked")
				ArrayList<T> array = (ArrayList<T>) items[0];
				Uri uri = getDatabaseTable().getContentUri();
				for (int i = 0; i < array.size(); ++i) {
					T item = array.get(i);
					if (item.getId() >= 0) {
						String[] column = { BaseTable.ID };
						String where = BaseTable.ID + "=?";// + item.getId();
						Cursor cursor = DatabaseUtil.query(mContext, uri, column,
								where, new String[]{String.valueOf(item.getId())}, null);
						if (cursor == null || cursor.getCount() == 0) {
							DatabaseUtil.insert(mContext, uri, getContentValues(item));
						} else if (cursor.moveToNext()) {
							DatabaseUtil.update(mContext, uri, getContentValues(item), where, null);
						}
						if (cursor != null) {
							cursor.close();
						}
					}
				}
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			mTaskManager.removeTask(mParent);
		}
	};	
	/**
	* @Title: updateDataList
	* @Description: 更新列表数据
	* @param dataList
	* @return void
	*/
	public void updateDataList(ArrayList<T> dataList) {
		ArrayList<T> addData = new ArrayList<T>();
        addData.addAll(dataList);
		mTaskManager.addTask(new DatabaseTask(this, addData, UPDATE_OBJECT_LIST));
	}
	
	public void updateDataListWithoutOrder(ArrayList<T> dataList) {
		ArrayList<T> addData = new ArrayList<T>();
		addData.addAll(dataList);
		mTaskManager.addTask(new DatabaseTask(this, addData,
				UPDATE_OBJECT_WITHOUTORDER));
	}

	private static final int UPDATE = 0;
	protected ArrayList<T> mUpdateList = new ArrayList<T>();
	protected Handler mUpdateHandler = new Handler(Looper.getMainLooper()) {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case UPDATE:
				updateDataListWithoutOrder(mUpdateList);
				mUpdateList = new ArrayList<T>();
				break;
			}
		}
	};
	public void updateDataDelayed(T item) {
		mUpdateList.add(item);
		mUpdateHandler.removeMessages(UPDATE);
		mUpdateHandler.sendEmptyMessageDelayed(UPDATE, 2000);
	}
}
