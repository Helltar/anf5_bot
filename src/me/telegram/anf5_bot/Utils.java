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
    
    public static String getStringFromFile(String filename) throws FileNotFoundException, IOException {
        String line = "";
        BufferedReader reader;

        reader = new BufferedReader(new FileReader(filename));
        line = reader.readLine();
        reader.close();

        return line;
    }

    public static int getIntFromFile(String filename) throws FileNotFoundException, IOException {
        return Integer.parseInt(getStringFromFile(filename));
    }

    public static void setLineToFile(String line, String filename) throws FileNotFoundException, UnsupportedEncodingException {
        PrintWriter writer = new PrintWriter(filename, "UTF-8");
        writer.println(line);
        writer.close();
    }

    public static void setLineToFile(int line, String filename) throws FileNotFoundException, UnsupportedEncodingException {
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
            .execute();
    }
    
    public static String replaceBB(String text) {
        text = text.replaceAll("\\[b\\](.*?)\\[\\/b\\]", "<b>$1</b>");
        text = text.replaceAll("\\[i\\](.*?)\\[\\/i\\]", "$1");
        text = text.replaceAll("\\[c=(.*?)\\](.*?)\\[\\/c\\]", "\n<i>" + LangData.QUOTE + ": $1\n\n   $2</i>\n");
        text = text.replaceAll("\\[c\\](.*?)\\[\\/c\\]", "\n<i>" + LangData.QUOTE + ":\n   $1</i>\n");

        return text;
    }
}
