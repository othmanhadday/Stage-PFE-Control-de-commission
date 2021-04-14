package com.hadday.gestion_commission.Service;

import com.hadday.gestion_commission.entities.UserApp;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Map;
import java.util.Optional;

public interface UserService extends UserDetailsService {

    UserApp addNewUser(UserApp userApp);

    void deleteUser(Optional<Long> id);

    Map<String,UserApp> reinitialiserPwd(Optional<Long> id);

    UserApp getUserByUsername(String username);

}
