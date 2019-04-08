package cemara.labschool.id.rumahcemara.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class GroupProfile extends RealmObject {

    @SerializedName("group_id")
    @Expose
    private String group_id;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("phone_number")
    @Expose
    private String phone_number;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("picture")
    @Expose
    private String picture;

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}