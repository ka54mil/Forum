package com.example.auction.config;

import com.example.auction.models.*;
import com.example.auction.repositories.*;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.HashSet;

@Configuration
@Profile("usersInDB")
public class RepositoriesInitializer {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private AttachmentRepository attachmentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    InitializingBean init() {

        return () -> {

            if(roleRepository.findAll().isEmpty()){
                try {
                    Role roleUser = roleRepository.save(new Role(Role.Types.USER));
                    Role roleAdmin = roleRepository.save(new Role(Role.Types.ADMIN));

                    User user = new User("user", true);
                    user.setRoles(new HashSet<>(Arrays.asList(roleUser)));
                    user.setPassword(passwordEncoder.encode("user"));
                    user.setEmail("user@user.user");

                    User admin = new User("admin", true);
                    admin.setRoles(new HashSet<>(Arrays.asList(roleAdmin)));
                    admin.setPassword(passwordEncoder.encode("admin"));
                    admin.setEmail("admin@admin.admin");


                    userRepository.save(user);
                    userRepository.save(admin);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }

        };
    }

}