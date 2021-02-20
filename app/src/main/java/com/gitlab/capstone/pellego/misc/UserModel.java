package com.gitlab.capstone.pellego.misc;

/**********************************************
 Arturo Lara
 UserModel class to keep track of User info
 **********************************************/
public class UserModel {

    private int uID;
    private String firstName;
    private String lastName;
    private String email;

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
