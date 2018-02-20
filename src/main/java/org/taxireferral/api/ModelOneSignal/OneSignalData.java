package org.taxireferral.api.ModelOneSignal;

public class OneSignalData {

    public OneSignalData(int notificationType, int screen_to_open) {
        this.notificationType = notificationType;
        this.screen_to_open = screen_to_open;
    }

    private int notificationType;
    private int screen_to_open;



    public int getScreen_to_open() {
        return screen_to_open;
    }

    public void setScreen_to_open(int screen_to_open) {
        this.screen_to_open = screen_to_open;
    }

    public int getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(int notificationType) {
        this.notificationType = notificationType;
    }
}
