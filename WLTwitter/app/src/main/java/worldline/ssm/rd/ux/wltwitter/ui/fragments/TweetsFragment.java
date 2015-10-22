package worldline.ssm.rd.ux.wltwitter.ui.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import worldline.ssm.rd.ux.wltwitter.R;
import worldline.ssm.rd.ux.wltwitter.WLTwitterActivity;
import worldline.ssm.rd.ux.wltwitter.async.RetrieveTweetsAsyncTask;
import worldline.ssm.rd.ux.wltwitter.interfaces.TweetChangeListener;
import worldline.ssm.rd.ux.wltwitter.pojo.Tweet;
import worldline.ssm.rd.ux.wltwitter.utils.PreferenceUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class TweetsFragment extends Fragment implements TweetChangeListener {

    // Keep a reference to the AsyncTask
    private RetrieveTweetsAsyncTask mTweetAsyncTask;

    public TweetsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_wltwitter, container, false);
    }


    @Override
    public void onStart() {
        super.onStart();

        // On start launch our AsyncTask to retrieve the Tweets of the user
        final String login = PreferenceUtils.getLogin();
        if (!TextUtils.isEmpty(login)){
            mTweetAsyncTask = new RetrieveTweetsAsyncTask(this);
            mTweetAsyncTask.execute(login);
        }
    }

    @Override
    public void onTweetRetrieved(List<Tweet> tweets) {
        for (int i=0;i<tweets.size();i++)
            Log.d(WLTwitterActivity.class.getName(), tweets.get(i).text);
        // Set our asynctask to null
        mTweetAsyncTask = null;
    }

    @Override
    public void onStop() {
        super.onStop();

        // If we have an AsyncTask running, close it
        if (null != mTweetAsyncTask){
            mTweetAsyncTask.cancel(true);
        }
    }
}
