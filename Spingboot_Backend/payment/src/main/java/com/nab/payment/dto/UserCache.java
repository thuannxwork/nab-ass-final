package com.nab.payment.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCache {
    private Long id;
    private String email;
    private String username;
    private String password;
    private String type;
}
