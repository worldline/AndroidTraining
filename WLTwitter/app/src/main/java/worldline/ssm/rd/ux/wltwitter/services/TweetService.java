package worldline.ssm.rd.ux.wltwitter.services;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;

import java.util.List;

import worldline.ssm.rd.ux.wltwitter.WLTwitterApplication;
import worldline.ssm.rd.ux.wltwitter.async.RetrieveTweetsAsyncTask;
import worldline.ssm.rd.ux.wltwitter.database.WLTwitterDatabaseManager;
import worldline.ssm.rd.ux.wltwitter.interfaces.TweetChangeListener;
import worldline.ssm.rd.ux.wltwitter.pojo.Tweet;
import worldline.ssm.rd.ux.wltwitter.utils.Constants;
import worldline.ssm.rd.ux.wltwitter.utils.PreferenceUtils;

public class TweetService extends Service implements TweetChangeListener {
    public TweetService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this,"Service Started",Toast.LENGTH_LONG).show();
        new RetrieveTweetsAsyncTask(this).execute(PreferenceUtils.getLogin());
        return Service.START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onTweetRetrieved(List<Tweet> tweets) {
        // The number of tweet inserted in database
        int nbTweetInserted = 0;

        // Insert all tweets
        for (Tweet tweet : tweets){
            final int id = WLTwitterDatabaseManager.insertTweet(tweet);
            if (id > -1){
                nbTweetInserted++;
            }
        }

        // If we inserted at least one tweet, broadcast a message
        if (nbTweetInserted > 0){
            final Intent newTweetsIntent = new Intent(Constants.General.ACTION_NEW_TWEETS);
            final Bundle extras = new Bundle();
            extras.putInt(Constants.General.ACTION_NEW_TWEETS_EXTRA_NB_TWEETS, nbTweetInserted);
            newTweetsIntent.putExtras(extras);
            sendBroadcast(newTweetsIntent);
        }

        stopSelf();
    }
}
