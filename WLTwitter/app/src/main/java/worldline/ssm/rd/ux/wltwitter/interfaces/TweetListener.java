package worldline.ssm.rd.ux.wltwitter.interfaces;

import worldline.ssm.rd.ux.wltwitter.pojo.Tweet;

public interface TweetListener {
    public void onRetweet(Tweet tweet);
    public void onViewTweet(Tweet tweet);

}
