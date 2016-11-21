package com.lazy.android.basefunc.task;

/**
 * @ClassName: ITask
 * @Description: 任务接口
 * @author 
 * @date
 * 
 */
public interface ITask {
	public abstract void execute();
	public abstract boolean isEqual(Object object);
}
