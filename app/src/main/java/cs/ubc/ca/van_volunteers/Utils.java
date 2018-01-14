package cs.ubc.ca.van_volunteers;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Jimmy on 12/23/2017.
 */

public class Utils {

    public static final String APP_PACKAGE = "cs.ubc.ca.van_volunteers";
    public static final String ALGOLIA_DATABASE = "BCV09S4M52";
    public static final String ALGOLIA_SERACH_KEY = "4f0c7c65d9ada147e5cb9a86201bd82e";
    public static final String ALGOLIA_INDEX = "Post";

    public static final String ACCOUNT_TYPE_VOLUNTEER = "volunteer";
    public static final String ACCOUNT_TYPE_ORGANIZATION = "organization";


    public static final String ACCOUNT_DATABASE = "Accounts";

    public static final String ORGANIZATION_DATABASE = "Organizations";
    public static final String ORGANIZATION_POSTS = "posts";

    public static final String VOLUNTEER_DATABASE = "Volunteers";
    public static final String VOLUNTEER_APPLIED = "applied";

    public static final String POST_DATABASE = "Post";
    public static final String POST_TITLE = "title";
    public static final String POST_APPLIED = "applied";

    private static FirebaseDatabase mDatabase;

    public static FirebaseDatabase getDatabase() {
        if (mDatabase == null) {
            mDatabase = FirebaseDatabase.getInstance();
            mDatabase.setPersistenceEnabled(true);
        }
        return mDatabase;
    }
}
