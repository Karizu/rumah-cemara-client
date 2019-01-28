package cemara.labschool.id.rumahcemara.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Chat extends RealmObject {

    @SerializedName("id")
    @Expose
    @PrimaryKey
    private String id;

    @SerializedName("channel")
    @Expose
    private String channel;

    @SerializedName("from_id")
    @Expose
    private String from_id;

    @SerializedName("from_name")
    @Expose
    private String from_name;

    @SerializedName("message_type")
    @Expose
    private String message_type;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("created_at")
    @Expose
    private String created_at;

    @SerializedName("service_transaction_id")
    @Expose
    private String service_transaction_id;

    @SerializedName("to_id")
    @Expose
    private String to_id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getFrom_id() {
        return from_id;
    }

    public void setFrom_id(String from_id) {
        this.from_id = from_id;
    }

    public String getFrom_name() {
        return from_name;
    }

    public void setFrom_name(String from_name) {
        this.from_name = from_name;
    }

    public String getMessage_type() {
        return message_type;
    }

    public void setMessage_type(String message_type) {
        this.message_type = message_type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getService_transaction_id() {
        return service_transaction_id;
    }

    public void setService_transaction_id(String service_transaction_id) {
        this.service_transaction_id = service_transaction_id;
    }

    public String getTo_id() {
        return to_id;
    }

    public void setTo_id(String to_id) {
        this.to_id = to_id;
    }
}
