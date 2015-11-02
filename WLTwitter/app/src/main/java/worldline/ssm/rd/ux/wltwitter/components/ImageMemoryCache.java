package worldline.ssm.rd.ux.wltwitter.components;

import android.graphics.Bitmap;
import android.util.LruCache;

public class ImageMemoryCache {

	// The cache object
	private LruCache<String, Bitmap> mMemoryCache;

	public ImageMemoryCache(int maxCacheSize){
		mMemoryCache = new LruCache<String, Bitmap>(maxCacheSize) {
			@Override
			protected int sizeOf(String key, Bitmap bitmap) {
				// The cache size will be measured in kilobytes rather than
				// number of items.
				return (bitmap.getRowBytes() * bitmap.getHeight()) / 1024;
			}
		};
	}

	public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
		if (getBitmapFromMemCache(key) == null) {
			mMemoryCache.put(key, bitmap);
		}
	}

	public Bitmap getBitmapFromMemCache(String key) {
		return mMemoryCache.get(key);
	}

}
