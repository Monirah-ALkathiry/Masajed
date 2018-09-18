package com.apps.noura.masajd.UserAccount;

/**
 * Created by Noura Alsomaikhi on 11/28/2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("userAutoID")
    @Expose
    private Integer userAutoID;
    @SerializedName("userIDNumber")
    @Expose
    private Object userIDNumber;
    @SerializedName("fullName")
    @Expose
    private String fullName;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("lastLoginDate")
    @Expose
    private Object lastLoginDate;
    @SerializedName("birthDate")
    @Expose
    private Object birthDate;
    @SerializedName("mobileNumber")
    @Expose
    private Object mobileNumber;
    @SerializedName("isTempPassword")
    @Expose
    private Boolean isTempPassword;
    @SerializedName("email")
    @Expose
    private Object email;
    @SerializedName("joinDate")
    @Expose
    private String joinDate;
    @SerializedName("isNotifivationEnabled")
    @Expose
    private Boolean isNotifivationEnabled;
    @SerializedName("userCategory")
    @Expose
    private Integer userCategory;

    public Integer getUserAutoID() {
        return userAutoID;
    }

    public void setUserAutoID(Integer userAutoID) {
        this.userAutoID = userAutoID;
    }

    public Object getUserIDNumber() {
        return userIDNumber;
    }

    public void setUserIDNumber(Object userIDNumber) {
        this.userIDNumber = userIDNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Object getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Object lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public Object getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Object birthDate) {
        this.birthDate = birthDate;
    }

    public Object getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(Object mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public Boolean getIsTempPassword() {
        return isTempPassword;
    }

    public void setIsTempPassword(Boolean isTempPassword) {
        this.isTempPassword = isTempPassword;
    }

    public Object getEmail() {
        return email;
    }

    public void setEmail(Object email) {
        this.email = email;
    }

    public String getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(String joinDate) {
        this.joinDate = joinDate;
    }

    public Boolean getIsNotifivationEnabled() {
        return isNotifivationEnabled;
    }

    public void setIsNotifivationEnabled(Boolean isNotifivationEnabled) {
        this.isNotifivationEnabled = isNotifivationEnabled;
    }

    public Integer getUserCategory() {
        return userCategory;
    }

    public void setUserCategory(Integer userCategory) {
        this.userCategory = userCategory;
    }

}