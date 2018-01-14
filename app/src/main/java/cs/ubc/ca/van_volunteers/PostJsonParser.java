package cs.ubc.ca.van_volunteers;

import org.json.JSONObject;

/**
 * Created by Jimmy on 1/13/2018.
 */

public class PostJsonParser {
    public Post parse(JSONObject jsonObject)
    {
        if (jsonObject == null)
            return null;
        String title = jsonObject.optString("title");
        String image = jsonObject.optString("image");
        int rating = jsonObject.optInt("rating", -1);
        int year = jsonObject.optInt("year", 0);
        if (title != null && image != null && rating >= 0 && year != 0)
            return new Post();
        return null;
    }
}
