package com.backend.repo;

import com.backend.model.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRoles, Long> {

    UserRoles findByRoleName(String roleName);
}
