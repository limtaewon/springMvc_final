package com.nhnacademy.springmvc.domain;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class Inquire implements Post {
    long id;
    String title, content, userID;
    Category category;
    LocalDateTime createTime;
    boolean isAnswer;
    List<String> imageFiles;

    private Inquire(String title, String content, Category category, List<String> imageFiles) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.createTime = LocalDateTime.now();
        this.isAnswer = false;
        this.imageFiles = imageFiles;
    }

    public static Inquire create(String title, String content, Category category, List<String> imageFiles) {
        return new Inquire(title, content, category, imageFiles);
    }
}
