package cs.ubc.ca.van_volunteers;

/**
 * Created by Daryl on 12/19/2017.
 */

public class Post {
    private String title;
    private String organization;
    private String post_date;
    private String location;
    private String description;
    private String phone;
    private String email;
    private String startDate;
    private String endDate;

    public Post(){}

    public Post(String id, String title, String organization, String post_date, String location,
                String description, String phone, String email){
        this.id = id;
        this.title = title;
        this.organization = organization;
        this.post_date = post_date;
        this.location = location;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }


}
