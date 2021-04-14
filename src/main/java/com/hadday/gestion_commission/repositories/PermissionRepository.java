package com.hadday.gestion_commission.repositories;

import com.hadday.gestion_commission.entities.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {
    Permission findPermissionByPermission(String name);
}
