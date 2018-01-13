package cs.ubc.ca.van_volunteers;

/**
 * Created by Daryl on 1/7/2018.
 */

public class Volunteer {

    private String name;
    private String bio;
    private String email;
    private String photoURL;
    private String birthday;

    public Volunteer(){}

    public Volunteer(String name, String bio, String email, String photoURL, String birthday){
        this.name = name;
        this.bio = bio;
        this.email = email;
        this.photoURL = photoURL;
        this.birthday = birthday;
    }

    public String getName() {
        return name;
    }

    public String getBio() {
        return bio;
    }

    public String getEmail() {
        return email;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public String getBirthday() {return birthday;}

}
