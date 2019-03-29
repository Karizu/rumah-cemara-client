package cemara.labschool.id.rumahcemara.util.news.model;

import cemara.labschool.id.rumahcemara.model.UserCreator;

public class News {
    private String newsId;
    private String newsCategoryId;
    private String title;
    private String content;
    private String author;
    private String dateCreated;
    private int newsImage;
    private String banner;
    private UserCreator userCreator;

    public News(String newsId, String newsCategoryId, String title, String author, String dateCreated, String banner, String content, UserCreator userCreator){
        this.newsId = newsId;
        this.newsCategoryId = newsCategoryId;
        this.title = title;
        this.author = author;
        this.dateCreated = dateCreated;
        this.banner = banner;
        this.content = content;
        this.userCreator = userCreator;
    }
    public News(String newsId, String title, String author, String dateCreated, int newsImage){
        this.newsId = newsId;
        this.title = title;
        this.author = author;
        this.dateCreated = dateCreated;
        this.newsImage = newsImage;
    }
    public News(String newsId, String title, String author, String dateCreated, String banner){
        this.newsId = newsId;
        this.title = title;
        this.author = author;
        this.dateCreated = dateCreated;
        this.banner = banner;
    }
    public News(String newsId, String title, String author, String dateCreated){
        this.newsId = newsId;
        this.title = title;
        this.author = author;
        this.dateCreated = dateCreated;
    }
    public String getNewsId(){
        return newsId;
    }
    public void setNewsId(String newsId){
        this.newsId = newsId;
    }

    public String getNewsCategoryId() {
        return newsCategoryId;
    }

    public void setNewsCategoryId(String newsCategoryId) {
        this.newsCategoryId = newsCategoryId;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UserCreator getUserCreator() {
        return userCreator;
    }

    public void setUserCreator(UserCreator userCreator) {
        this.userCreator = userCreator;
    }
}
