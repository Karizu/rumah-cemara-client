package cemara.labschool.id.rumahcemara.util.broadcast.model;

import cemara.labschool.id.rumahcemara.model.UserCreator;

public class Broadcast {

    private String broadcastId;
    private String title;
    private String author;
    private String dateCreated;
    private int newsImage;
    private String banner;
    private UserCreator userCreator;

    public Broadcast(String broadcastId, String title, String author, String dateCreated, String banner){
        this.broadcastId = broadcastId;
        this.title = title;
        this.author = author;
        this.dateCreated = dateCreated;
        this.banner = banner;
    }

    public String getBroadcastId() {
        return broadcastId;
    }

    public void setBroadcastId(String broadcastId) {
        this.broadcastId = broadcastId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
