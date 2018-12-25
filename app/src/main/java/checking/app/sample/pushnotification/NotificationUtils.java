package checking.app.sample.pushnotification;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.text.Html;
import android.text.TextUtils;
import android.util.Patterns;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import checking.app.sample.R;


/**
 * Created by Ravi on 31/03/15.
 */
public class NotificationUtils {

    private static String TAG = NotificationUtils.class.getSimpleName();

    private Context mContext;

    public NotificationUtils(Context mContext) {
        this.mContext = mContext;
    }

    public static boolean isAppIsInBackground(Context context) {
        boolean isInBackground = true;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    for (String activeProcess : processInfo.pkgList) {
                        if (activeProcess.equals(context.getPackageName())) {
                            isInBackground = false;
                        }
                    }
                }
            }
        } else {
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
            ComponentName componentInfo = taskInfo.get(0).topActivity;
            if (componentInfo.getPackageName().equals(context.getPackageName())) {
                isInBackground = false;
            }
        }

        return isInBackground;
    }

    public static void clearNotifications(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }

    public static long getTimeMilliSec(String timeStamp) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = format.parse(timeStamp);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void showNotificationMessage(final String title, final String message, final String timeStamp,
                                        Intent intent, String imageUrl, String notificationId) {

        if (TextUtils.isEmpty(message))
            return;
        final int icon = R.mipmap.ic_launcher;
        intent.putExtra("IMAGE", imageUrl);
        intent.putExtra("TITLE", title);
        intent.putExtra("Notficationids", notificationId);
        final PendingIntent resultPendingIntent = PendingIntent.getActivity(mContext, Integer.parseInt(notificationId),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        final NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mContext);
        if (!TextUtils.isEmpty(imageUrl)) {
            if (imageUrl != null && imageUrl.length() > 4 && Patterns.WEB_URL.matcher(imageUrl).matches()) {
                Bitmap bitmap = getBitmapFromURL(imageUrl);
                if (bitmap != null) {
                    showBigNotification(bitmap, imageUrl, mBuilder, icon, title, message, timeStamp, resultPendingIntent, notificationId);
                } else {

                }
            }
        } else {

        }
    }

    private void showBigNotification(Bitmap bitmap, final String imageUrl, NotificationCompat.Builder mBuilder, int icon, String title,
                                     String message, String timeStamp, PendingIntent resultPendingIntent, String notificationId1) {
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Intent yesReceive2 = new Intent();
        yesReceive2.setAction(Config.YES_ACTION);
        yesReceive2.putExtra("IMAGE", imageUrl);
        yesReceive2.putExtra("TITLE", title);
        yesReceive2.putExtra("NOTIFYID", notificationId1);
        PendingIntent share_intent = PendingIntent.getBroadcast(mContext, Integer.parseInt(notificationId1), yesReceive2, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification;
        notification = mBuilder.setSmallIcon(icon).setTicker(title).setWhen(0)
                .setAutoCancel(true)
                .setContentTitle(title)
                .setContentIntent(resultPendingIntent)
                .setStyle(new NotificationCompat.BigPictureStyle()
                        .setSummaryText(Html.fromHtml(message).toString())
                        .bigPicture(bitmap))
                .setWhen(getTimeMilliSec(timeStamp))
                .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), icon))
                .setContentText(message)
                .setSound(defaultSoundUri)
                .build();

        NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(Integer.parseInt(notificationId1), notification);

    }


    public Bitmap getBitmapFromURL(String strURL) {
        try {
            URL url = new URL(strURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
