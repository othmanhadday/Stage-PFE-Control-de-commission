package com.hadday.gestion_commission.Service;

import com.hadday.gestion_commission.entities.InstrumentClassBasisInstrument;
import com.hadday.gestion_commission.entities.InstrumentType;
import com.hadday.gestion_commission.repositories.InstrumentClassBasisInstrumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InstrumentClassBasisInstrumentServiceImpl implements InstrumentClassBasisInstrumentService {

    @Autowired
    private InstrumentClassBasisInstrumentRepository repository;

    @Override
    public InstrumentClassBasisInstrument findById(Long id) {
        return repository.findById(id).get();
    }

    @Override
    public InstrumentClassBasisInstrument findByName(String name) {
        return repository.findInstrumentClassBasisInstrumentByName(name);
    }

    @Override
    public List<InstrumentClassBasisInstrument> findAll() {
        return repository.findInstrumentClassBasisInstrumentByDeletedIsFalse();
    }

    @Override
    public InstrumentClassBasisInstrument createUpdateInstrument(InstrumentClassBasisInstrument instrument) {
        InstrumentClassBasisInstrument instrumentIsNull = findByName(instrument.getName());
        if (instrument.getId() == null) {
            if (instrumentIsNull == null) {
                return repository.save(instrument);
            } else {
                instrument.setId(instrumentIsNull.getId());
                instrument.setDeleted(false);
                return repository.save(instrument);
            }
        } else {
            Optional<InstrumentClassBasisInstrument> instrumentOptional = repository.findById(instrument.getId());
            if (instrumentOptional.isPresent()) {
                InstrumentClassBasisInstrument newInstrument = instrumentOptional.get();
                if (instrumentIsNull == null) {
                    newInstrument.setName(instrument.getName());
                } else {
                    newInstrument.setDeleted(false);
                }
                newInstrument.setId(instrument.getId());
                newInstrument.setInstrumentType(instrument.getInstrumentType());
                return repository.save(newInstrument);
            } else {
                return instrument;
            }
        }
    }

    @Override
    public void deleteInstrument(Long id) {
        InstrumentClassBasisInstrument instrument = findById(id);
        instrument.setDeleted(true);
        repository.save(instrument);
    }

    @Override
    public List<InstrumentClassBasisInstrument> findInstrumentBasisByInstrumentType(InstrumentType instrumentType) {
        return repository.findInstrumentClassBasisInstrumentsByInstrumentType(instrumentType);
    }
}
