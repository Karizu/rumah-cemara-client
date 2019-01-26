package cemara.labschool.id.rumahcemara.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Topic {
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("user_id")
    @Expose
    private String user_id;

    @SerializedName("question")
    @Expose
    private String question;

    @SerializedName("status")
    @Expose
    private int status;

    @SerializedName("created_at")
    @Expose
    private String created_at;

    @SerializedName("user")
    @Expose
    private User user;

    @SerializedName("messages")
    @Expose
    private List<TopicMessage> message;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<TopicMessage> getMessage() {
        return message;
    }

    public void setMessage(List<TopicMessage> message) {
        this.message = message;
    }
}
