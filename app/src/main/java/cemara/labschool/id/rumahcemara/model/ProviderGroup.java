package cemara.labschool.id.rumahcemara.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ProviderGroup extends RealmObject {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("parent_id")
    @Expose
    private String parent_id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("is_parent")
    @Expose
    private String is_parent;

    @SerializedName("created_at")
    @Expose
    private Integer created_at;

    @SerializedName("updated_at")
    @Expose
    private String updated_at;

    @SerializedName("deleted_at")
    @Expose
    private String deleted_at;

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("group_profile")
    @Expose
    private GroupProfile group_profile;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIs_parent() {
        return is_parent;
    }

    public void setIs_parent(String is_parent) {
        this.is_parent = is_parent;
    }

    public Integer getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Integer created_at) {
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public GroupProfile getGroup_profile() {
        return group_profile;
    }

    public void setGroup_profile(GroupProfile group_profile) {
        this.group_profile = group_profile;
    }
}
