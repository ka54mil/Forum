package com.example.auction.services;

import com.example.auction.models.Role;
import com.example.auction.repositories.RoleRepository;
import com.example.auction.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service("userDetailsService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.example.auction.models.User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }

        return createUserDetails(user);
    }

    private UserDetails createUserDetails(com.example.auction.models.User user) {
        Set<GrantedAuthority> grantedAuthorities =
                user.getRoles().stream().map(
                        r -> new SimpleGrantedAuthority(r.getType().toString())
                ).collect(Collectors.toSet());

        return new User(user.getUsername(), user.getPassword(), user.isEnabled(), true, true, true, grantedAuthorities);
    }

    @Override
    public com.example.auction.models.User save(com.example.auction.models.User user) {

        if(user.getRoles()==null || user.getRoles().isEmpty()){
            Role userRole = roleRepository.findRoleByType(Role.Types.ROLE_USER);
            List roles = Arrays.asList(userRole);
            user.setRoles(new HashSet<>(roles));
        }
        if(user.getId() == null){
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setPasswordConfirm(null);
        }
        userRepository.saveAndFlush(user);
        return user;
    }

    @Override
    public void delete(Long id) {
        userRepository.delete(id);
    }

    @Override
    public boolean isUniqueLogin(String username) {
        return userRepository.findByUsername(username) == null;
    }

    @Override
    public com.example.auction.models.User getById(Long id) {
        return userRepository.findOne(id);
    }

    @Override
    public Page<com.example.auction.models.User> getAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public List<com.example.auction.models.User> getAll() {
        return userRepository.findAll();
    }
}