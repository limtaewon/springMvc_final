package com.nhnacademy.springmvc.domain;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;

@Getter
@Setter
public class RequestInquire {

    @Size(min = 2, max = 200)
    String title;
    Category category;
    @Size(max = 40000)
    String content;

    public RequestInquire() {
    }

    public RequestInquire(String title, Category category, String content) {
        this.title = title;
        this.category = category;
        this.content = content;
    }
}
