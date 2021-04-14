package com.hadday.gestion_commission.security;

import com.hadday.gestion_commission.entities.Permission;
import com.hadday.gestion_commission.entities.UserApp;
import com.hadday.gestion_commission.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.ldap.userdetails.LdapAuthoritiesPopulator;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class CustomLdapAuthoritiesPopulator implements LdapAuthoritiesPopulator {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Collection<? extends GrantedAuthority> getGrantedAuthorities(DirContextOperations userData, String username) {
        List<GrantedAuthority> gas = new ArrayList<>();
        UserApp userApp = userRepository.findUserAppByUsername(username);

        if (userApp != null) {
            for (Permission permission : userApp.getPermissions()) {
                gas.add(new SimpleGrantedAuthority(permission.getPermission()));

//                for (Permission permission : role.getPermissions()) {
//                    gas.add(new SimpleGrantedAuthority(permission.getPermission()));
//                }
            }
        } else {
            gas = new ArrayList<>();
        }
        return gas;
    }
}
