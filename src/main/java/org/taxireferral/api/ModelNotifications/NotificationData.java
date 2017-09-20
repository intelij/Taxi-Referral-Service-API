package org.taxireferral.api.ModelNotifications;

/**
 * Created by sumeet on 2/8/17.
 */
public class NotificationData {

    /* Notification Types */

    public static final int NOTIFICATION_TYPE_TAXI_REQUEST = 1;
    public static final int NOTIFICATION_TYPE_CURRENT_TRIP = 2;



    /* Notification subtypes for taxi request */

    public static final int NOTIFICATION_SUB_TYPE_TAXI_REQUEST_REQUEST_RECEIVED = 1; // for driver
    public static final int NOTIFICATION_SUB_TYPE_TAXI_REQUEST_REQUEST_ACCEPTED = 2; // for end user
    public static final int NOTIFICATION_SUB_TYPE_TAXI_REQUEST_PICKUP_REQUESTED = 3; // for driver
    public static final int NOTIFICATION_SUB_TYPE_TAXI_REQUEST_PICKUP_STARTED = 4; // for end user


    /* Notification subtypes for current trip */

    public static final int NOTIFICATION_SUB_TYPE_CURRENT_TRIP_START_REQUESTED = 1; // for driver
    public static final int NOTIFICATION_SUB_TYPE_CURRENT_TRIP_TRIP_STARTED = 2; // for end user
    public static final int NOTIFICATION_SUB_TYPE_CURRENT_TRIP_TRIP_FINISHED = 3; // for end user

    public static final int NOTIFICATION_SUB_TYPE_CURRENT_TRIP_TRIP_CANCELLED_BY_DRIVER = 4; // for end user
    public static final int NOTIFICATION_SUB_TYPE_CURRENT_TRIP_TRIP_CANCELLED_BY_END_USER = 5; // for driver


    private int notificationType;
    private int notificationSubType;




    public int getNotificationSubType() {
        return notificationSubType;
    }

    public void setNotificationSubType(int notificationSubType) {
        this.notificationSubType = notificationSubType;
    }

    public int getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(int notificationType) {
        this.notificationType = notificationType;
    }
}
