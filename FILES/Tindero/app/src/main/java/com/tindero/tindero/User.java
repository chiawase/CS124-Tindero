package com.tindero.tindero;

/**
 * Created by Maan on 4/21/2016.
 */
public class User {

    private String username;
    private String password;
    private String fullName;
    private String userType;
    private String contactNum;
    private String emailAddress;
    private String description = "";

    public User(String name, String pass, String fName, String type, String contactNum, String emailAddress, String desc) {
        this.username = name;
        this.password = pass;
        this.fullName = fName;
        this.userType = type;
        this.contactNum = contactNum;
        this.emailAddress = emailAddress;
        this.description = desc;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String name) {
        this.username = name;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String pass){
        this.password = pass;
    }

    public String getFullName() {
        return this.fullName;
    }

    public void setFullName(String fName){
        this.fullName = fName;
    }

    public String getUserType() {
        return this.userType;
    }

    public void setUserType(String type){
        this.userType = type;
    }

    public String getContactNum() {
        return this.contactNum;
    }

    public void setContactNum(String contactNum){
        this.contactNum = contactNum;
    }

    public String getEmailAddress() {
        return this.emailAddress;
    }

    public void setEmailAddress(String emailAddress){
        this.emailAddress = emailAddress;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String desc){
        this.description = desc;
    }
}
