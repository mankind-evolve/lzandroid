package com.lazy.android.basefunc.LZUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;


import com.lazy.android.config.ConfigSystem;
import com.lazy.android.application.LzandroidApplication;
import com.lazy.android.baseprotocol.LZHttpNetwork;
import com.lazy.android.baseprotocol.LZHttpRequestInfo;
import com.lazy.android.basefunc.LZLogger.Logger;
import com.lazy.android.basefunc.error.ErrorManager;
import com.lazy.android.basefunc.storage.FileCache;
import com.lazy.android.basefunc.task.ITask;
import com.lazy.android.basefunc.task.TaskManager;
import com.lazy.android.baseui.tools.ImageCache;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.TreeMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 图片加载器(从网络下载图片并缓存)
 * @author gavin
 *
 */
public class UtilsHttpImageLoader {
	private final static String TAG = "HttpImageLoader";
	private final static int POOLSIZE = ConfigSystem.MAX_TASK_SIZE;
	private static UtilsHttpImageLoader mHttpImageLoader = null;
	private static final int FETCH_IMAGE_MSG = 1;
	private static final int DOWNLOAD_IMAGE_MSG = FETCH_IMAGE_MSG + 1;
	private static final int ERROR_MSG = DOWNLOAD_IMAGE_MSG + 1;
	private static ExecutorService sImageFetchThreadPool;
	private static TaskManager mTaskManager = new TaskManager(5);

	private volatile boolean mStop = false;
	private ImageLoadHandler mImageLoadHandler;
	private TreeMap<Long, ArrayList<ImageHolder>> mViewHolders = new TreeMap<Long, ArrayList<ImageHolder>>();
	private ArrayList<ImageHolder> mMissedHolders = new ArrayList<ImageHolder>();
	private HashSet<String> mDownload = new HashSet<String>();
	private FileCache mFileCache = FileCache.getInstance();

	public long mCurrentBelongId = 0;
	public long mLastBelongId = 0;
	public HttpImageListener mListener = null;
	private Context mContext = null;

	public UtilsHttpImageLoader() {
		mImageLoadHandler = new ImageLoadHandler(Looper.getMainLooper());
		mContext = LzandroidApplication.getContext();
	}

	public static UtilsHttpImageLoader getInstance(Context c) {
		if (mHttpImageLoader == null) {
			mHttpImageLoader = new UtilsHttpImageLoader();
		}
//		mHttpImageLoader.clearImageFetching();
		return mHttpImageLoader;
	}

	public interface HttpImageListener {
		public abstract void httpImageReady(View v, String url, Bitmap bmp);
	}

	/**
	 * @ClassName: ImageDownloadListener
	 * @Description: 图片下载监听
	 * @author
	 * @date
	 *
	 */
	public interface ImageDownloadListener {
		/**
		 * @Title: onReady
		 * @Description: 下载完成
		 * @param uri 图片真实url地址
		 * @param localFilePath 本地图片文件地址
		 * @return void
		 */
		public abstract void onReady(String uri, String localFilePath);
		/**
		 * @Title: onError
		 * @Description: 下载失败
		 * @param msg 错误信息
		 * @param uri 图片真实url地址
		 * @return void
		 */
		public abstract void onError(String msg, String uri);
	}

	private Handler mCallbackHandler = new Handler(Looper.getMainLooper()) {
		@Override
		public void handleMessage(Message message) {
			switch (message.what) {
			case FETCH_IMAGE_MSG:
				callbackStruct s = (callbackStruct) message.obj;
				if (s.mListener != null) {
					s.mListener.httpImageReady(s.mView, s.mUrl, s.mBmp);
				} else if (mListener != null) {
					mListener.httpImageReady(s.mView, s.mUrl, s.mBmp);
				}
				break;
			case DOWNLOAD_IMAGE_MSG:
				downloadCallbackStruct downloadCallback = (downloadCallbackStruct) message.obj;
				if (downloadCallback.mListener != null) {
					downloadCallback.mListener.onReady(downloadCallback.mUrl, downloadCallback.mLocalFilePath);
				}
				break;
			case ERROR_MSG:
				if (message.obj instanceof downloadCallbackStruct) {
					downloadCallbackStruct callback = (downloadCallbackStruct) message.obj;
					if (callback.mListener != null) {
						callback.mListener.onError(callback.mError, callback.mUrl);
					}
				}
				break;
			}
		}
	};

