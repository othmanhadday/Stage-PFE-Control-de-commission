package com.hadday.gestion_commission.Service;


import com.hadday.gestion_commission.entities.Permission;
import com.hadday.gestion_commission.entities.RoleApp;
import com.hadday.gestion_commission.entities.UserApp;
import com.hadday.gestion_commission.repositories.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserApp addNewUser(UserApp userApp) {
        if (userApp.getId() == null) {
            userApp.setPassword(passwordEncoder.encode("maroclear"));
            userApp = userRepository.save(userApp);
            return userApp;
        } else {
            Optional<UserApp> userOpt = userRepository.findById(userApp.getId());
            if (userOpt.isPresent()) {
                UserApp user = userOpt.get();
                if (userApp.getNom() != null) {
                    user.setNom(userApp.getNom());
                }
                if (userApp.getUsername() != null) {
                    user.setUsername(userApp.getUsername());
                }
                if (userApp.getEmail() != null) {
                    user.setEmail(userApp.getEmail());
                }
                if (userApp.getPassword() != null) {
                    user.setPassword(passwordEncoder.encode(userApp.getPassword()));
                }
                if (userApp.getRoles() != null) {
                    List<Permission> permissions = new ArrayList<>();
                    for (RoleApp roleApp : userApp.getRoles()) {
                        for (Permission permission : roleApp.getPermissions()) {
                            permissions.add(permission);
                        }
                    }
                    user.setRoles(userApp.getRoles());
                    user.setPermissions(permissions);
                    user.setActivate(true);
                }
                if (userApp.getPermissions() != null) {
                    user.setPermissions(userApp.getPermissions());
                }
                return userRepository.save(user);
            } else {
                userApp = userRepository.save(userApp);
                return userApp;
            }
        }
    }

    @Override
    public void deleteUser(Optional<Long> id) {
        if (id.isPresent()) {
            Optional<UserApp> userApp = userRepository.findById(id);
            if (userApp.get().getId() != null) {
                userApp.get().setDeleted(true);
                userRepository.save(userApp.get());
            } else {
                userApp.get();
            }
        }
    }

    @Override
    public Map<String, UserApp> reinitialiserPwd(Optional<Long> id) {
        HashMap<String, UserApp> map = new HashMap<String, UserApp>();
        UserApp userApp = null;
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@#$%&*-_=+";
        if (id.isPresent()) {
            userApp = userRepository.findById(id).get();

            if (userApp.getId() != null) {
                String pwd = RandomStringUtils.random(10, characters);
                userApp.setPassword(passwordEncoder.encode(pwd));
                userApp = userRepository.save(userApp);
                map.put(pwd, userApp);
                return map;
            }
        }
        return map;
    }

    @Override
    public UserApp getUserByUsername(String username) {
        UserApp user = userRepository.findUserAppByUsername(username);
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserApp userApp = userRepository.findUserAppByUsername(username);
        if (userApp == null) {
            throw new UsernameNotFoundException("Invalid username password");
        } else {
            if (userApp.isActivate() == true && userApp.isDeleted() == false) {
                return new User(userApp.getUsername(), userApp.getPassword(), getAuthorities(userApp.getPermissions()));
            } else {
                return null;
            }
        }
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Collection<Permission> permissions) {
        return getGrantedAuthorities(permissions);
    }

    private List<GrantedAuthority> getGrantedAuthorities(Collection<Permission> permissions) {
        return permissions.stream().map(permission ->
                new SimpleGrantedAuthority(permission.getPermission())
        ).collect(Collectors.toList());
    }

    private List<String> getPermission(Collection<RoleApp> roles) {
        List<String> permissions = new ArrayList<>();
        List<Permission> collection = new ArrayList<>();
        for (RoleApp role : roles) {
            for (Permission permission : role.getPermissions()) {
                permissions.add(permission.getPermission());
            }
//            collection.addAll(role.getPermissions());
        }
//        for (Permission permission : collection) {
//            permissions.add(permission.getPermission());
//        }
        return permissions;
    }
}
