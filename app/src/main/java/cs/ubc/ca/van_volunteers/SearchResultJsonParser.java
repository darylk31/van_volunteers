package cs.ubc.ca.van_volunteers;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jimmy on 1/13/2018.
 */

public class SearchResultJsonParser
{
    private PostJsonParser postParser = new PostJsonParser();
    public List<Post> parseResults(JSONObject jsonObject)
    {
        if (jsonObject == null)
            return null;
        List<Post> results = new ArrayList<>();
        JSONArray hits = jsonObject.optJSONArray("hits");
        if (hits == null)
            return null;
        for (int i = 0; i < hits.length(); ++i) {
            JSONObject hit = hits.optJSONObject(i);
            if (hit == null)
                continue;
            Post movie = postParser.parse(hit);
            if (movie == null)
                continue;
            results.add(movie);
        }
        return results;
    }
}
