package com.example.auction.services;

import com.example.auction.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;


public interface UserService extends UserDetailsService {

    void save(User user);
    boolean isUniqueLogin(String login);

    Page<User> getAll(Pageable pageable);
}
