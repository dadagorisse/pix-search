package com.pix.search.dal.entities;

import java.time.LocalDateTime;

public class Image {
    private final int id;
    private final int width;
    private final int height;
    private final LocalDateTime date;

    public Image(int id, int width, int height, LocalDateTime date) {
        this.id = id;
        this.width = width;
        this.height = height;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public LocalDateTime getDate() {
        return date;
    }
}
