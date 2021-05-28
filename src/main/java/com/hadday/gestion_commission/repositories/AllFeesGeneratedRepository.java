package com.hadday.gestion_commission.repositories;

import com.hadday.gestion_commission.entities.AllFeesGenerated;
import com.hadday.gestion_commission.entities.RelevesoldesAvoirs;
import com.hadday.gestion_commission.entities.RelevesoldesComptes;
import com.hadday.gestion_commission.entities.Ssatf;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AllFeesGeneratedRepository extends JpaRepository<AllFeesGenerated, Long> {

     AllFeesGenerated findAllFeesGeneratedByRelevesoldesAvoirs(RelevesoldesAvoirs relevesoldesAvoirs);

     AllFeesGenerated findAllFeesGeneratedByRelevesoldesComptes(RelevesoldesComptes relevesoldesComptes);

     AllFeesGenerated findAllFeesGeneratedBySsatf(Ssatf ssatf);

}
