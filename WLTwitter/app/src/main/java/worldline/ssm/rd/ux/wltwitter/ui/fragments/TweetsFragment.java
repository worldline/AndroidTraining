package worldline.ssm.rd.ux.wltwitter.ui.fragments;


import android.app.ActionBar;
import android.app.Activity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import worldline.ssm.rd.ux.wltwitter.R;
import worldline.ssm.rd.ux.wltwitter.WLTwitterActivity;
import worldline.ssm.rd.ux.wltwitter.WLTwitterApplication;
import worldline.ssm.rd.ux.wltwitter.adapters.TweetsAdapter;
import worldline.ssm.rd.ux.wltwitter.adapters.TweetsCursorAdapter;
import worldline.ssm.rd.ux.wltwitter.async.RetrieveTweetsAsyncTask;
import worldline.ssm.rd.ux.wltwitter.database.WLTwitterDatabaseContract;
import worldline.ssm.rd.ux.wltwitter.database.WLTwitterDatabaseManager;
import worldline.ssm.rd.ux.wltwitter.interfaces.TweetChangeListener;
import worldline.ssm.rd.ux.wltwitter.interfaces.TweetListener;
import worldline.ssm.rd.ux.wltwitter.pojo.Tweet;
import worldline.ssm.rd.ux.wltwitter.utils.PreferenceUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class TweetsFragment extends Fragment implements AdapterView.OnItemClickListener, LoaderManager.LoaderCallbacks<Cursor> {

    // Keep a reference to the AsyncTask
    private RetrieveTweetsAsyncTask mTweetAsyncTask;

    // Keep a reference to the ListView
    private ListView mListView;

    // Keep a reference to our Activiyt (implementing TweetListener)
    private TweetListener mListener;

    // Our adapter
    private TweetsCursorAdapter mAdapter;

    public TweetsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_wltwitter, container, false);

        // Get the ListView
        mListView = (ListView) rootView.findViewById(R.id.tweetsListView);

        // Set a Progress Bar as empty view, and display it (set adapter with no elements))
        final ProgressBar progressBar = new ProgressBar(getActivity());
        progressBar.setLayoutParams(new ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT,
                                                               ActionBar.LayoutParams.WRAP_CONTENT,
                                                               Gravity.CENTER));
        progressBar.setIndeterminate(true);
        mListView.setEmptyView(progressBar);

        // Add the view in our content view
        ViewGroup root = (ViewGroup) rootView.findViewById(R.id.tweetsRootRelativeLayout);
        root.addView(progressBar);
		
		// Set adapter with no elements to let the ListView display the empty view
        mAdapter = new TweetsCursorAdapter(getActivity(), null, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        mAdapter.setTweetListener(mListener);
        mListView.setAdapter(mAdapter);

        // Add a listener when an item is clicked
        mListView.setOnItemClickListener(this);

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (activity instanceof TweetListener){
            mListener = (TweetListener) activity;
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        // On start launch our AsyncTask to retrieve the Tweets of the user
        final String login = PreferenceUtils.getLogin();

        //Load data using CursorLoader
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public void onStop() {
        super.onStop();

        // If we have an AsyncTask running, close it
        if (null != mTweetAsyncTask){
            mTweetAsyncTask.cancel(true);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
        if (null != mListener){
            final TweetsCursorAdapter.ViewHolder holder = (TweetsCursorAdapter.ViewHolder) view.getTag();
            final Tweet tweet = (Tweet) holder.button.getTag();
            mListener.onViewTweet(tweet);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        final CursorLoader cursorLoader = new CursorLoader(WLTwitterApplication.getContext());
        cursorLoader.setUri(WLTwitterDatabaseContract.TWEETS_URI);
        cursorLoader.setProjection(WLTwitterDatabaseContract.PROJECTION_FULL);
        cursorLoader.setSelection(null);
        cursorLoader.setSelectionArgs(null);
        cursorLoader.setSortOrder(null);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (null != mAdapter){
            mAdapter.changeCursor(cursor);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
