package com.hadday.gestion_commission.Service;

import com.hadday.gestion_commission.entities.Permission;
import com.hadday.gestion_commission.entities.RoleApp;
import com.hadday.gestion_commission.repositories.PermissionRepository;
import com.hadday.gestion_commission.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthorityServiceImpl implements AuthorityService {

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Permission createOrUpdatePermission(Permission permission) {
        if (permission.getId() == null) {
            permission.setPermission("ROLE_" + permission.getPermission());
            permission = permissionRepository.save(permission);
            return permission;
        } else {
            Optional<Permission> permissionOptional = permissionRepository.findById(permission.getId());
            if (permissionOptional.isPresent()) {
                Permission newPermession = permissionOptional.get();
                newPermession.setId(permission.getId());
                newPermession.setPermission(permission.getPermission());
                return permissionRepository.save(newPermession);
            } else {
                permission = permissionRepository.save(permission);
                return permission;
            }
        }
    }

    @Override
    public RoleApp createOrUpdateRole(RoleApp roleApp) {
        if (roleApp.getId() == null) {
            roleApp.setName("ROLE_" + roleApp.getName());
            roleApp = roleRepository.save(roleApp);
            return roleApp;
        } else {
            Optional<RoleApp> roleOptional = roleRepository.findById(roleApp.getId());
            if (roleOptional.isPresent()) {
                RoleApp newRole = roleOptional.get();
                if (roleApp.getName() !=null){
                    newRole.setName(roleApp.getName());
                }
                if (roleApp.getPermissions() !=null){
                    newRole.setPermissions(roleApp.getPermissions());
                }
                if (roleApp.isDeleted()==true){
                    newRole.setDeleted(roleApp.isDeleted());
                }
                return roleRepository.save(newRole);
            } else {
                roleApp = roleRepository.save(roleApp);
                return roleApp;
            }
        }
    }
}
