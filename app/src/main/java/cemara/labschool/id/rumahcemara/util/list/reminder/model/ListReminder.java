package cemara.labschool.id.rumahcemara.util.list.reminder.model;

public class ListReminder {
    private String eventId;
    private String idList;
    private String title;
    private String address;
    private int newsImage;
    private String banner;

    public ListReminder(String eventId, String title, String address, int newsImage, String banner) {
        this.eventId = eventId;
        this.title = title;
        this.address = address;
        this.newsImage = newsImage;
        this.banner = banner;
    }

    public ListReminder(String eventId, String idList, String title, String address, int newsImage, String banner) {
        this.eventId = eventId;
        this.idList = idList;
        this.title = title;
        this.address = address;
        this.newsImage = newsImage;
        this.banner = banner;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getIdList() {
        return idList;
    }

    public void setIdList(String idList) {
        this.idList = idList;
    }
}
