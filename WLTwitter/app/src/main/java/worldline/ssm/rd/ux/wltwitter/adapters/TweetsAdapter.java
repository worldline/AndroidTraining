package worldline.ssm.rd.ux.wltwitter.adapters;


import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import worldline.ssm.rd.ux.wltwitter.pojo.Tweet;

public class TweetsAdapter extends BaseAdapter {

    // The list of items to display
    private final List<Tweet> mTweets;

    public TweetsAdapter(List<Tweet> tweets) {
        mTweets = tweets;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
