package me.telegram.anf5_bot;

public class Main {

	public static void main(String[] args) {
        Logger.add("bot start ...");

        new Anf5Bot(
            Utils.getStringFromFile(
                BotConfig.BOT_TOKEN_FILENAME))
            .start();
    }
}
