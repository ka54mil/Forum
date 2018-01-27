package com.example.auction.services;

import com.example.auction.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;


public interface UserService extends UserDetailsService, Service<User> {

    User save(User user);
    boolean isUniqueLogin(User login);

    Page<User> getAll(Pageable pageable);
}
