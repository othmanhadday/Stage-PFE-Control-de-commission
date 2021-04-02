package com.hadday.gestion_commission.repositories;

import com.hadday.gestion_commission.entities.FeeRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeeRateRepository extends JpaRepository<FeeRate,Long> {
    public List<FeeRate> findFeeRatesByDeletedIsFalse();
}
