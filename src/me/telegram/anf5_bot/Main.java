package me.telegram.anf5_bot;

public class Main {

	public static void main(String[] args) {

        BotConfig.CHANNEL_NAME = Utils.getStringFromFile(BotConfig.CHANNEL_FILENAME);

        new Anf5Bot(Utils.getStringFromFile(BotConfig.BOT_TOKEN_FILENAME)).start();
    }
}