	private static class downloadCallbackStruct {
		public String mUrl;
		public String mLocalFilePath;
		public ImageDownloadListener mListener = null;
		public String mError = null;

		public downloadCallbackStruct(String url, String localFilePath) {
			this(url, localFilePath, null);
		}

		public downloadCallbackStruct(String url, String localFilePath, ImageDownloadListener listener) {
			mUrl = url;
			mLocalFilePath = localFilePath;
			mListener = listener;
		}

		public void setError(String error) {
			mError = error;
		}
	}

	private static class callbackStruct {
		public View mView;
		public String mUrl;
		public Bitmap mBmp;
		public HttpImageListener mListener = null;

		public callbackStruct(View view, String url, Bitmap bmp) {
			this(view, url, bmp, null);
		}

		public callbackStruct(View view, String url, Bitmap bmp, HttpImageListener listener) {
			mView = view;
			mUrl = url;
			mBmp = bmp;
			mListener = listener;
		}
	}

	private void sendFetchMessage(final View view, final String url, final Bitmap bmp) {
		sendFetchMessage(view, url, bmp, null);
	}

	private void sendFetchMessage(final View view, final String url, final Bitmap bmp, HttpImageListener listener) {
		Message message = new Message();
		message.what = FETCH_IMAGE_MSG;
		message.obj = new callbackStruct(view, url, bmp, listener);
		mCallbackHandler.sendMessage(message);
	}

	private void sendDownloadMessage(final String url, final String localFilePath, ImageDownloadListener listener,
			String error) {
		downloadCallbackStruct obj = new downloadCallbackStruct(url, localFilePath, listener);
		obj.setError(error);
		Message message = new Message();
		if (TextUtils.isEmpty(error)) {
			message.what = DOWNLOAD_IMAGE_MSG;
		} else {
			message.what = ERROR_MSG;
		}
		message.obj = obj;
		mCallbackHandler.sendMessage(message);
	}

	public HttpImageListener getListener() {
		return mListener;
	}

	public void setListener(HttpImageListener listener) {
		this.mListener = listener;
	}

	public long getCurrentBelongId() {
		return mCurrentBelongId;
	}

	public void setCurrentBelongId(long mCurrentBelongId) {
		this.mCurrentBelongId = mCurrentBelongId;
	}

	public long getLastBelongId() {
		return mLastBelongId;
	}

	public void setLastBelongId(long mLastBelongId) {
		this.mLastBelongId = mLastBelongId;
	}

	/**
	 * @Title: getLocalImagePath
	 * @Description: 本地缓存的图片路径
	 * @param realUrl 真实的图片url地址
	 * @return null 如果图片文件不存在
	 */
	public String getLocalImagePath(String realUrl) {
		return mFileCache.getCacheFile(realUrl);
	}
	/**
	 * 从网络下载图片
	 * @param imageUrl 图片Url
	 * @param listener 下载结果回调
	 */
	public void downloadImage(String imageUrl, ImageDownloadListener listener) {
		doDownloadImage(makeRealImageUrl(imageUrl), listener);
	}

	private void doDownloadImage(String imageUrl, ImageDownloadListener listener) {
		if (TextUtils.isEmpty(imageUrl) || "null".equalsIgnoreCase(imageUrl)) {
			sendDownloadMessage(imageUrl, null, listener, "image url is null");
			return;
		}
		Logger.getInstance(TAG).debug("downloadImage:" + imageUrl);
		sendDownloadImageMessage(imageUrl, listener);
	}

