package cs.ubc.ca.van_volunteers;

/**
 * Created by Jimmy on 12/23/2017.
 */

public class Account {
    private String EmailAddress;
    private Boolean Verification;
    private String PostDate;

    public Account(){}

    public Account(String EmailAddress,Boolean Verification, String PostDate){
        this.EmailAddress = EmailAddress;
        this.Verification = Verification;
        this.PostDate = PostDate;
    }

    public Boolean getVerification() {
        return Verification;
    }

    public void setVerification(Boolean verification) {
        Verification = verification;
    }

    public String getPostDate() {
        return PostDate;
    }

    public void setPostDate(String postDate) {
        PostDate = postDate;
    }

    public String getEmailAddress() {
        return EmailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        EmailAddress = emailAddress;
    }
}
