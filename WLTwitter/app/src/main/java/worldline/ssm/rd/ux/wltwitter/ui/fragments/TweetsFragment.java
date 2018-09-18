package worldline.ssm.rd.ux.wltwitter.ui.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import worldline.ssm.rd.ux.wltwitter.R;
import worldline.ssm.rd.ux.wltwitter.async.RetrieveTweetsAsyncTask;
import worldline.ssm.rd.ux.wltwitter.interfaces.TweetChangeListener;
import worldline.ssm.rd.ux.wltwitter.pojo.Tweet;
import worldline.ssm.rd.ux.wltwitter.utils.PreferenceUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class TweetsFragment extends Fragment implements TweetChangeListener{

    private RetrieveTweetsAsyncTask mTweetAsyncTask;
    private ListView mListView;
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
        final ArrayAdapter<Tweet> adapter = new ArrayAdapter<Tweet>(getActivity(),
                android.R.layout.simple_list_item_1, tweets);
        mListView.setAdapter(adapter);

        for (Tweet tweet:tweets) {
            //System.out.println(tweet.text);
            Log.d("result",tweet.text);
        }
    }
}
