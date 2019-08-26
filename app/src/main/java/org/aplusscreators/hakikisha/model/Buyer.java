package org.aplusscreators.hakikisha.model;

public class Buyer {

    private String uuid;
    private String firstName;
    private String lastName;
    private String address_1;
    private String address_2;
    private String email;
    private String dob;
    private int rating;

    public Buyer() {
    }

    public Buyer(String uuid, String firstName, String lastName, String address_1, String address_2, String email, String dob, int rating) {
        this.uuid = uuid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address_1 = address_1;
        this.address_2 = address_2;
        this.email = email;
        this.dob = dob;
        this.rating = rating;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress_1() {
        return address_1;
    }

    public void setAddress_1(String address_1) {
        this.address_1 = address_1;
    }

    public String getAddress_2() {
        return address_2;
    }

    public void setAddress_2(String address_2) {
        this.address_2 = address_2;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
