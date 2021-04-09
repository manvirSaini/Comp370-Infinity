package com.example.infinity_courseproject.ui.util;


public class EventBusTag {
    private final int tag;
    private int position;
    private String content;
    private String title;

    private Object object;

    public EventBusTag(int tag, Object object , int position) {
        this.tag = tag;
        this.object = object;
        this.position = position;
    }

    public EventBusTag(int tag, String title , String content ) {
        this.tag = tag;
        this.title = title;
        this.content = content;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Object getObject() {
        return object;
    }

    public EventBusTag(int tag) {
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