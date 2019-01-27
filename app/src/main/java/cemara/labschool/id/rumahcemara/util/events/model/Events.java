package cemara.labschool.id.rumahcemara.util.events.model;

public class Events {
    private String eventId;
    private String eventTitle;
    private String eventAddress;
    private String eventCreated;
    private int eventImage;

    public Events(String eventId, String eventTitle, String eventAddress, int eventImage){
        this.eventId = eventId;
        this.eventTitle = eventTitle;
        this.eventAddress = eventAddress;
        this.eventImage = eventImage;
    }
    public Events(String eventId, String eventTitle, String eventAddress, String eventCreated, int eventImage){
        this.eventId = eventId;
        this.eventTitle = eventTitle;
        this.eventAddress = eventAddress;
        this.eventCreated = eventCreated;
        this.eventImage = eventImage;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public String getEventAddress() {
        return eventAddress;
    }

    public void setEventAddress(String eventAddress) {
        this.eventAddress = eventAddress;
    }

    public String getEventCreated() {
        return eventCreated;
    }

    public void setEventCreated(String eventCreated) {
        this.eventCreated = eventCreated;
    }

    public int getEventImage() {
        return eventImage;
    }

    public void setEventImage(int eventImage) {
        this.eventImage = eventImage;
    }
}
