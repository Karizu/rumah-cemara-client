package cemara.labschool.id.rumahcemara.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TopicMessage {
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("topic_id")
    @Expose
    private String topic_id;

    @SerializedName("message")
    @Expose
    private String message;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTopic_id() {
        return topic_id;
    }

    public void setTopic_id(String topic_id) {
        this.topic_id = topic_id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
