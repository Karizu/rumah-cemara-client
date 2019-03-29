package cemara.labschool.id.rumahcemara.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ChatHistory {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<Datum> data = null;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public class Datum {
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("channel")
        @Expose
        private String channel;
        @SerializedName("from_id")
        @Expose
        private String fromId;
        @SerializedName("from_name")
        @Expose
        private String fromName;
        @SerializedName("message_type")
        @Expose
        private String messageType;
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("deleted_at")
        @Expose
        private Object deletedAt;
        @SerializedName("service_transaction_id")
        @Expose
        private String serviceTransactionId;
        @SerializedName("to_id")
        @Expose
        private String toId;
        @SerializedName("user")
        @Expose
        private User user;

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

        public String getFromId() {
            return fromId;
        }

        public void setFromId(String fromId) {
            this.fromId = fromId;
        }

        public String getFromName() {
            return fromName;
        }

        public void setFromName(String fromName) {
            this.fromName = fromName;
        }

        public String getMessageType() {
            return messageType;
        }

        public void setMessageType(String messageType) {
            this.messageType = messageType;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public Object getDeletedAt() {
            return deletedAt;
        }

        public void setDeletedAt(Object deletedAt) {
            this.deletedAt = deletedAt;
        }

        public String getServiceTransactionId() {
            return serviceTransactionId;
        }

        public void setServiceTransactionId(String serviceTransactionId) {
            this.serviceTransactionId = serviceTransactionId;
        }

        public String getToId() {
            return toId;
        }

        public void setToId(String toId) {
            this.toId = toId;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

    }

    public class Profile {

        @SerializedName("user_id")
        @Expose
        private String userId;
        @SerializedName("treatment_id")
        @Expose
        private Object treatmentId;
        @SerializedName("identity_card")
        @Expose
        private Object identityCard;
        @SerializedName("fullname")
        @Expose
        private String fullname;
        @SerializedName("phone_number")
        @Expose
        private Object phoneNumber;
        @SerializedName("birth_date")
        @Expose
        private String birthDate;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("gender")
        @Expose
        private String gender;
        @SerializedName("address")
        @Expose
        private Object address;
        @SerializedName("city")
        @Expose
        private Object city;
        @SerializedName("picture")
        @Expose
        private String picture;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public Object getTreatmentId() {
            return treatmentId;
        }

        public void setTreatmentId(Object treatmentId) {
            this.treatmentId = treatmentId;
        }

        public Object getIdentityCard() {
            return identityCard;
        }

        public void setIdentityCard(Object identityCard) {
            this.identityCard = identityCard;
        }

        public String getFullname() {
            return fullname;
        }

        public void setFullname(String fullname) {
            this.fullname = fullname;
        }

        public Object getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(Object phoneNumber) {
            this.phoneNumber = phoneNumber;
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

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public Object getAddress() {
            return address;
        }

        public void setAddress(Object address) {
            this.address = address;
        }

        public Object getCity() {
            return city;
        }

        public void setCity(Object city) {
            this.city = city;
        }

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }

    }

    public class Role {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("deleted_at")
        @Expose
        private Object deletedAt;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public Object getDeletedAt() {
            return deletedAt;
        }

        public void setDeletedAt(Object deletedAt) {
            this.deletedAt = deletedAt;
        }

    }

    public class User {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("group_id")
        @Expose
        private String groupId;
        @SerializedName("role_id")
        @Expose
        private String roleId;
        @SerializedName("username")
        @Expose
        private String username;
        @SerializedName("status_online")
        @Expose
        private Integer statusOnline;
        @SerializedName("status")
        @Expose
        private Integer status;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("deleted_at")
        @Expose
        private Object deletedAt;
        @SerializedName("role")
        @Expose
        private Role role;
        @SerializedName("profile")
        @Expose
        private Profile profile;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getGroupId() {
            return groupId;
        }

        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }

        public String getRoleId() {
            return roleId;
        }

        public void setRoleId(String roleId) {
            this.roleId = roleId;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public Integer getStatusOnline() {
            return statusOnline;
        }

        public void setStatusOnline(Integer statusOnline) {
            this.statusOnline = statusOnline;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public Object getDeletedAt() {
            return deletedAt;
        }

        public void setDeletedAt(Object deletedAt) {
            this.deletedAt = deletedAt;
        }

        public Role getRole() {
            return role;
        }

        public void setRole(Role role) {
            this.role = role;
        }

        public Profile getProfile() {
            return profile;
        }

        public void setProfile(Profile profile) {
            this.profile = profile;
        }
    }

}
