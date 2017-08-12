package org.taxireferral.api.ModelNotifications;

/**
 * Created by sumeet on 7/8/17.
 */
public class FirebaseNotification {


    private String to;
    private NotificationData data;


    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public NotificationData getData() {
        return data;
    }

    public void setData(NotificationData data) {
        this.data = data;
    }
}
