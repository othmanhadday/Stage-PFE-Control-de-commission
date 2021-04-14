package com.hadday.gestion_commission.Service;

import com.hadday.gestion_commission.entities.InstrumentClassBasisInstrument;
import com.hadday.gestion_commission.entities.InstrumentType;
import com.hadday.gestion_commission.repositories.BookingInstrumentBasisRepository;
import com.hadday.gestion_commission.repositories.InstrumentClassBasisInstrumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class InstrumentClassBasisInstrumentServiceImpl implements InstrumentClassBasisInstrumentService {

    @Autowired
    private InstrumentClassBasisInstrumentRepository repository;
    @Autowired
    private BookingInstrumentBasisRepository bookingInstrumentBasisRepository;

    @Override
    public InstrumentClassBasisInstrument findById(Long id) {
        return repository.findById(id).get();
    }

    @Override
    public List<InstrumentClassBasisInstrument> findByName(String name) {
        return repository.findInstrumentClassBasisInstrumentByName(name);
    }

    @Override
    public List<InstrumentClassBasisInstrument> findAll() {
        return repository.findInstrumentClassBasisInstrumentByDeletedIsFalse();
    }

    @Override
    public InstrumentClassBasisInstrument createUpdateInstrument(InstrumentClassBasisInstrument instrument) {
        AtomicBoolean isEquals = new AtomicBoolean(false);
        List<InstrumentClassBasisInstrument> instruments = repository.findInstrumentClassBasisInstrumentByDeletedIsFalse();
        
        InstrumentClassBasisInstrument it = instrument;
        instruments.forEach(its -> {
            if (its.compareTo(it) == 1) {
                isEquals.set(true);
                return;
            }
        });

        if (instrument.getId() == null) {
            if (isEquals.get() == false) {
                instrument = repository.save(instrument);
            } else {
                instrument = null;
            }
        } else {
            Optional<InstrumentClassBasisInstrument> instrumentOptional = repository.findById(instrument.getId());
            if (instrumentOptional.isPresent()) {
                InstrumentClassBasisInstrument newInstrument = instrumentOptional.get();
                newInstrument.setId(instrument.getId());
                newInstrument.setInstrumentType(instrument.getInstrumentType());
                newInstrument.setName(instrument.getName());
                if (isEquals.get() == false) {
                    instrument = repository.save(newInstrument);
                } else {
                    instrument = null;
                }
            } else {
                instrument = repository.save(instrument);
            }
        }
        return instrument;
    }

    @Override
    public InstrumentClassBasisInstrument deleteInstrument(Long id) {
        InstrumentClassBasisInstrument instrument = findById(id);
        if(bookingInstrumentBasisRepository.findBookingInstrumentBasesByInstrumentClassBasisInstrumentAndDeletedIsFalse(instrument).size()>0){
            return null;
        }else {
            instrument.setDeleted(true);
            return repository.save(instrument);
        }
    }

    @Override
    public List<InstrumentClassBasisInstrument> findInstrumentBasisByInstrumentType(InstrumentType instrumentType) {
        return repository.findInstrumentClassBasisInstrumentsByInstrumentTypeAndDeletedIsFalse(instrumentType);
    }


}
