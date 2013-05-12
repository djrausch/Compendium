package djraus.ch.Compendium.model;

public class Event {
    private int eventId;
    private String title;
    private long timestamp;
    private String notificationTitle;
    private String notificationTicker;
    private String notificationDescription;

    public Event(int eventId, String title, long timestamp, String notificationTitle, String notificationTicker, String notificationDescription){
        this.eventId = eventId;
        this.title = title;
        this.timestamp = timestamp;
        this.notificationTitle = notificationTitle;
        this.notificationTicker = notificationTicker;
        this.notificationDescription = notificationDescription;
    }

    public int getEventId() {
        return eventId;
    }

    public String getTitle() {
        return title;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getNotificationTitle() {
        return notificationTitle;
    }

    public String getNotificationTicker() {
        return notificationTicker;
    }

    public String getNotificationDescription() {
        return notificationDescription;
    }
}
