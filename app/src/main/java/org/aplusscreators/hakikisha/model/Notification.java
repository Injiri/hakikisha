package org.aplusscreators.hakikisha.model;

public class Notification {

    private String uuid;
    private String title;
    private int icon_drawable;

    public Notification() {
    }

    public Notification(String uuid, String title, int icon_drawable) {
        this.uuid = uuid;
        this.title = title;
        this.icon_drawable = icon_drawable;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIcon_drawable() {
        return icon_drawable;
    }

    public void setIcon_drawable(int icon_drawable) {
        this.icon_drawable = icon_drawable;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "uuid='" + uuid + '\'' +
                ", title='" + title + '\'' +
                ", icon_drawable=" + icon_drawable +
                '}';
    }
}
