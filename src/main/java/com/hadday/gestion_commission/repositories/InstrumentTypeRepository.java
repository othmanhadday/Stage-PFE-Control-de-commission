package com.hadday.gestion_commission.repositories;

import com.hadday.gestion_commission.entities.InstrumentClass;
import com.hadday.gestion_commission.entities.InstrumentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InstrumentTypeRepository extends JpaRepository<InstrumentType, Long> {

    public InstrumentType findInstrumentTypeByInstrumentTypeNameAndDeletedIsFalse(String namd);

    public List<InstrumentType> findInstrumentTypesByDeletedIsFalse();

    public List<InstrumentType> findInstrumentTypesByInstrumentClassAndDeletedIsFalse(InstrumentClass instrumentClass);
}
