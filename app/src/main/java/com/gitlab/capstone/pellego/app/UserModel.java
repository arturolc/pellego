package com.gitlab.capstone.pellego.app;

/**********************************************
 Arturo Lara
 UserModel class to keep track of User info
 **********************************************/
public class UserModel {

    private int uID;
    private String firstName;
    private String lastName;
    private String email;

    // initialize empty user to be filled out using setters
    public UserModel() {
        this.uID = 0;
        this.firstName = "";
        this.lastName = "";
        this.email = "";
    }

    public UserModel(int uID, String firstName, String lastName, String email) {
        this.uID = uID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public int getuID() {
        return uID;
    }


    public void setuID(int uID) {
        this.uID = uID;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "uID=" + uID +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
