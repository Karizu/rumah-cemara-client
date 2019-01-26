package cemara.labschool.id.rumahcemara.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NearestOutreachModel {

    @SerializedName("id")
    @Expose
    private String id;
    private String user_id;
    private String srcImage;
    private String name;
    private String description;
    private String address;
    private String city;
    private String phoneNumber;
    private String distance;
    private User user;
    private Group group;

    public NearestOutreachModel(String id, String user_id, String srcImage, String name, String description, String address, String city, String phoneNumber, String distance,User user, Group group) {
        this.id = id;
        this.user_id = user_id;
        this.srcImage = srcImage;
        this.name = name;
        this.description = description;
        this.address = address;
        this.city = city;
        this.phoneNumber = phoneNumber;
        this.distance = distance;
        this.user = user;
        this.group = group;
    }

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

    public String getSrcImage() {
        return srcImage;
    }

    public void setSrcImage(String srcImage) {
        this.srcImage = srcImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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
