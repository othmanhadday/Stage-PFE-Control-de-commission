package com.hadday.gestion_commission.repositories;

import com.hadday.gestion_commission.entities.EcartCommission;
import com.hadday.gestion_commission.entities.RelevesoldesAvoirs;
import com.hadday.gestion_commission.entities.Ssatf;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EcartCommissionRepository extends JpaRepository<EcartCommission, Long> {

    public boolean existsEcartCommissionBySsatf(Ssatf ssatf);


    public List<EcartCommission> findEcartCommissionsByRelevesoldesAvoirs(RelevesoldesAvoirs relevesoldesAvoirs);

    public Page<EcartCommission> findEcartCommissionsByRelevesoldesAvoirsIsNotNullAndDeletedIsFalse(Pageable pageable);

    public Page<EcartCommission> findEcartCommissionsBySsatfIsNotNullAndDeletedIsFalse(Pageable pageable);

    public Page<EcartCommission> findEcartCommissionsByRelevesoldesComptesIsNotNullAndDeletedIsFalse(Pageable pageable);


}