	private String makeRealImageUrl(String imageUrl) {
		if (TextUtils.isEmpty(imageUrl) || "null".equals(imageUrl)) {
			return "";
		}
		if (imageUrl.indexOf("http") > 0) {
			return imageUrl;
		}
		String realImageUrl = null;
		if(imageUrl.startsWith("http://")){
			realImageUrl = imageUrl;
		}else {
			realImageUrl = ConfigSystem.FILE_ROOT + imageUrl;
		}
		return realImageUrl;
	}
	/**
	 * 下载图片并显示。原理：首先从内存里找图片，内存里没有则从手机卡里查找，手机卡没有，则从网上下载，并同时存入内存和手机卡
	 * @param uri 图片的Url
	 * @param view 用于显示图片的imageview
	 * @param belongId activityId。BaseActivity.getActivityId()返回此值
	 * @param defaultId 本地的占位图片。
	 * @return
	 */
	public Bitmap loadImage(String uri, ImageView view, long belongId, int defaultId) {
		ViewGroup.LayoutParams lp = view.getLayoutParams();
		if (lp == null || lp.width == LayoutParams.WRAP_CONTENT || lp.height == LayoutParams.WRAP_CONTENT
				|| lp.width == LayoutParams.FILL_PARENT || lp.height == LayoutParams.FILL_PARENT) {
			throw new IllegalArgumentException("ImageView 未指定大小或getLayoutParams 为null");
		}
		return loadImage(makeRealImageUrl(uri), view, false, lp.width, lp.height, belongId, defaultId, false);
	}

	public Bitmap loadImage(String uri, ImageView view, int maxWidth, int maxHeight, long belongId, int defaultId, long width, long height) {
		return loadImage(makeRealImageUrl(uri), view, false, maxWidth, maxHeight, belongId, defaultId, false);
	}

	public Bitmap loadImage(String uri, ImageView view, long belongId, int defaultId, long width, long height) {
		ViewGroup.LayoutParams lp = view.getLayoutParams();
		if (lp == null || lp.width == LayoutParams.WRAP_CONTENT || lp.height == LayoutParams.WRAP_CONTENT
				|| lp.width == LayoutParams.FILL_PARENT || lp.height == LayoutParams.FILL_PARENT) {
			throw new IllegalArgumentException("ImageView 未指定大小或getLayoutParams 为null");
		}
		return loadImage(makeRealImageUrl(uri), view, false, lp.width, lp.height, belongId, defaultId, false);
	}

	public Bitmap loadImage(String uri, ImageView view, int maxWidth, int maxHeight, long belongId, int defaultId) {
		return loadImage(makeRealImageUrl(uri), view, maxWidth, maxHeight, belongId, defaultId, null);
	}

	public Bitmap loadImage(String uri, ImageView view, int maxWidth, int maxHeight, long belongId, int defaultId,
			HttpImageListener listener) {
		return loadImage(makeRealImageUrl(uri), view, false, maxWidth, maxHeight, belongId, defaultId, false, listener);
	}

	public Bitmap loadImage(String uri, ImageView view, boolean isBackground, int maxWidth, int maxHeight,
			long belongId, int defaultId, boolean reload) {
		return doLoadImage(makeRealImageUrl(uri), view, isBackground, maxWidth, maxHeight, belongId, defaultId, reload, null);
	}

	public Bitmap loadImage(String uri, ImageView view, boolean isBackground, int maxWidth, int maxHeight,
			long belongId, int defaultId, boolean reload, HttpImageListener listener) {
		return doLoadImage(makeRealImageUrl(uri), view, isBackground, maxWidth, maxHeight, belongId, defaultId, reload, listener);
	}

