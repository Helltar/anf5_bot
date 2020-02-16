package me.telegram.anf5_bot;

public class Logger {

    public static void addLog(String msg) {
        System.out.println(msg);
    }
    
    public static void addLog(Exception e) {
        addLog(e.getMessage());
    }
}

