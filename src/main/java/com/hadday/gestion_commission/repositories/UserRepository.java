package com.hadday.gestion_commission.repositories;

import com.hadday.gestion_commission.entities.RoleApp;
import com.hadday.gestion_commission.entities.UserApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserApp, Long> {
    UserApp findUserAppByUsername(String username);

    List<UserApp> findUserAppsByDeletedFalse();

    Optional<UserApp> findById(Optional<Long> id);

    List<UserApp> findUserAppsByActivateTrueAndDeletedFalse();

    List<UserApp> findUserAppsByActivateFalseAndDeletedFalse();

    List<UserApp> findUserAppsByRolesAndActivateTrueAndDeletedFalse(RoleApp roleApp);
}
