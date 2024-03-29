package com.nab.sso.dto;

import com.nab.sso.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserCache {
    private Long id;
    private String email;
    private String username;
    private String password;
    private String type;

    public UserCache(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.type = user.getType();
    }
}
