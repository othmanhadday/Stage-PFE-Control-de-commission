package com.hadday.gestion_commission.repositories;


import com.hadday.gestion_commission.entities.RelevesoldesComptes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReleveSoldeCompteRepository extends JpaRepository<RelevesoldesComptes,Long> {
}
