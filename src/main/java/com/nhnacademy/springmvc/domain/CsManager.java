package com.nhnacademy.springmvc.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;

@NoArgsConstructor
public class CsManager implements User {
    @Getter
    @Size(max = 20)
    private String id;

    @Getter
    @Setter
    private String pwd;

    @Getter
    @Setter
    private String name;

    public CsManager(String id, String pwd, String name) {
        this.id = id;
        this.pwd = pwd;
        this.name = name;
    }
}

