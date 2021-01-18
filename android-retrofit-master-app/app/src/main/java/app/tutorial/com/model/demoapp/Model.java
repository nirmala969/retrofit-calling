package app.tutorial.com.model.demoapp;

import com.orm.SugarRecord;

import java.io.Serializable;

public class Model  /*extends SugarRecord*/  implements Serializable{

    private String gender;
    private Name name;
    private Location location;
    private String email;
    private Login login;
    private Dob dob;
    private Registered registered;
    private String phone;
    private String cell;
    private Id idm;
    private long mid;
    private Picture picture;
    private String nat;

    private boolean isAccept;
    private boolean isDecline;

    public Model(long mid, Name name, boolean isAccept, boolean isDecline) {
        this.mid = mid;
        this.name = name;
        this.isAccept = isAccept;
        this.isDecline = isDecline;
    }

    public Model(long mid, String gender, Name name, Location location, String email, Login login, Dob dob, Registered registered, String phone, String cell, Id id, Picture picture, String nat, boolean isAccept, boolean isDecline) {
        this.mid = mid;
        this.gender = gender;
        this.name = name;
        this.location = location;
        this.email = email;
        this.login = login;
        this.dob = dob;
        this.registered = registered;
        this.phone = phone;
        this.cell = cell;
        this.idm = id;
        this.picture = picture;
        this.nat = nat;
        this.isAccept = isAccept;
        this.isDecline = isDecline;
    }

    public long getMid() {
        return mid;
    }

    public void setMid(long mid) {
        this.mid = mid;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

    public Dob getDob() {
        return dob;
    }

    public void setDob(Dob dob) {
        this.dob = dob;
    }

    public Registered getRegistered() {
        return registered;
    }

    public void setRegistered(Registered registered) {
        this.registered = registered;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCell() {
        return cell;
    }

    public void setCell(String cell) {
        this.cell = cell;
    }

    public Id getId() {
        return idm;
    }

    public void setId(Id id) {
        this.idm = id;
    }

    public Picture getPicture() {
        return picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }

    public String getNat() {
        return nat;
    }

    public void setNat(String nat) {
        this.nat = nat;
    }

    public boolean isAccept() {
        return isAccept;
    }

    public void setAccept(boolean accept) {
        isAccept = accept;
    }

    public boolean isDecline() {
        return isDecline;
    }

    public void setDecline(boolean decline) {
        isDecline = decline;
    }

    @Override
    public int hashCode() {
        return (int) mid;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Model) {
            Model model = (Model) obj;
            return this.mid == model.getMid();
        }
        return false;
    }
}
