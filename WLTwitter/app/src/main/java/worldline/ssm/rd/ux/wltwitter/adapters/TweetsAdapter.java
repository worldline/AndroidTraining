package worldline.ssm.rd.ux.wltwitter.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import worldline.ssm.rd.ux.wltwitter.R;
import worldline.ssm.rd.ux.wltwitter.WLTwitterApplication;
import worldline.ssm.rd.ux.wltwitter.pojo.Tweet;

public class TweetsAdapter extends BaseAdapter {

    private final List<Tweet> mTweets;

    // The Layout inflater
    private final LayoutInflater mInflater;


    public TweetsAdapter(List<Tweet> tweets) {
        this.mTweets = tweets;
        mInflater = LayoutInflater.from(WLTwitterApplication.getContext());

    }

    @Override
    public int getCount() {
        return null != mTweets ? mTweets.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return null != mTweets ? mTweets.get(position) :  null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
           // Inflate our View
            final View view = mInflater.inflate(R.layout.tweet_listitem, null);

            // Get the current item
            final Tweet tweet = (Tweet) getItem(position);

            // Set the user name, to do so, retrieve the corresponding TextView
            final TextView userName = (TextView) view.findViewById(R.id.tweetListItemNameTextView);
            userName.setText(tweet.user.name);

            // Set the user alias, to do so, retrieve the corresponding TextView
            final TextView userAlias = (TextView) view.findViewById(R.id.tweetListItemAliasTextView);
            userAlias.setText("@" + tweet.user.screenName);

            // Set the text of the tweet, to do so, retrieve the corresponding TextView
            final TextView text = (TextView) view.findViewById(R.id.tweetListItemTweetTextView);
            text.setText(tweet.text);

            return view;
        }

    }