	private synchronized Bitmap doLoadImage(String uri, ImageView view, boolean isBackground, int maxWidth,
			int maxHeight, long belongId, int defaultId, boolean reload, HttpImageListener listener) {
		if (maxWidth <= 0 || maxHeight <= 0) {
			throw new IllegalArgumentException("图片尺寸无效。width:" + maxWidth + ",height:" + maxHeight);
		}
		if (TextUtils.isEmpty(uri) || "null".equalsIgnoreCase(uri) || "\"\"".equalsIgnoreCase(uri)) {
			if (defaultId != 0) {
				view.setImageResource(defaultId);
			} else {
				view.setImageBitmap(null);
			}
			setImageHolderUriNull(view, isBackground, maxWidth, maxHeight, belongId, defaultId, reload, listener);
			Logger.getInstance(TAG).debug(belongId + ":imageUrl is null");
			return null;
		}
		String realUrl = uri;
		if (realUrl != null && view != null) {
			if (defaultId != 0) {
				view.setImageResource(defaultId);
			} else {
				view.setImageBitmap(null);
			}
			if (belongId == 0) {
				sendFetchMessage(view, null, null);
				return null;
			}
			Bitmap bmp = ImageCache.getBitmap(realUrl);
			ImageHolder imageHolder = getImageHolderByView(view, belongId);
			if (imageHolder == null) {
				imageHolder = new ImageHolder(realUrl, view, maxWidth, maxHeight, belongId, defaultId);
				imageHolder.mIsBackground = isBackground;
				imageHolder.mReload = reload;
				addViewHolder(imageHolder);
			} else {
				imageHolder.mBelongId = belongId;
				imageHolder.mDefaultPicId = defaultId;
				imageHolder.mUri = realUrl;
				imageHolder.mIsBackground = isBackground;
				imageHolder.mReload = reload;
			}
			if (bmp != null && !reload) {
				sendFetchMessage(view, realUrl, bmp);
				view.setImageBitmap(bmp);
				Logger.getInstance(TAG).debug("belong id:" + belongId + ",(" + realUrl + ") in cache");
				return bmp;
			}
			if (mStop) {
				mMissedHolders.add(imageHolder);
			} else {
				sendFetchImageMessage(imageHolder, realUrl, listener);
			}
			return null;
		}
		return null;
	}

	/**
	 * @Title: setImageHolderUriNull
	 * @Description: url为null，设置ImageHolder为空
	 */
	private void setImageHolderUriNull(ImageView view, boolean isBackground, int maxWidth, int maxHeight,
			long belongId, int defaultId, boolean reload, HttpImageListener listener) {
		ImageHolder imageHolder = getImageHolderByView(view, belongId);
		if (imageHolder != null) {
			imageHolder.mUri = "";
			imageHolder.mImageView = view;
			imageHolder.mBelongId = belongId;
			imageHolder.mDefaultPicId = defaultId;
			imageHolder.mImageHeight = maxWidth;
			imageHolder.mImageWidth = maxHeight;
			imageHolder.mIsBackground = isBackground;
			imageHolder.mListener = listener;
			imageHolder.mReload = reload;
		}

		if (listener != null) {
			listener.httpImageReady(view, null, null);
		}
	}

	public void start() {
		mStop = false;
		for (ImageHolder holder : mMissedHolders) {
			if (holder.mUri != null && holder.mImageView != null) {
				loadImage(holder.mUri, holder.mImageView, holder.mImageWidth, holder.mImageHeight, holder.mBelongId,
						holder.mDefaultPicId);
			}
		}
		mMissedHolders.clear();
	}

	public void stop() {
		mStop = true;
	}

	public void clearImageFetching() {
		if (sImageFetchThreadPool != null) {
			sImageFetchThreadPool.shutdownNow();
			sImageFetchThreadPool = null;
		}
		mImageLoadHandler.removeMessage();
		mDownload.clear();
		mTaskManager.clear();
	}

	/**
	 * @ClassName: ImageFetcher
	 * @Description: 获取图片文件。如果磁盘上没有此文件，那么使用http协议从服务器下载并存成磁盘文件
	 * @author
	 * @date
	 *
	 */
	private class ImageFetcher implements Runnable, ITask {
		private String mUri;
		private Bitmap mBitmap;
		private ImageView mImageView;
		private int mImageWidth;
		private int mImageHeight;
		private String mError;
		private boolean mOnlyDownload = false;
		private ImageDownloadListener mDownloadListener = null;
		private HttpImageListener mFetchListener = null;

		public ImageFetcher(String uri) {
			this(uri, false);
		}

		public ImageFetcher(String uri, boolean onlyDownload) {
			mUri = uri;
			mBitmap = null;
			mImageView = null;
			mError = null;
			mOnlyDownload = onlyDownload;
		}

		public void setImageWidth(int width) {
			mImageWidth = width;
		}

		public void setImageHeight(int height) {
			mImageHeight = height;
		}

