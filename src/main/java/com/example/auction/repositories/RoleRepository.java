package com.example.auction.repositories;

import com.example.auction.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findRoleByType(Role.Types type);
}
