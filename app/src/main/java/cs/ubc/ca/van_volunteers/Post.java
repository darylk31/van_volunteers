package cs.ubc.ca.van_volunteers;

/**
 * Created by Daryl on 12/19/2017.
 */

public class Post {
    private String title;
    private String organization;
    private String post_date;
    private String start_date;
    private String city;
    private String address;
    private String description;
    private String email;
    private String oid;

    public Post(){}

    public Post(String id, String title, String organization, String post_date, String start_date, String city, String address,
                String description, String email){
        this.id = id;
        this.title = title;
        this.organization = organization;
        this.post_date = post_date;
        this.start_date = start_date;
        this.city = city;
        this.address = address;
        this.description = description;
        this.email = email;
        this.oid = oid;
    }

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOrganization() {
        return organization;
    }

    public String getPost_date() {
        return post_date;
    }

    public String getAddress() {
        return address;
    }

    public String getDescription() {
        return description;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCity() {return city;}

    public String getOid() {return oid;}
}
