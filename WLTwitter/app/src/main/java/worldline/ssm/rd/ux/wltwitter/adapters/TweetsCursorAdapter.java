package worldline.ssm.rd.ux.wltwitter.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import java.util.List;
import worldline.ssm.rd.ux.wltwitter.WLTwitterApplication;
import worldline.ssm.rd.ux.wltwitter.components.ImageMemoryCache;
import worldline.ssm.rd.ux.wltwitter.interfaces.TweetListener;
import worldline.ssm.rd.ux.wltwitter.pojo.Tweet;

public class TweetsCursorAdapter extends CursorAdapter{
    // The cache for images
    private final ImageMemoryCache mImageMemoryCache;
    // The Layout inflater
    private final LayoutInflater mInflater;
    // The listener for events
    private TweetListener mListener;

    public TweetsCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);

        mInflater = LayoutInflater.from(WLTwitterApplication.getContext());

        // Instantiate our cache
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory / 16;
        mImageMemoryCache = new ImageMemoryCache(cacheSize);
    }

    public void setTweetListener(TweetListener listener){
        mListener = listener;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return null;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

    }
}
