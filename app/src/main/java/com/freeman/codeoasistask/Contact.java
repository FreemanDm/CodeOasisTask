package com.freeman.codeoasistask;

/**
 * Created by Freeman on 28.04.2017.
 */

public class Contact {
    private int pk;
    private String id, name, email, address, gender, mPhone, hPhone, oPhone;

    public Contact() {
    }

    public Contact(int pk, String id, String name, String email, String address,
                   String gender, String mPhone, String hPhone, String oPhone) {
        this.pk = pk;
        this.id = id;
        this.name = name;
        this.email = email;
        this.address = address;
        this.gender = gender;
        this.mPhone = mPhone;
        this.hPhone = hPhone;
        this.oPhone = oPhone;
    }

    public Contact(String id, String name, String email, String address, String gender,
                   String mPhone, String hPhone, String oPhone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.address = address;
        this.gender = gender;
        this.mPhone = mPhone;
        this.hPhone = hPhone;
        this.oPhone = oPhone;
    }

    public int getPk() {
        return pk;
    }

    public void setPk(int pk) {
        this.pk = pk;
    }

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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getmPhone() {
        return mPhone;
    }

    public void setmPhone(String mPhone) {
        this.mPhone = mPhone;
    }

    public String gethPhone() {
        return hPhone;
    }

    public void sethPhone(String hPhone) {
        this.hPhone = hPhone;
    }

    public String getoPhone() {
        return oPhone;
    }

    public void setoPhone(String oPhone) {
        this.oPhone = oPhone;
    }
}
