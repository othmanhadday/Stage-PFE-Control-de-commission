package com.hadday.gestion_commission.Service;

import com.hadday.gestion_commission.entities.InstrumentClass;
import com.hadday.gestion_commission.entities.InstrumentType;

import javax.sound.midi.Instrument;
import java.util.List;

public interface InstrumentClassTypeService {

    public List<InstrumentClass> getAllInstrumentClass();

    public InstrumentClass createUpdateInstrumentClass(InstrumentClass instrumentClass);

    public InstrumentClass getInstrumentClassById(Long id);

    public void deleteInstrumentClass(Long id);

    public List<InstrumentType> getAllInstrumentType();

    public InstrumentType getInstrumentTypeById(Long id);

    public InstrumentType createUpdateInstrumentType(InstrumentType instrumentType);

    public void deleteInstrumentType(Long id);

    public List<InstrumentType> getInstrumentTypeByClass(InstrumentClass instrumentClass);


}
