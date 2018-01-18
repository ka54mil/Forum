package com.example.auction.services;

import com.example.auction.models.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    void save(User user);
    boolean isUniqueLogin(String login);
}