		public void setImageView(ImageView view) {
			mImageView = view;
		}

		public void setDownloadListener(ImageDownloadListener listener) {
			mDownloadListener = listener;
		}

		public void setFetchListener(HttpImageListener listener) {
			mFetchListener = listener;
		}

		public boolean isOnlyDownload() {
			return mOnlyDownload;
		}

		public String getError() {
			return mError;
		}

		public void run() {
			try {
				if (Thread.interrupted()) {
					mError = "线程被中止";
					handleError(this);
					mTaskManager.removeTask(this);
					return;
				}
				String imageFile = null;
//				synchronized (mFileCache)
				{
					imageFile = mFileCache.getCacheFile(mUri);
				}
				if (imageFile == null) {
					// 磁盘文件不存在
					LZHttpRequestInfo reqInfo = new LZHttpRequestInfo(mUri, LZHttpRequestInfo.RequestType.DOWNLOAD);
					reqInfo.setSavedFilePath(imageFile);
					byte[] bytes = LZHttpNetwork.getInstance(mContext).downloadFile(reqInfo);
					if (reqInfo.getHttpResultCode() == ErrorManager.NO_ERROR && bytes != null) {
//						synchronized (mFileCache)
						{
							// 写入磁盘文件
							mFileCache.writeCacheFile(mUri, bytes);
						}
						imageFile = mFileCache.getCacheFile(mUri);
					} else {
						mError = "下载文件失败！";
						Logger.getInstance(TAG).debug("下载图片:<" + mUri + ">失败!Error:" + reqInfo.getHttpResultCode());
						handleError(this);
					}
				}
				if (imageFile != null) {
					if (!mOnlyDownload) {
						try {
							mBitmap = UtilsGraphic.getImageThumbnailByPixel(mContext, imageFile, mImageWidth,
									mImageHeight);
						} catch (OutOfMemoryError e) {
							mError = e.getMessage();
							System.gc();
							handleError(this);
						} catch (Exception e) {
							mError = e.getMessage();
							System.gc();
							handleError(this);
						}
						if (mBitmap == null) {
							mError = "bitmap is null";
							System.gc();
							mTaskManager.removeTask(this);
							handleError(this);
							return;
						}
					}
				} else {
					mError = "imageFile is null";
					Logger.getInstance(TAG).debug(mUri + ":imageFile is null");
					mTaskManager.removeTask(this);
					handleError(this);
					return;
				}
				if (Thread.interrupted()) {
					mError = "线程被中止！";
					Logger.getInstance(TAG).debug(mError + mUri);
				}
				if (mBitmap != null || mOnlyDownload) {
					complete();
				}
				mTaskManager.removeTask(this);
			} catch (OutOfMemoryError e) {
				System.gc();
				mError = e.getMessage();
				mTaskManager.removeTask(this);
				handleError(this);
			}
		}

		private void complete() {
			mImageLoadHandler.obtainMessage(FETCH_IMAGE_MSG, this).sendToTarget();
		}

		@Override
		public void execute() {
			if (sImageFetchThreadPool == null) {
				sImageFetchThreadPool = Executors.newFixedThreadPool(POOLSIZE);
			}
			sImageFetchThreadPool.execute(this);
		}

		@Override
		public boolean isEqual(Object o) {
			if (o instanceof ImageFetcher) {
				ImageFetcher temp = (ImageFetcher) o;
				return this == temp;
			} else {
				return false;
			}
		}
	}

	private class ImageLoadHandler extends Handler {
		public ImageLoadHandler(Looper mainLooper) {
			super(mainLooper);
		}

		@Override
		public void handleMessage(Message message) {
			ImageFetcher fetcher = (ImageFetcher) message.obj;
			mDownload.remove(fetcher.mUri);
			if (fetcher.mError == null) {
				if (fetcher.isOnlyDownload()) {
					// TODO 仅下载图片
					downloadReady(fetcher);
					return;
				}
				if (fetcher.mBitmap != null) {
					bitmapReady(fetcher);
				}
			}
		}

		public void removeMessage() {
			removeMessages(FETCH_IMAGE_MSG);
			removeMessages(DOWNLOAD_IMAGE_MSG);
			removeMessages(ERROR_MSG);
		}
	}

