package cemara.labschool.id.rumahcemara.model;

import android.support.v4.app.FragmentManager;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class Profile extends RealmObject{
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("group_id")
    @Expose
    private String groupId;
    @SerializedName("fullname")
    @Expose
    private String fullname;
    @SerializedName("phone_number")
    @Expose
    private String phoneNumber;
    @SerializedName("birth_place")
    @Expose
    private String birthPlace;
    @SerializedName("birth_date")
    @Expose
    private String birthDate;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("picture")
    @Expose
    private String picture;
    @SerializedName("hobby")
    @Expose
    private String hobby;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("work")
    @Expose
    private String work;
    @SerializedName("whatsapp_number")
    @Expose
    private String whatsupNumber;
    @SerializedName("line_account")
    @Expose
    private String lineAccount;
    @SerializedName("pin_bbm")
    @Expose
    private String pinBbm;
    @SerializedName("facebook_account")
    @Expose
    private String facebookAccount;
    @SerializedName("twitter_account")
    @Expose
    private String twitterAccount;
    @SerializedName("shirt_size")
    @Expose
    private String shirtSize;
    @SerializedName("group")
    @Expose
    private Group group;

    @SerializedName("privacy_config")
    @Expose
    private String privacyConfig;

    @SerializedName("instagram_account")
    private String instagramAccount;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public String getWhatsupNumber() {
        return whatsupNumber;
    }

    public void setWhatsupNumber(String whatsupNumber) {
        this.whatsupNumber = whatsupNumber;
    }

    public String getLineAccount() {
        return lineAccount;
    }

    public void setLineAccount(String lineAccount) {
        this.lineAccount = lineAccount;
    }

    public String getPinBbm() {
        return pinBbm;
    }

    public void setPinBbm(String pinBbm) {
        this.pinBbm = pinBbm;
    }

    public String getFacebookAccount() {
        return facebookAccount;
    }

    public void setFacebookAccount(String facebookAccount) {
        this.facebookAccount = facebookAccount;
    }

    public String getTwitterAccount() {
        return twitterAccount;
    }

    public void setTwitterAccount(String twitterAccount) {
        this.twitterAccount = twitterAccount;
    }

    public String getShirtSize() {
        return shirtSize;
    }

    public void setShirtSize(String shirtSize) {
        this.shirtSize = shirtSize;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public String getPrivacyConfig() {
        return privacyConfig;
    }

    public void setPrivacyConfig(String privacyConfig) {
        this.privacyConfig = privacyConfig;
    }

    public String getInstagramAccount() {
        return instagramAccount;
    }

    public void setInstagramAccount(String instagramAccount) {
        this.instagramAccount = instagramAccount;
    }
}