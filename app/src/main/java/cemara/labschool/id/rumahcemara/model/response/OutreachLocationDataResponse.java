package cemara.labschool.id.rumahcemara.model.response;

import cemara.labschool.id.rumahcemara.model.Group;
import cemara.labschool.id.rumahcemara.model.ProviderWorker;
import cemara.labschool.id.rumahcemara.model.Rating;
import cemara.labschool.id.rumahcemara.model.ServiceType;
import cemara.labschool.id.rumahcemara.model.User;
import cemara.labschool.id.rumahcemara.model.Worker;

public class OutreachLocationDataResponse {
    private String id;
    private String user_id;
    private String lat;
    private String longitude;
    private String description;
    private String created_at;
    private String updated_at;
    private String deleted_at;
    private String distance;
    private User user;
    private Group group;

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

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getDeleted_at() {
        return deleted_at;
    }

    public void setDeleted_at(String deleted_at) {
        this.deleted_at = deleted_at;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}
