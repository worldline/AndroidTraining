package worldline.ssm.rd.ux.wltwitter.interfaces;

import java.util.List;

import worldline.ssm.rd.ux.wltwitter.pojo.Tweet;

public interface TweetChangeListener {

	public void onTweetRetrieved(List<Tweet> tweets);
	
}
