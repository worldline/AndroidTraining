package worldline.ssm.rd.ux.wltwitter.async;

import java.util.List;

import worldline.ssm.rd.ux.wltwitter.WLTwitterActivity;
import worldline.ssm.rd.ux.wltwitter.helpers.TwitterHelper;
import worldline.ssm.rd.ux.wltwitter.pojo.Tweet;
import android.os.AsyncTask;
import android.util.Log;

public class RetrieveTweetsAsyncTask extends AsyncTask<String, Void, List<Tweet>>{


	public RetrieveTweetsAsyncTask(){
	}

	@Override
	protected List<Tweet> doInBackground(String... params) {
		if ((null != params) && (params.length > 0)){
			return TwitterHelper.getTweetsOfUser(params[0]);
		}
		return null;
	}

	@Override
	protected void onPostExecute(List<Tweet> result) {
		if (null != result){
			for (int i=0;i<result.size();i++)
				Log.d(WLTwitterActivity.class.getName(), result.get(i).text);
		}
	}

}
