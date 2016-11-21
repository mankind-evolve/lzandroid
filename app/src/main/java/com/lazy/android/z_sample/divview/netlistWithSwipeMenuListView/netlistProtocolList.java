package com.lazy.android.z_sample.divview.netlistWithSwipeMenuListView;

import android.content.Context;

import com.lazy.android.basefunc.LZLogger.Logger;
import com.lazy.android.basemodel.LZRefreshListData;
import com.lazy.android.baseprotocol.LZHttpIProtocolCallback;
import com.lazy.android.baseprotocol.LZHttpProtocolListHandlerBase;
import com.lazy.android.baseprotocol.LZHttpRequestInfo;
import com.lazy.android.config.ConfigProtocolType;
import com.lazy.android.config.ConfigStaticType;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Administrator on 2015/5/14.
 */

/**
 * 技师评论列表的协议层
 */
public class netlistProtocolList extends LZHttpProtocolListHandlerBase<itemmodel> {
    private static final String TAG = "netlistProtocolList";
    private Logger logger;
    public netlistProtocolList(Context context, boolean refreshed, LZRefreshListData<itemmodel> data, LZHttpIProtocolCallback callBack) {
        super(context, refreshed, data, callBack);
        mSubUrl = mListUrl + "?page=" + data.getPageNum();
        //测试数据
        mProtocolType = ConfigProtocolType.SAMPLE_LIST;
        //组装请求地址，组装数据域，发送数据
        mRequestInfo = new LZHttpRequestInfo(mSubUrl, LZHttpRequestInfo.RequestType.GET);
        this.sendRequest();
    }

    @Override
    public boolean parse() {
        try {
            if (mProtocolStatus == ConfigStaticType.ProtocolStatus.RESULT_SUCCESS) {  //请求数据成功
                if (!mResponeVO.isNull("data")) {   //截取到的data数据不为空
                    JSONArray objArray = mResponeVO.getJSONArray("data");   //data数据转成Json数据
                    if (objArray != null && objArray.length() > 0) {//截取到的json数据长度不为0也不为空
                        JSONObject item;
                        int count = objArray.length();
                        for (int index = 0;  index < count;  ++index) {  //把转换成的json数据转换成对象，塞入集合中
                            item = objArray.getJSONObject(index);
                            itemmodel data = itemmodel.parse(item);
                            if (data != null) {
                                mDataList.getDataList().add(data);
                                mReceiveDataList.add(data);
                            }
                        }

                    } else {
                        // 没有结果集(最后一页)
                        mProtocolStatus = ConfigStaticType.ProtocolStatus.RESULT_SUCCESS_EMPTY;
                        mDataList.setLastPage(true);
                    }
                } else {
                    // 没有结果集(最后一页)
                    mProtocolStatus = ConfigStaticType.ProtocolStatus.RESULT_SUCCESS_EMPTY;
                    mDataList.setLastPage(true);
                }
            } else if (mProtocolStatus == ConfigStaticType.ProtocolStatus.RESULT_SUCCESS_EMPTY) {
                // 没有结果集(最后一页)
                mDataList.setLastPage(true);
            }
        } catch (Exception e) {
            Logger.getInstance(TAG).debug(e.getMessage(), e);
        }
        return true;
    }




    @Override
    public void afterParse() {
        super.afterParse();
    }
}
