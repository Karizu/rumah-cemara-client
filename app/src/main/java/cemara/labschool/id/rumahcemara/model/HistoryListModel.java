package cemara.labschool.id.rumahcemara.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HistoryListModel {

    private String id;

    private String group_id;

    private String user_id;

    private String provider_id;

    private String service_type_id;

    private String worker_id;

    private String start_date;

    private String end_date;

    private String description;

    private String attachment;

    private String type_provider;

    private String status;

    private String status_report;

    private String created_at;

    private String updated_at;

    private String deleted_at;

    private String location;

    private Group group;

    private User user;

    private ServiceType service_type;

    private ProviderWorker provider_worker;

    public HistoryListModel(String id, String group_id, String user_id, String provider_id, String service_type_id, String worker_id, String start_date, String end_date, String description, String attachment, String type_provider, String status, String status_report, String created_at, String updated_at, String deleted_at, String location, Group group, User user, ServiceType service_type, ProviderWorker provider_worker) {
        this.id = id;
        this.group_id = group_id;
        this.user_id = user_id;
        this.provider_id = provider_id;
        this.service_type_id = service_type_id;
        this.worker_id = worker_id;
        this.start_date = start_date;
        this.end_date = end_date;
        this.description = description;
        this.attachment = attachment;
        this.type_provider = type_provider;
        this.status = status;
        this.status_report = status_report;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.deleted_at = deleted_at;
        this.location = location;
        this.group = group;
        this.user = user;
        this.service_type = service_type;
        this.provider_worker = provider_worker;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getProvider_id() {
        return provider_id;
    }

    public void setProvider_id(String provider_id) {
        this.provider_id = provider_id;
    }

    public String getService_type_id() {
        return service_type_id;
    }

    public void setService_type_id(String service_type_id) {
        this.service_type_id = service_type_id;
    }

    public String getWorker_id() {
        return worker_id;
    }

    public void setWorker_id(String worker_id) {
        this.worker_id = worker_id;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public String getType_provider() {
        return type_provider;
    }

    public void setType_provider(String type_provider) {
        this.type_provider = type_provider;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus_report() {
        return status_report;
    }

    public void setStatus_report(String status_report) {
        this.status_report = status_report;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ServiceType getService_type() {
        return service_type;
    }

    public void setService_type(ServiceType service_type) {
        this.service_type = service_type;
    }

    public ProviderWorker getProvider_worker() {
        return provider_worker;
    }

    public void setProvider_worker(ProviderWorker provider_worker) {
        this.provider_worker = provider_worker;
    }
}
