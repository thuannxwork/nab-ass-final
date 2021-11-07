package com.nab.gateway.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class UserCache {
    private Long id;
    private String email;
    private String username;
    private String password;
    private String type;
}
