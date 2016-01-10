package worldline.ssm.rd.ux.wltwitter.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import worldline.ssm.rd.ux.wltwitter.R;
import worldline.ssm.rd.ux.wltwitter.WLTwitterApplication;
import worldline.ssm.rd.ux.wltwitter.components.ImageMemoryCache;
import worldline.ssm.rd.ux.wltwitter.interfaces.TweetListener;
import worldline.ssm.rd.ux.wltwitter.pojo.Tweet;

public class TweetsCursorAdapter extends CursorAdapter implements View.OnClickListener {
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
    public View newView(Context context, Cursor cursor, ViewGroup container) {
        final View view = LayoutInflater.from(context).inflate(R.layout.tweet_listitem, null);
        // Instantiate the ViewHolder
        final ViewHolder holder = new ViewHolder(view);
        // Set as tag to the convertView to retrieve it easily
        view.setTag(holder);
        return view;
    }

    public class ViewHolder {
        public ImageView image;
        public TextView name;
        public TextView alias;
        public TextView text;
        public Button button;

        public ViewHolder(View view){
            image = (ImageView) view.findViewById(R.id.tweetListItemImageView);
            name = (TextView) view.findViewById(R.id.tweetListItemNameTextView);
            alias = (TextView) view.findViewById(R.id.tweetListItemAliasTextView);
            text = (TextView) view.findViewById(R.id.tweetListItemTweetTextView);
            button = (Button) view.findViewById(R.id.tweetListItemButton);
        }
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

    }

    @Override
    public void onClick(View view) {
        // If we have a listener set, call the retweet method
        if (null != mListener){
            final Tweet tweet = (Tweet) view.getTag();
            mListener.onRetweet(tweet);
        }
    }
}
