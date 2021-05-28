package com.hadday.gestion_commission.repositories;


import com.hadday.gestion_commission.entities.RelevesoldesAvoirs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReleveSoldeRepository extends JpaRepository<RelevesoldesAvoirs,Long> {
}
