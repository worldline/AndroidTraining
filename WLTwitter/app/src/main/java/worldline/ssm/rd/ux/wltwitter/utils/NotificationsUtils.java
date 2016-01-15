package worldline.ssm.rd.ux.wltwitter.utils;

import worldline.ssm.rd.ux.wltwitter.R;
import worldline.ssm.rd.ux.wltwitter.WLTwitterApplication;
import worldline.ssm.rd.ux.wltwitter.WLTwitterLoginActivity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

public class NotificationsUtils {

	public static void displayNewTweetsNotification(int nbTweets, boolean vibrate, boolean playSound){
		// Retrieve the context
		final Context context = WLTwitterApplication.getContext();

		// Create the notification, add an icon, a title and the content to display
		final NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
		.setSmallIcon(R.drawable.ic_launcher)
		.setContentTitle(context.getString(R.string.app_name))
		.setContentText(String.format(context.getString(R.string.notification_content), nbTweets))
		.setAutoCancel(true);

		// Create an intent to handle click on the notification, and display the list of Tweets
		final Intent newIntent = new Intent(context, WLTwitterLoginActivity.class);
		newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

		// Create a stack builder, so when the user click on the notification, it goes directly
		// int the app to see user profile, but when clicking back button, it'll return to home screen
		final TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
		stackBuilder.addParentStack(WLTwitterLoginActivity.class);
		stackBuilder.addNextIntent(newIntent);
		final PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
		mBuilder.setContentIntent(resultPendingIntent);

		// Build the final notification
		final Notification notification = mBuilder.build();

		// Vibrate if needed
		if (vibrate){
			notification.defaults = Notification.DEFAULT_VIBRATE;
		}

		// Play sound if need
		if (playSound){
			notification.sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
		}

		// Finally display the notification
		final NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		mNotificationManager.notify(42, notification);
	}

}
