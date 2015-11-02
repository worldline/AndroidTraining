package worldline.ssm.rd.ux.wltwitter.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class WLTwitterDatabaseHelper extends SQLiteOpenHelper {

	// The current database version
	private static final int DATABASE_VERSION = 1;
	
	// The current database name
	private static final String DATABASE_NAME = "tweets.db";
	
	public WLTwitterDatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(WLTwitterDatabaseContract.TABLE_TWEETS_CREATE_SCRIPT);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + WLTwitterDatabaseContract.TABLE_TWEETS);
		onCreate(db);
	}

}
