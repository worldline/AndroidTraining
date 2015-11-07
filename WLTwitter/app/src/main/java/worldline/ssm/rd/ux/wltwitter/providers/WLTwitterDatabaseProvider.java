package worldline.ssm.rd.ux.wltwitter.providers;


import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import worldline.ssm.rd.ux.wltwitter.database.WLTwitterDatabaseContract;
import worldline.ssm.rd.ux.wltwitter.database.WLTwitterDatabaseHelper;
import worldline.ssm.rd.ux.wltwitter.utils.Constants;

public class WLTwitterDatabaseProvider extends ContentProvider{

    // Our database helper
    private WLTwitterDatabaseHelper mDBHelper;

    // The URI matcher to check if the URI is correct
    private UriMatcher mUriMatcher;

    // The URI matcher code for correct result
    private static final int TWEET_CORRECT_URI_CODE = 42;

    @Override
    public boolean onCreate() {
        mDBHelper = new WLTwitterDatabaseHelper(getContext());
        mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        mUriMatcher.addURI(WLTwitterDatabaseContract.CONTENT_PROVIDER_TWEETS_AUTHORITY,
                WLTwitterDatabaseContract.TABLE_TWEETS, TWEET_CORRECT_URI_CODE);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Log.i(Constants.General.LOG_TAG, "QUERY");
        if (mUriMatcher.match(uri) == TWEET_CORRECT_URI_CODE){
            try {
                SQLiteDatabase db = mDBHelper.getReadableDatabase();
                Cursor c = db.query(WLTwitterDatabaseContract.TABLE_TWEETS, projection, selection, selectionArgs, null, null, WLTwitterDatabaseContract.ORDER_BY_DATE_CREATED_TIMESTAMP_DESCENDING);
                //c.setNotificationUri(getContext().getContentResolver(), uri);
                return c;
            } catch (Exception e){
                return null;
            }
        }
        return null;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        if (mUriMatcher.match(uri) == TWEET_CORRECT_URI_CODE){
            return WLTwitterDatabaseContract.TWEETS_CONTENT_TYPE;
        }
        throw new IllegalArgumentException("Unknown URI " + uri);
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Log.e(Constants.General.LOG_TAG, "INSERT");
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        Log.e(Constants.General.LOG_TAG, "DELETE");
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        Log.e(Constants.General.LOG_TAG, "UPDATE");
        return 0;
    }
}
