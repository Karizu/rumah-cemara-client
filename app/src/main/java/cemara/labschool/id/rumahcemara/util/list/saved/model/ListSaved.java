package cemara.labschool.id.rumahcemara.util.list.saved.model;

public class ListSaved {
    private String id;
    private String title;
    private String author;
    private String dateCreated;
    private int newsImage;
    private String banner;
    private boolean isArticle;

    public ListSaved(String id, String title, String author, String dateCreated, int newsImage, String banner,boolean isArticle) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.dateCreated = dateCreated;
        this.newsImage = newsImage;
        this.banner = banner;
        this.isArticle=isArticle;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public boolean isArticle() {
        return isArticle;
    }

    public void setArticle(boolean article) {
        isArticle = article;
    }
}
