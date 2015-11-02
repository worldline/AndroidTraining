package worldline.ssm.rd.ux.wltwitter.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;

import java.util.List;

import worldline.ssm.rd.ux.wltwitter.WLTwitterApplication;
import worldline.ssm.rd.ux.wltwitter.pojo.Tweet;
import worldline.ssm.rd.ux.wltwitter.pojo.TwitterUser;
import worldline.ssm.rd.ux.wltwitter.utils.Constants;

public class WLTwitterDatabaseManager {

	public static ContentValues tweetToContentValues(Tweet tweet){
		final ContentValues values = new ContentValues();

		// Set the date created
		if (!TextUtils.isEmpty(tweet.dateCreated)){
			values.put(WLTwitterDatabaseContract.DATE_CREATED, tweet.dateCreated);
		}

		// Set the date created as timestamp
		values.put(WLTwitterDatabaseContract.DATE_CREATED_TIMESTAMP, tweet.getDateCreatedTimestamp());
		
		// Set the user name
		if (!TextUtils.isEmpty(tweet.user.name)){
			values.put(WLTwitterDatabaseContract.USER_NAME, tweet.user.name);
		}

		// Set the user alias
		if (!TextUtils.isEmpty(tweet.user.screenName)){
			values.put(WLTwitterDatabaseContract.USER_ALIAS, tweet.user.screenName);
		}

		// Set the user image url
		if (!TextUtils.isEmpty(tweet.user.profileImageUrl)){
			values.put(WLTwitterDatabaseContract.USER_IMAGE_URL, tweet.user.profileImageUrl);
		}

		// Set the text of the tweet
		if (!TextUtils.isEmpty(tweet.text)){
			values.put(WLTwitterDatabaseContract.TEXT, tweet.text);
		}

		return values;
	}

	public static void testDatabase(List<Tweet> tweets){
		final WLTwitterDatabaseHelper dbHelper = new WLTwitterDatabaseHelper(WLTwitterApplication.getContext());
		final SQLiteDatabase db = dbHelper.getWritableDatabase();

		// First insert all values in database
		for (Tweet tweet : tweets){
			db.insert(WLTwitterDatabaseContract.TABLE_TWEETS, "", tweetToContentValues(tweet));
			Log.w(Constants.General.LOG_TAG, "Tweet stored");
			Log.w(Constants.General.LOG_TAG, tweet.toString());
			Log.w(Constants.General.LOG_TAG, "----------------------");
		}
	}

}
