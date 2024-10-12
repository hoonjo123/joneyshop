package com.joney.shop.Service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

public class CustomUser extends User {

    public Long id;
    public String displayName;

    public CustomUser(String username, String password, List<GrantedAuthority> authorities) {
        super(username, password, authorities);
    }
}