package com.nhnacademy.springmvc.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Answer {
    long id;
    long postID;
    String content;
    String managerID;
    LocalDateTime createTime;

    private Answer(long postID, String content) {
        this.postID = postID;
        this.content = content;
        this.createTime = LocalDateTime.now();
    }

    public static Answer create(long postID, String content) {
        return new Answer(postID, content);
    }
}
