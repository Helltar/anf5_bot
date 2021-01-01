
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
        String pat = "((.|\r\n)*?)"; // TODO: (* only for spoiler)

        return text
            .replaceAll("\\[b\\]"+ pat +"\\[\\/b\\]", "<b>$1</b>")
            .replaceAll("\\[c\\]"+ pat +"\\[\\/c\\]", "<i>Цитата:\n   $1</i>\n")
            .replaceAll("\\[i\\]"+ pat +"\\[\\/i\\]", "$1")
            .replaceAll("\\[s\\]"+ pat +"\\[\\/s\\]", "<s>$1</s>")

            .replaceAll("\\[center\\]"+ pat +"\\[\\/center\\]", "$1")
            .replaceAll("\\[code\\]"+ pat +"\\[\\/code\\]", "<code>$1</code>")
            .replaceAll("\\[img\\]"+ pat +"\\[\\/img\\]", "$1")
            .replaceAll("\\[mono\\]"+ pat +"\\[\\/mono\\]", "<code>$1</code>")
            .replaceAll("\\[spoiler\\]"+ pat +"\\[\\/spoiler\\]", "$1")
            .replaceAll("\\[youtube\\]"+ pat +"\\[\\/youtube\\]", "$1")

            .replaceAll("\\[c=(.*)\\]"+ pat +"\\[\\/c\\]", "<i>Цитата: $1\n   $2</i>\n")
            .replaceAll("\\[code(.*)\\]"+ pat +"\\[\\/code\\]", "<code>$2</code>")
            .replaceAll("\\[color=(.*)\\]"+ pat +"\\[\\/color\\]", "$2")
            .replaceAll("\\[url=(.*)\\]"+ pat +"\\[\\/url\\]", "<a href=\"$1\">$2</a>")
            .replaceAll("\\[spoiler=(.*)\\]"+ pat +"\\[\\/spoiler\\]", "$1\n$2");
    }
}
