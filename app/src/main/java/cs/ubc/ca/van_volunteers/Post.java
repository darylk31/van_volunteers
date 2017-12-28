package cs.ubc.ca.van_volunteers;

/**
 * Created by Daryl on 12/19/2017.
 */

public class Post {
    private String title;
    private String organization;
    private String post_date;
    private String city;
    private String address;
    private String description;
    private String phone;
    private String email;
    private String startDate;
    private String endDate;

    public Post(){}

    public Post(String id, String title, String organization, String post_date, String city, String address,
                String description, String phone, String email){
        this.id = id;
        this.title = title;
        this.organization = organization;
        this.post_date = post_date;
        this.city = city;
        this.address = address;
        this.description = description;
        this.phone = phone;
        this.email = email;
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

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getPost_date() {
        return post_date;
    }

    public void setPost_date(String post_date) {
        this.post_date = post_date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getCity() {return city;}

    public void setCity() {this.city = city;}


}
