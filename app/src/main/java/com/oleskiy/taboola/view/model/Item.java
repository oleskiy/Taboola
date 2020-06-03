package com.oleskiy.taboola.view.model;

public class Item {
    int id;
    String name;
    String thumbnail;
    String description;
    int color;
    ViewType viewType = ViewType.REGULAR;

    public Item(){}
    public Item(String name, String description, String thumbnail, int color) {
        this.name = name;
        this.thumbnail = thumbnail;
        this.description = description;
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ViewType getViewType() {
        return viewType;
    }

    public void setViewType(ViewType viewType) {
        this.viewType = viewType;
    }

    public void setColor(int color){this.color = color;}

    public int getColor(){return color;}

    public enum ViewType{
        REGULAR,
        TABOOLA_WIDGET,
        TABOOLA_FEED
    }
}
