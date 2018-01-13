package cs.ubc.ca.van_volunteers;

/**
 * Created by Daryl on 1/7/2018.
 */

public class Organization {

    private String email;
    private String address;
    private String website;
    private String name;
    private String oid;
    private String bio;
    private boolean verified;
    private String photoURL;

    public Organization(){}


    public Organization(String oid, String email, String name, String address, String bio, String website, String photoURL, boolean verified){
        this.oid = oid;
        this.email = email;
        this.name = name;
        this.address = address;
        this.bio = bio;
        this.website = website;
        this.verified = verified;
        this.photoURL = photoURL;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getWebsite() {
        return website;
    }

    public String getOid() {
        return oid;
    }

    public String getBio() {
        return bio;
    }

    public boolean isVerified() {
        return verified;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public String getName(){
        return name;
    }



}
