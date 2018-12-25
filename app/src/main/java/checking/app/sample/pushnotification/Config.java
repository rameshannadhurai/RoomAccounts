package checking.app.sample.pushnotification;

/**
 * Created by Sys-0 on 1/3/2018.
 */

public class Config {

    // global topic to receive app wide push notifications
    public static final String TOPIC_GLOBAL = "global";
    public static final String YES_ACTION = "YES_ACTION";
    // broadcast receiver intent filters
    public static final String REGISTRATION_COMPLETE = "registrationComplete";
    public static final String PUSH_NOTIFICATION = "pushNotification";
    public static final int NOTIFICATION_ID_BIG_IMAGE = 101;
    public static final String SHARED_PREF = "ah_firebase";
    // id to handle the notification in the notification tray
    public static String NOTIFICATION_ID = "";
}
