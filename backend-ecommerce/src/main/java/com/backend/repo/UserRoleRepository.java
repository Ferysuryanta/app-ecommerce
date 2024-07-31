package com.backend.repo;

import com.backend.model.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRoles, Long> {

    UserRoles findByRoleName(String roleName);
}