	private void handleError(ImageFetcher fetcher) {
		mDownload.remove(fetcher.mUri);
		if (fetcher.isOnlyDownload()) {
			sendDownloadMessage(fetcher.mUri, null, fetcher.mDownloadListener, fetcher.getError());
		} else {
			sendFetchMessage(null, fetcher.mUri, null);
		}
	}

	private void downloadReady(ImageFetcher fetcher) {
		String localPath = getLocalImagePath(fetcher.mUri);
		sendDownloadMessage(fetcher.mUri, localPath, fetcher.mDownloadListener, null);
	}

	private synchronized void bitmapReady(ImageFetcher fetcher) {
		Bitmap bmp = fetcher.mBitmap;
		String uri = fetcher.mUri;
		ImageView imageView = fetcher.mImageView;
		boolean isSet = false;
		ArrayList<ImageHolder> holders = mViewHolders.get(mCurrentBelongId);
		if (holders != null) {
			for (ImageHolder holder : holders) {
				if (holder.mUri.equals(uri)) { // holder.mImageView == imageView
					if (!holder.mIsBackground) {
						holder.mImageView.setImageBitmap(bmp);
					} else {
						if (holder.mImageView.getContext() != null
								&& holder.mImageView.getContext().getResources() != null) {
							Drawable drawable = new BitmapDrawable(holder.mImageView.getContext().getResources(), bmp);
							holder.mImageView.setBackgroundDrawable(drawable);
						}
					}
					sendFetchMessage(holder.mImageView, uri, bmp, fetcher.mFetchListener);
					ImageCache.putBitmap(bmp, uri);
					isSet = true;
				}
			}
		}
		if (!isSet) {
			bmp.recycle();
		}
	}

	private ImageHolder getImageHolderByView(ImageView view, long belongId) {
		ArrayList<ImageHolder> holders = mViewHolders.get(belongId);
		if (holders != null) {
			Logger.getInstance(TAG).debug("ViewHolders count:" + holders.size());
			for (ImageHolder holder : holders) {
				if (holder.mImageView == view)
					return holder;
			}
		}
		return null;
	}

	private synchronized void removeImageHolderByView(ImageView view, long belongId) {
		ArrayList<ImageHolder> holders = mViewHolders.get(belongId);
		if (holders != null) {
			for (int index = holders.size() - 1; index >= 0; --index) {
				ImageHolder holder = holders.get(index);
				if (holder.mImageView == view) {
					holders.remove(index);
				}
			}
		}
		for (int index = mMissedHolders.size() - 1; index >= 0; --index) {
			ImageHolder holder = mMissedHolders.get(index);
			if (holder.mImageView == view) {
				mMissedHolders.remove(index);
			}
		}
	}

	private void sendDownloadImageMessage(String uri, ImageDownloadListener listener) {
		if (TextUtils.isEmpty(uri)) {
			return;
		}
		if (mDownload.contains(uri)) {
			return;
		}
		mDownload.add(uri);
		final ImageFetcher fetcher = new ImageFetcher(uri, true);
		fetcher.setDownloadListener(listener);
		mTaskManager.addTask(fetcher);
	}

	private void sendFetchImageMessage(ImageHolder imageHolder, String uri, HttpImageListener listener) {
		if (TextUtils.isEmpty(uri)) {
			return;
		}
		if (mDownload.contains(uri)) {
			return;
		}
		mDownload.add(uri);
		final ImageFetcher fetcher = new ImageFetcher(uri);
		fetcher.setFetchListener(listener);
		fetcher.setImageView(imageHolder.mImageView);
		fetcher.setImageWidth(imageHolder.mImageWidth);
		fetcher.setImageHeight(imageHolder.mImageHeight);
		mTaskManager.addTask(fetcher);
	}

	private void reloadImageHolder(ImageHolder holder) {
		Bitmap bmp = loadImage(holder.mUri, holder.mImageView, holder.mIsBackground, holder.mImageWidth,
				holder.mImageHeight, holder.mBelongId, holder.mDefaultPicId, holder.mReload);
		if (bmp != null && holder.mImageView != null) {
			holder.mImageView.setImageBitmap(bmp);
		}
	}

