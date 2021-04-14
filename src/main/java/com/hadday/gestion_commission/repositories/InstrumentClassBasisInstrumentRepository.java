package com.hadday.gestion_commission.repositories;

import com.hadday.gestion_commission.entities.InstrumentClass;
import com.hadday.gestion_commission.entities.InstrumentClassBasisInstrument;
import com.hadday.gestion_commission.entities.InstrumentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InstrumentClassBasisInstrumentRepository extends JpaRepository<InstrumentClassBasisInstrument, Long> {

    public List<InstrumentClassBasisInstrument> findInstrumentClassBasisInstrumentByDeletedIsFalse();

    public List<InstrumentClassBasisInstrument> findInstrumentClassBasisInstrumentsByInstrumentTypeAndDeletedIsFalse(InstrumentType instrumentType);

    public List<InstrumentClassBasisInstrument> findInstrumentClassBasisInstrumentByName(String name);

}
