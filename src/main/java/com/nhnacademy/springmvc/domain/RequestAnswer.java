package com.nhnacademy.springmvc.domain;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;

@Getter
@Setter
public class RequestAnswer {
    @Size(min = 1, max = 40000)
    String content;

    public RequestAnswer() {
    }

}
