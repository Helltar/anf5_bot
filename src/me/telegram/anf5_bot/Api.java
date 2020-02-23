package me.telegram.anf5_bot;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Api {

    public static final String URL = "https://annimon.com";
    private static final String URL_API = URL + "/json/";

    public static List<ApiData> getLastPosts(int limit) {
        String[] data = {
            "html", "0",
            "limit", Integer.toString(limit)
        };

        String jData = Utils.sendPost(URL_API + "forum/last_posts", data).body();

        List<ApiData> lastPostsList = new ArrayList<>();

        try {
            JSONArray topicsArray = new JSONArray(jData);

            for (int i = 0; i < topicsArray.length(); i++) {
                JSONObject jTopic = topicsArray.getJSONObject(i);

                String title = jTopic.getString("title");
                String username = jTopic.getString("user");
                String text = jTopic.getString("text");
                int postId = jTopic.getInt("message_id");
                int time = jTopic.getInt("time");

                lastPostsList.add(
                    new ApiData(title,username, text, postId, time));
            }
        } catch (JSONException e) {
            Logger.addLog(e);
        }

        return lastPostsList;
    }

    public static int getLastPostId() {
        return getLastPosts(1).get(0).getPostId();
    }
}
