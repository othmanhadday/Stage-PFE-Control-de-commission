package com.hadday.gestion_commission.repositories;


import com.hadday.gestion_commission.entities.Ssatf;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SsatfRepository extends JpaRepository<Ssatf, Long> {

    public List<Ssatf> findSsatfsByDeletedIsFalse();



}
