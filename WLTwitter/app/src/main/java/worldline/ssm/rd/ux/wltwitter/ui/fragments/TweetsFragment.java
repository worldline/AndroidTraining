package worldline.ssm.rd.ux.wltwitter.ui.fragments;


import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import worldline.ssm.rd.ux.wltwitter.R;
import worldline.ssm.rd.ux.wltwitter.adapters.TweetsAdapter;
import worldline.ssm.rd.ux.wltwitter.async.RetrieveTweetsAsyncTask;
import worldline.ssm.rd.ux.wltwitter.interfaces.TweetChangeListener;
import worldline.ssm.rd.ux.wltwitter.interfaces.TweetListener;
import worldline.ssm.rd.ux.wltwitter.pojo.Tweet;
import worldline.ssm.rd.ux.wltwitter.utils.PreferenceUtils;

import static android.widget.AdapterView.*;

/**
 * A simple {@link Fragment} subclass.
 */
public class TweetsFragment extends Fragment implements TweetChangeListener,
        AdapterView.OnItemClickListener {

    private RetrieveTweetsAsyncTask mTweetAsyncTask;
    private ListView mListView;

    // Keep a reference to our Activiyt (implementing TweetListener)
    private TweetListener mListener;

    public TweetsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View RootView = inflater.inflate(R.layout.fragment_tweets, container, false);
        mListView= (ListView) RootView.findViewById(R.id.tweetsListView);
        // Set adapter with no elements to let the ListView display the empty view
        mListView.setAdapter(
                new ArrayAdapter<Tweet>(getActivity(),
                        android.R.layout.simple_list_item_1, new ArrayList<Tweet>()));

        // Add a listener when an item is clicked
        mListView.setOnItemClickListener(this);

        return RootView;
    }

    @Override
    public void onStart() {

        super.onStart();
        final String login = PreferenceUtils.getLogin();
        mTweetAsyncTask = new RetrieveTweetsAsyncTask(this);
        mTweetAsyncTask.execute(login);
    }

    @Override
    public void onTweetRetrieved(List<Tweet> tweets) {
        // Set the adapter
        final TweetsAdapter adapter = new TweetsAdapter(tweets);
        adapter.setTweetListener(mListener);
        mListView.setAdapter(adapter);
        // Set our asynctask to null
        mTweetAsyncTask = null;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (activity instanceof TweetListener){
            mListener = (TweetListener) activity;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
        if (null != mListener){
            final Tweet tweet = (Tweet) adapter.getItemAtPosition(position);
            mListener.onViewTweet(tweet);
        }
    }
}
