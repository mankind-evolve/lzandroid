//package com.lazy.android.basedata.model;
//
//import com.baicaijia.baicaijia.base.config.SystemConfig;
//import com.baicaijia.baicaijia.db.dbcontroller.BaseDataController;
//
//import java.util.ArrayList;
//import java.util.TreeMap;
//import java.util.TreeSet;
//
//
//public class LZRefreshListData_bak<T extends LZBaseData> {
//	private final static String TAG = "RefreshListData";
//
//	public static final int TYPE_GET_NEXT_PAGE = 0;
//	public static final int TYPE_GET_PREVIOUS_PAGE = TYPE_GET_NEXT_PAGE + 1;
//	private final static int START_INDEX = 1;
//
//	private ArrayList<T> mDataList;
//	private TreeMap<Long, T> mDataMap;
//	private BaseDataController<T> mDataDbController;
//
//	private boolean mIsLastPage = false;
//	private long mTimeStamp = 0;
//	private int mPageSize = SystemConfig.PAGE_SIZE;
//	private int mType = TYPE_GET_NEXT_PAGE;
//	private int mPageNum = START_INDEX;
//
//	public LZRefreshListData_bak() {
//		mDataList = new ArrayList<T>();
//		mDataMap = new TreeMap<Long, T>();
//	}
//
//	public LZRefreshListData_bak(ArrayList<T> dataList) {
//		this(dataList, new TreeMap<Long, T>());
//	}
//
//	public LZRefreshListData_bak(TreeMap<Long, T> treeMap) {
//		this(null, treeMap);
//	}
//
//	public LZRefreshListData_bak(ArrayList<T> dataList, TreeMap<Long, T> treeMap) {
//		mDataMap = treeMap;
//		if (dataList != null) {
//			mDataList = dataList;
//			for (T data : dataList) {
//				mDataMap.put(data.getId(), data);
//			}
//		} else {
//			mDataList = new ArrayList<T>();
//		}
//	}
//
//	public LZRefreshListData_bak(ArrayList<T> dataList, BaseDataController<T> controller) {
//		this(dataList);
//		mDataDbController = controller;
//	}
//
//	public LZRefreshListData_bak(TreeMap<Long, T> treeMap, BaseDataController<T> controller) {
//		this(treeMap);
//		if (controller != null) {
//			mDataDbController = controller;
//			mDataList = mDataDbController.reloadAllDataWithOrder();
//			for (T data : mDataList) {
//				mDataMap.put(data.getId(), data);
//			}
//		} else {
//			mDataList = new ArrayList<T>();
//		}
//	}
//	/**
//	* @Title: updateObject
//	* @Description: 更新对象的值
//	* @param obj 要更新的对象
//	* @return void
//	*/
//	public void updateObject(T obj) {
//		mDataMap.put(obj.getId(), obj);
//		for (int index = 0, count = mDataList.size(); index < count; ++index) {
//			if (mDataList.get(index).getId() == obj.getId()) {
//				mDataList.set(index, obj);
//				return;
//			}
//		}
//	}
//
//	public void setType(int type) {
//		mType = type;
//	}
//
//	public TreeMap<Long, T> getMap() {
//		return mDataMap;
//	}
//
//	public boolean isEmpty() {
//		return mDataList == null || mDataList.isEmpty();
//	}
//
//	public void clear() {
//		mPageNum = START_INDEX;
//		mDataList.clear();
//		mDataMap.clear();
//	}
//
//	public boolean isLastPage() {
//		return mIsLastPage;
//	}
//
//	public void setLastPage(boolean isLastPage) {
//		this.mIsLastPage = isLastPage;
//	}
//
//	public void setTimeStamp(long timeStamp) {
//		mTimeStamp = timeStamp;
//	}
//	/**
//	* @Title: getPageNum
//	* @Description: 页码。从1开始计数
//	* @return
//	* int
//	*/
//	public int getPageNum() {
//		return mPageNum;
//	}
//	/**
//	* @Title: getCursor
//	* @Description: 计算出当前游标id值(数据分页时候需要使用)
//	* @return long
//	*/
//	public long getCursor() {
//		if (mDataList.size() == 0) {
//			return -1L;//0;
//		} else if (mType == TYPE_GET_NEXT_PAGE) {
//			return mDataList.get(mDataList.size() - 1).getCursorId();
//		} else if (mType == TYPE_GET_PREVIOUS_PAGE) {
//			return mDataList.get(0).getCursorId();
//		}
//		return -1L;//0;
//	}
//
//	public long getTimeStamp() {
//		return mTimeStamp;
//	}
//
//	public int getPageSize() {
//		return mPageSize;
//	}
//
//	public void setPageSize(int size) {
//		mPageSize = size;
//	}
//
//	public int getType() {
//		return mType;
//	}
//	/**
//	* @Title: getData
//	* @Description: 根据对象id获取对象
//	* @param objId
//	* @return
//	* T
//	*/
//	public T getData(long objId) {
//		return mDataMap.get(objId);
//	}
//
//	public ArrayList<T> getDataList() {
//		return mDataList;
//	}
//
//	public void setDataList(TreeMap<Long, T> dataMap) {
//		mDataMap.putAll(dataMap);
//
//	}
//	/**
//	* @Title: add
//	* @Description: 把对象添加到指定的位置
//	* @param index 位置
//	* @param obj 要添加的对象
//	* @return void
//	*/
//	public void add(int index, T obj) {
//		if (mDataMap.get(obj.getId()) != null
//				&& mDataList.contains(mDataMap.get(obj.getId()))) {
//			mDataList.remove(mDataMap.get(obj.getId()));
//		}
//		mDataList.add(index, obj);
//		mDataMap.put(obj.getId(), obj);
//		if (mDataDbController != null) {
//			mDataDbController.updateDataList(mDataList);
//		}
//	}
//	public void add(ArrayList<T> dataList) {
//		for(T obj : dataList) {
//			add(obj);
//		}
//	}
//	/**
//	* @Title: add
//	* @Description: 添加对象进列表(如果需要持久化则写入到物理存储)
//	* @param obj 要添加的对象
//	* @return void
//	*/
//	public void add(T obj) {
//		int index = -1;
//		if (mDataMap.get(obj.getId()) != null) {
//			index = mDataList.indexOf(mDataMap.get(obj.getId()));
//		}
//		if (index >= 0) {
//			mDataList.set(index, obj);
//		} else {
//			mDataList.add(obj);
//		}
//		mDataMap.put(obj.getId(), obj);
//		if (mDataDbController != null) {
//			mDataDbController.updateDataList(mDataList);
//		}
//	}
//
//	private void removeData(ArrayList<T> dataList, long objId) {
//		for (int index = dataList.size() - 1; index >= 0; --index) {
//			T data = dataList.get(index);
//			if (data.getId() == objId) {
//				dataList.remove(index);
//				return;
//			}
//		}
//	}
//
//	private boolean mChange = false;
//
//	public boolean anchorChange() {
//		boolean change = mChange;
//		mChange = false;
//		return change;
//
//    }
//
//    public void setDataList(ArrayList<T> dataList, ArrayList<Long> deleteIdList) {
//		if (dataList != null) {
//			if (dataList.size() >= mPageSize) {
//				++mPageNum;
//			} else {
//				mIsLastPage = true;
//			}
//			//++mPageNum;
//			if (dataList.size() == 0) {
//				mIsLastPage = true;
//			}
//			mChange = dataList.size() > 0;
//			for (T data : dataList) {
//				removeData(mDataList, data.getId());
//			}
//            mDataList.addAll(dataList);
//			for (T data : dataList) {
//				mDataMap.put(data.getId(), data);
//			}
//		} else {
//			mIsLastPage = true;
//		}
//		if (deleteIdList != null) {
//			for (Long id : deleteIdList) {
//				removeObject(id);
//			}
//		}
//		if (mDataDbController != null) {
//			mDataDbController.updateDataList(mDataList);
//		}
//	}
//
//	/**
//	* @Title: delete
//	* @Description: 从物理存储中删除指定的对象
//	* @param objId 要删除的对象Id
//	* @return void
//	*/
//	public void delete(long objId) {
//		if (mDataDbController != null) {
//			if (mDataDbController.delete(objId)) {
//				removeObject(objId);
//			}
//		}
//	}
//	/**
//	* @Title: removeObject
//	* @Description: 移走指定的对象
//	* @param obj 要移走的对象
//	* @return void
//	*/
//	public void removeObject(T obj) {
//		if (obj != null) {
//			removeObject(obj.getId());
//		}
//	}
//	/**
//	* @Title: removeObject
//	* @Description: 移走指定id的对象
//	* @param objId 对象Id
//	* @return void
//	*/
//	public void removeObject(long objId) {
//		for (T obj : mDataList) {
//			if (obj.getId() == objId) {
//				mChange = true;
//				mDataList.remove(obj);
//				return;
//			}
//		}
//	}
//	/**
//	* @Title: removeDuplicate
//	* @Description: 去掉id值重复的对象
//	* @return void
//	*/
//	public void removeDuplicate() {
//		TreeSet<Long> mIdSet = new TreeSet<Long>();
//		for (int index = mDataList.size() - 1; index >= 0; --index) {
//			if (mIdSet.contains(mDataList.get(index).getId())) {
//				mDataList.remove(index);
//			} else {
//				mIdSet.add(mDataList.get(index).getId());
//			}
//		}
//	}
//
//
//    @Override
//    public String toString() {
//        return "RefreshListData{" +
//                "mDataList=" + mDataList +
//                ", mDataMap=" + mDataMap +
//                ", mDataDbController=" + mDataDbController +
//                ", mIsLastPage=" + mIsLastPage +
//                ", mTimeStamp=" + mTimeStamp +
//                ", mPageSize=" + mPageSize +
//                ", mType=" + mType +
//                ", mPageNum=" + mPageNum +
//                ", mChange=" + mChange +
//                '}';
//    }
//}
