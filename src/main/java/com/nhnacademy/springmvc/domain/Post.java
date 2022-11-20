package com.nhnacademy.springmvc.domain;

public interface Post {
    String getUserID();

    long getId();

    boolean isAnswer();

    Category getCategory();
}
