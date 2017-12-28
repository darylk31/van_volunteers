package cs.ubc.ca.van_volunteers;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Jimmy on 12/23/2017.
 */

public class Utils {

    public static final String ACCOUNT_DATABASE = "Accounts";
    public static final String ACCOUNT_POSTS = "posts";
    public static final String POST_DATABASE = "Post";
    public static final String POST_TITLE = "title";

    private static FirebaseDatabase mDatabase;

    public static FirebaseDatabase getDatabase() {
        if (mDatabase == null) {
            mDatabase = FirebaseDatabase.getInstance();
            mDatabase.setPersistenceEnabled(true);
        }
        return mDatabase;
    }
}
