package com.joaquimley.smsparsing;

public class DriverBAC {

    String _id;
    String obserDate;
    String bacValue;
    String driverId;
    String APIKey;

    public String getAPIKey() {
        return APIKey;
    }

    public void setAPIKey(String APIKey) {
        this.APIKey = APIKey;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getObserDate() {
        return obserDate;
    }

    public void setObserDate(String obserDate) {
        this.obserDate = obserDate;
    }

    public String getBacValue() {
        return bacValue;
    }

    public void setBacValue(String bacValue) {
        this.bacValue = bacValue;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }
}
