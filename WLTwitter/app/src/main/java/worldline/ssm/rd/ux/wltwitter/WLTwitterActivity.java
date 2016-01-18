package worldline.ssm.rd.ux.wltwitter;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.FragmentTransaction;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.Calendar;

import worldline.ssm.rd.ux.wltwitter.database.WLTwitterDatabaseManager;
import worldline.ssm.rd.ux.wltwitter.interfaces.TweetListener;
import worldline.ssm.rd.ux.wltwitter.pojo.Tweet;
import worldline.ssm.rd.ux.wltwitter.services.TweetService;
import worldline.ssm.rd.ux.wltwitter.ui.fragments.TweetFragment;
import worldline.ssm.rd.ux.wltwitter.ui.fragments.TweetsFragment;
import worldline.ssm.rd.ux.wltwitter.utils.NotificationsUtils;
import worldline.ssm.rd.ux.wltwitter.utils.PreferenceUtils;
import worldline.ssm.rd.ux.wltwitter.utils.Constants;


public class WLTwitterActivity extends Activity implements TweetListener {

    // The PendingIntent to call service
    private PendingIntent mServicePendingIntent;

    // The receiver for new tweets
    private NewTweetsReceiver mReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wltwitter);

        // Retrieve the login passed as parameter
        final Intent intent = getIntent();
        if (null != intent){
            final Bundle extras = intent.getExtras();
            if ((null != extras) && (extras.containsKey(Constants.Login.EXTRA_LOGIN))){
                // Retrieve the login
                final String login = extras.getString(Constants.Login.EXTRA_LOGIN);

                // Set as ActionBar subtitle
                getActionBar().setSubtitle(login);

            }
        }

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction().add(R.id.container, new TweetsFragment()).commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.wltwitter, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.actionLogout) {
            // Erase login and password in Preferences
            PreferenceUtils.setLogin(null);
            PreferenceUtils.setPassword(null);
            // Finish this activity, and go back to LoginActivity
            finish();

            return true;
        }else  if (id == R.id.actionDropTable) {
            // Erase login and password in Preferences
            PreferenceUtils.setLogin(null);
            PreferenceUtils.setPassword(null);
            // Erase all datas in database
            WLTwitterDatabaseManager.dropDatabase();
            finish();
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRetweet(Tweet tweet) {
        Toast.makeText(this, tweet.text, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onViewTweet(Tweet tweet) {
        final FragmentTransaction transaction = getFragmentManager().beginTransaction();

        final TweetFragment fragment = new TweetFragment();
        final Bundle bundle = new Bundle();
        bundle.putString(Constants.Tweet.EXTRA_TEXT, tweet.text);
        bundle.putString(Constants.Tweet.EXTRA_IMAGEURL, tweet.user.profileImageUrl);
        bundle.putString(Constants.Tweet.EXTRA_NAME, tweet.user.name);
        bundle.putString(Constants.Tweet.EXTRA_ALIAS, tweet.user.screenName);
        fragment.setArguments(bundle);

        transaction.add(R.id.container, fragment);
        transaction.setCustomAnimations(R.animator.slide_in_right, 0, 0, R.animator.slide_out_left);
        transaction.addToBackStack(null).commit();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Register a receiver when new Tweets are downloaded
        mReceiver = new NewTweetsReceiver();
        registerReceiver(mReceiver, new IntentFilter(Constants.General.ACTION_NEW_TWEETS));

        // Schedule service to run every xx seconds (defined in Constants.Twitter.POLLING_DELAY)
        final Calendar cal = Calendar.getInstance();
        final Intent serviceIntent = new Intent(this, TweetService.class);
        mServicePendingIntent = PendingIntent.getService(this, 0, serviceIntent, 0);
        final AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), Constants.Twitter.POLLING_DELAY, mServicePendingIntent);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Unregister our receiver
        unregisterReceiver(mReceiver);
        mReceiver = null;

        // Cancel the service repetition
        final AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(mServicePendingIntent);
    }

    private class NewTweetsReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            final int nbNewTweets = intent.getExtras().getInt(Constants.General.ACTION_NEW_TWEETS_EXTRA_NB_TWEETS);

            // Display a notification
            NotificationsUtils.displayNewTweetsNotification(nbNewTweets, true, true);
        }

    }
}
