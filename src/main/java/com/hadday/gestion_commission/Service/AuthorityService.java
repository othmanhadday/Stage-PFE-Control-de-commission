package com.hadday.gestion_commission.Service;


import com.hadday.gestion_commission.entities.Permission;
import com.hadday.gestion_commission.entities.RoleApp;

public interface AuthorityService {
    Permission createOrUpdatePermission(Permission permission);

    RoleApp createOrUpdateRole(RoleApp roleApp);
}
