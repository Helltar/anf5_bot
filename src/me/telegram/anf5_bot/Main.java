package me.telegram.anf5_bot;

import java.io.IOException;
import org.json.JSONException;

public class Main {

	public static void main(String[] args) {
        Logger.addLog("bot start ...");

        try {
            new Anf5Bot(Utils.getStringFromFile(BotConfig.BOT_TOKEN_FILENAME))
                .start();
        } catch (JSONException e) {
            Logger.addLog(e);
        } catch (IOException e) {
            Logger.addLog(e);
        }
    }
}
