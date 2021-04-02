package com.hadday.gestion_commission.repositories;

import com.hadday.gestion_commission.entities.InstrumentClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InstrumentClassRepository extends JpaRepository<InstrumentClass, Long> {
    public InstrumentClass findInstrumentClassByInstrementClass(String namd);

    public List<InstrumentClass> findInstrumentClassesByDeletedIsFalse();
}
