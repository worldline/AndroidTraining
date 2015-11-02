package worldline.ssm.rd.ux.wltwitter.database;

import android.net.Uri;
import android.provider.BaseColumns;

public class WLTwitterDatabaseContract implements BaseColumns {

	// Field names
	public static final String DATE_CREATED = "dateCreated";
	public static final String TEXT = "tweetText";
	public static final String USER_NAME = "userName";
	public static final String USER_ALIAS = "userAlias";
	public static final String USER_IMAGE_URL = "userImageUrl";
	public static final String DATE_CREATED_TIMESTAMP = "dateCreatedTimestamp";
	
	// Table name
	public static final String TABLE_TWEETS = "tweets";
	
	// Table scripts creation
	private static final String TABLE_GENERIC_CREATE_SCRIPT_PREFIX = "CREATE TABLE IF NOT EXISTS ";
	private static final String TABLE_IMAGES_CREATE_SCRIPT_SUFFIX = "(" + _ID + " INTEGER PRIMARY KEY, " +
			DATE_CREATED + " TEXT NOT NULL, " +
			DATE_CREATED_TIMESTAMP + " INTEGER, " + 
			TEXT + " TEXT NOT NULL, "+
			USER_NAME + " TEXT NOT NULL, "+
			USER_ALIAS + " TEXT NOT NULL, "+
			USER_IMAGE_URL + " TEXT NOT NULL)";

	public static final String TABLE_TWEETS_CREATE_SCRIPT = TABLE_GENERIC_CREATE_SCRIPT_PREFIX +
			TABLE_TWEETS + TABLE_IMAGES_CREATE_SCRIPT_SUFFIX; 

	
}
