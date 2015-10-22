package worldline.ssm.rd.ux.wltwitter.async;

import java.util.List;

import worldline.ssm.rd.ux.wltwitter.WLTwitterActivity;
import worldline.ssm.rd.ux.wltwitter.helpers.TwitterHelper;
import worldline.ssm.rd.ux.wltwitter.interfaces.TweetChangeListener;
import worldline.ssm.rd.ux.wltwitter.pojo.Tweet;
import android.os.AsyncTask;
import android.util.Log;

public class RetrieveTweetsAsyncTask extends AsyncTask<String, Void, List<Tweet>>{

	// A reference to the listener
	private TweetChangeListener mListener;

	public RetrieveTweetsAsyncTask(TweetChangeListener listener){ mListener = listener; }
	
	@Override
	protected List<Tweet> doInBackground(String... params) {
		if ((null != params) && (params.length > 0)){
			return TwitterHelper.getTweetsOfUser(params[0]);
		}
		return null;
	}

	@Override
	protected void onPostExecute(List<Tweet> result) {
		if (null !=mListener && null != result){
			mListener.onTweetRetrieved(result);
		}
	}
	
}
