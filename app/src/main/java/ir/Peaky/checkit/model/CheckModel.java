package ir.Peaky.checkit.model;

import org.json.JSONObject;

public class CheckModel {

    public int check_id;
    public String check_image_url;
    public String check_name;
    JSONObject check_result;
    public String check_status;
    public String check_taken_time;
    public int check_user;
    public String created_at;
    public String updated_at;

    public CheckModel(int check_id, String check_image_url, String check_name, JSONObject check_result, String check_status, String check_taken_time, int check_user, String created_at, String updated_at) {
        this.check_id = check_id;
        this.check_image_url = check_image_url;
        this.check_name = check_name;
        this.check_result = check_result;
        this.check_status = check_status;
        this.check_taken_time = check_taken_time;
        this.check_user = check_user;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public CheckModel() {
    }

    public int getCheck_id() {
        return check_id;
    }

    public void setCheck_id(int check_id) {
        this.check_id = check_id;
    }

    public String getCheck_image_url() {
        return check_image_url;
    }

    public void setCheck_image_url(String check_image_url) {
        this.check_image_url = check_image_url;
    }

    public String getCheck_name() {
        return check_name;
    }

    public void setCheck_name(String check_name) {
        this.check_name = check_name;
    }

    public JSONObject getCheck_result() {
        return check_result;
    }

    public void setCheck_result(JSONObject check_result) {
        this.check_result = check_result;
    }

    public String getCheck_status() {
        return check_status;
    }

    public void setCheck_status(String check_status) {
        this.check_status = check_status;
    }

    public String getCheck_taken_time() {
        return check_taken_time;
    }

    public void setCheck_taken_time(String check_taken_time) {
        this.check_taken_time = check_taken_time;
    }

    public int getCheck_user() {
        return check_user;
    }

    public void setCheck_user(int check_user) {
        this.check_user = check_user;
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
}
