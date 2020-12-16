package me.telegram.anf5_bot;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

public class Utils {

    public static String getStringFromFile(String filename) {
        String line = "";

        try {
            BufferedReader reader;
            reader = new BufferedReader(new FileReader(filename));
            line = reader.readLine();
            reader.close();
        } catch (FileNotFoundException e) {
            Logger.add(e);
        } catch (IOException e) {
            Logger.add(e);
        }

        return line;
    }

    public static int getIntFromFile(String filename) {
        return Integer.parseInt(getStringFromFile(filename));
    }

    public static void setLineToFile(String line, String filename) {
        try {
            PrintWriter writer = new PrintWriter(filename, "UTF-8");
            writer.println(line);
            writer.close();
        } catch (FileNotFoundException e) {
            Logger.add(e);
        } catch (UnsupportedEncodingException e) {
            Logger.add(e);
        }
    }

    public static void setLineToFile(int line, String filename) {
        setLineToFile(Integer.toString(line), filename);
    }

    public static Connection.Response sendPost(String url, String data) throws IOException {
        return sendPost(url, data.split("="));
    }

    public static Connection.Response sendPost(String url, String[] data) throws IOException {
        return Jsoup
            .connect(url)
            .data(data)
            .method(Connection.Method.POST)
            .ignoreContentType(true)
            .timeout(0)
            .execute();
    }

    public static String replaceBB(String text) {
        return text
            .replaceAll("\\[b\\](.*?)\\[\\/b\\]", "<b>$1</b>")
            .replaceAll("\\[i\\](.*?)\\[\\/i\\]", "$1")
            .replaceAll("\\[s\\](.*?)\\[\\/s\\]", "<s>$1</s>")
            .replaceAll("\\[c=(.*?)\\](.*?)\\[\\/c\\]", "<i>цитата: $1\n   $2</i>\n")
            .replaceAll("\\[color=(.*?)\\](.*?)\\[\\/color\\]", "$2")
            .replaceAll("\\[c\\](.*?)\\[\\/c\\]", "<i>цитата:\n   $1</i>\n")
            .replaceAll("\\[url=(.*?)\\](.*?)\\[\\/url\\]", "<a href=\"$1\">$2</a>")
            .replaceAll("\\[code(.*?)\\](.*?)\\[\\/code\\]", "<pre><code>$2</code></pre>")
            .replaceAll("\\[youtube\\](.*?)\\[\\/youtube\\]", "$1")
            .replaceAll("\\[spoiler\\](.*?)\\[\\/spoiler\\]", "$1")
            .replaceAll("\\[spoiler=(.*?)\\](.*?)\\[\\/spoiler\\]", "$1 : $2")
            .replaceAll("\\[img\\](.*?)\\[\\/img\\]", "$1");
    }
}
