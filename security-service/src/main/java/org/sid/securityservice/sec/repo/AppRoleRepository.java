package org.sid.securityservice.sec.repo;

import org.sid.securityservice.sec.entities.AppRole;
import org.sid.securityservice.sec.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppRoleRepository extends JpaRepository<AppRole,Long> {
    AppRole findByRoleName(String roleName);

}