	private void addViewHolder(ImageHolder holder) {
		ArrayList<ImageHolder> holders = mViewHolders.get(holder.mBelongId);
		if (holders == null) {
			holders = new ArrayList<UtilsHttpImageLoader.ImageHolder>();
		}
		holders.add(holder);
		mViewHolders.put(holder.mBelongId, holders);
	}

	public void remove(String uri) {
		ImageCache.remove(uri);
		mFileCache.deleteCacheFile(uri);
	}

	public void recycle(long id) {
		recycleDestroyByBelongId(id);
	}
	
	public synchronized void recycleUnuseImage() {
		
	}
	
	public synchronized void recycleDestroyByBelongId(long id) {
		mViewHolders.remove(id);
		for (int index = mMissedHolders.size() - 1; index >= 0; --index) {
			ImageHolder holder = mMissedHolders.get(index);
			if (holder.mBelongId == id) {
				mMissedHolders.remove(index);
			}
		}
		if (mLastBelongId != mCurrentBelongId) {
			recycleUnuseImage();
		}
	}

	public synchronized void recyclePauseByBelongId(long id) {
		clearImageFetching();
		if (mLastBelongId != id) {
			ArrayList<ImageHolder> holders = mViewHolders.get(mLastBelongId);
			if (holders != null) {
				for (int index = holders.size() - 1; index >= 0; --index) {
					ImageHolder holder = holders.get(index);
					holder.mImageView.setImageResource(holder.mDefaultPicId);
				}
			}
			for (int index = mMissedHolders.size() - 1; index >= 0; --index) {
				ImageHolder holder = mMissedHolders.get(index);
				if (holder.mBelongId == id) {
					holder.mImageView.setImageResource(holder.mDefaultPicId);
				}
			}
			mLastBelongId = id;
		}
	}

	public synchronized void resumeBelongId(long id) {
		if (mCurrentBelongId != id) {
			mCurrentBelongId = id;
			mDownload.clear();
			ArrayList<ImageHolder> holders = mViewHolders.get(id);
			if (holders != null) {
				for (int index = holders.size() - 1; index >= 0; --index) {
					ImageHolder holder = holders.get(index);
					reloadImageHolder(holder);
				}
			}
			for (int index = mMissedHolders.size() - 1; index >= 0; --index) {
				ImageHolder holder = mMissedHolders.get(index);
				if (holder.mBelongId == id) {
					reloadImageHolder(holder);
				}
			}
			System.gc();
		}
	}

	public static class ImageHolder {
		public ImageView mImageView;
		public String mUri;
		public String mNewUri = null;
		public int mImageWidth;
		public int mImageHeight;
		public long mBelongId;
		public int mDefaultPicId;
		public boolean mReload = false;
		public boolean mIsBackground = false;
		public HttpImageListener mListener = null;

		public ImageHolder(String uri, ImageView view, int maxWidth, int maxHeight, long belongId, int defaultId) {
			this(uri, view, maxWidth, maxHeight, belongId, defaultId, null);
		}

		public ImageHolder(String uri, ImageView view, int maxWidth, int maxHeight, long belongId, int defaultId,
				HttpImageListener listener) {
			mUri = uri;
			mImageView = view;
			mImageWidth = maxWidth;
			mImageHeight = maxHeight;
			mBelongId = belongId;
			mDefaultPicId = defaultId;
			mListener = listener;
		}

		public void setBitmap(Bitmap bmp) {
			mImageView.setImageBitmap(bmp);
		}
	}

	public static class ImageBackgroundHolder extends ImageHolder {
		public ImageBackgroundHolder(String uri, ImageView view, int maxWidth, int maxHeight, long belongId,
				int defaultId) {
			super(uri, view, maxWidth, maxHeight, belongId, defaultId);
		}

		public void setBitmap(Bitmap bmp) {
			Drawable drawable = new BitmapDrawable(mImageView.getContext().getResources(), bmp);
			mImageView.setBackgroundDrawable(drawable);
		}
	}
	
}
