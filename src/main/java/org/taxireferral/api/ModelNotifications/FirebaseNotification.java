package org.taxireferral.api.ModelNotifications;

/**
 * Created by sumeet on 7/8/17.
 */
public class FirebaseNotification {


    private String to;
    private AndroidOptions android;
    private NotificationData data;


    public AndroidOptions getAndroid() {
        return android;
    }

    public void setAndroid(AndroidOptions android) {
        this.android = android;
    }

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
