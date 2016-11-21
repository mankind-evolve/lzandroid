package com.lazy.android.basemodel;

import org.xutils.DbManager;
import org.xutils.common.util.KeyValue;
import org.xutils.db.sqlite.WhereBuilder;
import org.xutils.ex.DbException;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/1/29.
 * Email: 187228131@qq.com
 */
public class LZBaseSqliteBean implements Serializable {

//	保存
	public void save(DbManager db){
		try {
			db.save(this);
		} catch (DbException e) {
			e.printStackTrace();
		}
	}

//	删除
	public void delete(DbManager db , int id){
		try {
			db.deleteById(this.getClass(),id);
		} catch (DbException e) {
			e.printStackTrace();
		}
	}

//	修改
	public void update(DbManager db,int id,KeyValue... nameValuePairs){
		try {
			db.update(this.getClass(), WhereBuilder.b("id","=",id),nameValuePairs);
		} catch (DbException e) {
			e.printStackTrace();
		}
	}

//	查找
	public Object select(DbManager db,int id){
		Object object = null;
		try {
			object = db.selector(this.getClass()).where("id","=",id).findFirst();
		} catch (DbException e) {
			e.printStackTrace();
		}
		return object;
	}


}
