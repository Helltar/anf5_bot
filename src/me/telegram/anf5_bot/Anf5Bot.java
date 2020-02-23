package me.telegram.anf5_bot;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import me.telegram.anf5_bot.Utils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Connection;

public class Anf5Bot {

    private String apiUrl = "https://api.telegram.org/";

    public Anf5Bot(String token) {
        apiUrl += token;
    }

    private void sendMessage(int chatId, String text) {
        sendMessage(Integer.toString(chatId), text);
    }

    private void sendMessage(String chatId, String text) {
        String[] data = {
            "chat_id", chatId,
            "parse_mode", "HTML",
            "disable_web_page_preview", "true",
            "text", text
        };

        Utils.sendPost(apiUrl + "/sendMessage", data);
    }

    private Connection.Response getUpdates(int offset) {
        return Utils.sendPost(apiUrl + "/getUpdates",
                              "offset=" + Integer.toString(offset));
    }

    public void start() {
        Connection.Response response;
        int last_update_id = 0;

        while (true) {
            response = getUpdates(last_update_id++);

            if (response.statusCode() != 200) {
                continue;
            }

            String lastIdFilename = BotConfig.LAST_ID_FILENAME;
            int lastPostId = Api.getLastPostId();

            if (new File(lastIdFilename).exists()) {
                int lastId = Utils.getIntFromFile(lastIdFilename);

                if (lastId < lastPostId) {
                    sendLastPosts(BotConfig.CHANNEL_NAME, 1);
                    Utils.setLineToFile(lastPostId, lastIdFilename);
                }
            } else {
                sendLastPosts(BotConfig.CHANNEL_NAME, 20);
                Utils.setLineToFile(lastPostId, lastIdFilename);
            }

            JSONArray responses =
                new JSONObject(response.body())
                .getJSONArray("result");

            if (responses.isNull(0)) {
                continue;
            } else {
                last_update_id = 
                    responses
                    .getJSONObject(responses.length() - 1)
                    .getInt("update_id") + 1;
            }

            for (int i = 0; i < responses.length(); i++) {
                JSONObject message = responses
                    .getJSONObject(i)
                    .getJSONObject("message");

                JSONObject chat = message.getJSONObject("chat");

                int chat_id = chat.getInt("id");

                String username = chat.has("username") ?
                    chat.getString("username") : "null";

                String text = message.has("text") ?
                    message.getString("text") : "null";

                if (text.startsWith("/start")) {
                    sendMessage(chat_id, LangData.IM_READY);
                } else if (text.startsWith("/getlastposts")) {
                    sendLastPosts(chat_id, 5);
                }

                Logger.addLog(
                    "date: " + new Date().toString() + "\n"
                    + "id: " + chat_id + "\n"
                    + "user: " + username + "\n"
                    + "text: " + text + "\n");

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Logger.addLog(e);
                }
            }
        }
    }

    private void sendLastPosts(String chatId, int limit) {
        List<ApiData> list = new ArrayList<>();

        list = Api.getLastPosts(limit);
        String result = "";

        for (int i = 0; i < list.size(); i++) {
            String text = list.get(i).getText();
            int postId = list.get(i).getPostId();
            String postLink = "<a href=\"" + Api.URL + "/forum/post" + postId + "\">";

            result =
                "<b># " + list.get(i).getTitle() + "\n\n"
                + list.get(i).getUsername() + "</b> "
                + postLink + "#"
                + postId + "</a>\n\n"
                + text;

            result = 
                StringEscapeUtils.unescapeHtml4(
                StringEscapeUtils.unescapeHtml3(
                    Utils.replaceBB(result)));

            sendMessage(chatId, result);
        }
    }

    private void sendLastPosts(int chatId, int limit) {
        sendLastPosts(Integer.toString(chatId), limit);
    }
}
