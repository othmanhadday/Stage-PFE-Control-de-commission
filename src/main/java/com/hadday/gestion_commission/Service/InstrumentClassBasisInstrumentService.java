package com.hadday.gestion_commission.Service;

import com.hadday.gestion_commission.entities.InstrumentClassBasisInstrument;
import com.hadday.gestion_commission.entities.InstrumentType;

import java.util.List;

public interface InstrumentClassBasisInstrumentService {

    public InstrumentClassBasisInstrument findById(Long id);

    public List<InstrumentClassBasisInstrument> findByName(String name);

    public List<InstrumentClassBasisInstrument> findAll();

    public InstrumentClassBasisInstrument createUpdateInstrument(InstrumentClassBasisInstrument instrument);

    public InstrumentClassBasisInstrument deleteInstrument(Long id);

    public List<InstrumentClassBasisInstrument> findInstrumentBasisByInstrumentType(InstrumentType instrumentType);
}
