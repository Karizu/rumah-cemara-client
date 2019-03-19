package cemara.labschool.id.rumahcemara.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class News  {
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("news_category_id")
    @Expose
    private String news_category_id;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("created_at")
    @Expose
    private String createdAt;

    @SerializedName("user_creator")
    @Expose
    private UserCreator userCreator;

    @SerializedName("banner")
    @Expose
    private String banner;
    @SerializedName("content")
    @Expose
    private String content;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNews_category_id() {
        return news_category_id;
    }

    public void setNews_category_id(String news_category_id) {
        this.news_category_id = news_category_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public UserCreator getUserCreator() {
        return userCreator;
    }

    public void setUserCreator(UserCreator userCreator) {
        this.userCreator = userCreator;
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
}
