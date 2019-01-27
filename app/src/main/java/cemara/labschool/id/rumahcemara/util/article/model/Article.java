package cemara.labschool.id.rumahcemara.util.article.model;

public class Article {
    private String articleId;
    private String title;
    private String author;
    private String dateCreated;
    private int newsImage;
    private String banner;

    public Article(String articleId, String title, String author, String dateCreated, int newsImage){
        this.articleId = articleId;
        this.title = title;
        this.author = author;
        this.dateCreated = dateCreated;
        this.newsImage = newsImage;
    }
    public Article(String articleId, String title, String author, String dateCreated, String banner){
        this.articleId = articleId;
        this.title = title;
        this.author = author;
        this.dateCreated = dateCreated;
        this.banner = banner;
    }
    public Article(String articleId, String title, String author, String dateCreated){
        this.articleId = articleId;
        this.title = title;
        this.author = author;
        this.dateCreated = dateCreated;
    }
    public String getArticleId(){
        return articleId;
    }
    public void setArticleId(String articleId){
        this.articleId = articleId;
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
}
