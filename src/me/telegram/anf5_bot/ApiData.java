package me.telegram.anf5_bot;

public class ApiData {

    private String title;
    private String username;
    private String text;
    private int postId;
    private int time;

    public ApiData(String title, String username, String text, int postId, int time) {
        this.title = title;
        this.username = username;
        this.text = text;
        this.postId = postId;
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public String getUsername() {
        return username;
    }

    public String getText() {
        return text;
    }
    
    public int getPostId() {
        return postId;
    }
    
    public int getTime() {
        return time;
    }
}
