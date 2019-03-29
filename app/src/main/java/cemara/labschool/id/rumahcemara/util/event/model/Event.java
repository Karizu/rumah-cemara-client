package cemara.labschool.id.rumahcemara.util.event.model;

import cemara.labschool.id.rumahcemara.model.UserCreator;

public class Event {
    private String eventId;
    private String title;
    private String author;
    private String dateCreated;
    private int newsImage;
    private String banner;
    private UserCreator userCreator;

    public Event(String eventId, String title, String author, String dateCreated, int newsImage, UserCreator userCreator){
        this.eventId = eventId;
        this.title = title;
        this.author = author;
        this.dateCreated = dateCreated;
        this.newsImage = newsImage;
        this.userCreator = userCreator;
    }
    public Event(String eventId, String title, String author, String dateCreated, String banner){
        this.eventId = eventId;
        this.title = title;
        this.author = author;
        this.dateCreated = dateCreated;
        this.banner = banner;
    }
    public Event(String eventId, String title, String author, String dateCreated){
        this.eventId = eventId;
        this.title = title;
        this.author = author;
        this.dateCreated = dateCreated;
    }
    public String getEventId(){
        return eventId;
    }
    public void setEventId(String eventId){
        this.eventId = eventId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getNewsImage() {
        return newsImage;
    }

    public void setNewsImage(int newsImage) {
        this.newsImage = newsImage;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public UserCreator getUserCreator() {
        return userCreator;
    }

    public void setUserCreator(UserCreator userCreator) {
        this.userCreator = userCreator;
    }
}
