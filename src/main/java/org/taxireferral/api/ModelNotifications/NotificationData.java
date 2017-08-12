package org.taxireferral.api.ModelNotifications;

/**
 * Created by sumeet on 2/8/17.
 */
public class NotificationData {

    public static final int NOTIFICATION_TYPE_TAXI_REQUEST = 1;
    public static final int NOTIFICATION_TYPE_CURRENT_TRIP = 2;

    private int notificationType;

    public int getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(int notificationType) {
        this.notificationType = notificationType;
    }
}
