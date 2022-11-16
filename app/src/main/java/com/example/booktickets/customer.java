package com.example.booktickets;

public class customer {
    String phone,fullname,email, diachi, avatar;

    customer(){

    }
    public customer(String phone, String fullname, String email, String diachi, String avatar) {
        this.phone = phone;
        this.fullname = fullname;
        this.email = email;
        this.diachi = diachi;
        this.avatar = avatar;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
