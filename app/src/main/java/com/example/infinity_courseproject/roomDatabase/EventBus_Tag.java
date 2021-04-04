package com.example.infinity_courseproject.roomDatabase;


public class EventBus_Tag {
    private final int tag;
    private int position;
    private String content;

    public EventBus_Tag(int tag, int position) {
        this.tag = tag;
        this.position = position;
    }


    public EventBus_Tag(int tag) {
        this.tag = tag;
    }

    public int getTag() {
        return tag;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}