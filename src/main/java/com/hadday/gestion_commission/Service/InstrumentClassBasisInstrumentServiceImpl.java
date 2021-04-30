package com.hadday.gestion_commission.Service;

import com.hadday.gestion_commission.entities.DTO.InstrumentClassBasisInstrumentDto;
import com.hadday.gestion_commission.entities.InstrumentClassBasisInstrument;
import com.hadday.gestion_commission.entities.InstrumentType;
import com.hadday.gestion_commission.repositories.BookingInstrumentBasisRepository;
import com.hadday.gestion_commission.repositories.InstrumentClassBasisInstrumentRepository;
import com.hadday.gestion_commission.repositories.InstrumentTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class InstrumentClassBasisInstrumentServiceImpl implements InstrumentClassBasisInstrumentService {

    @Autowired
    private InstrumentClassBasisInstrumentRepository repository;
    @Autowired
    private BookingInstrumentBasisRepository bookingInstrumentBasisRepository;
    @Autowired
    private InstrumentTypeRepository instrumentTypeRepository;
    @Autowired
    private InstrumentClassTypeService instrumentClassTypeService;

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
    public InstrumentClassBasisInstrument createUpdateInstrument(InstrumentClassBasisInstrumentDto instrumentDto) {
        AtomicBoolean isEquals = new AtomicBoolean(false);
        List<InstrumentClassBasisInstrument> instruments = repository.findInstrumentClassBasisInstrumentByDeletedIsFalse();

        InstrumentClassBasisInstrument instrument=new InstrumentClassBasisInstrument();
        instrument.setId(instrumentDto.getId());
        instrument.setName(instrumentDto.getName());
        if (instrumentDto.getInstrumentType()!=null){
            InstrumentType instType = new InstrumentType(null, "-", "-", false, instrumentDto.getInstrumentClass(), null);
            if (instrumentDto.getInstrumentType().equals("-")) {
                instType = instrumentTypeRepository.save(instrumentTypeisExist(instType));
            }else{
                instType = instrumentClassTypeService.getInstrumentTypeById(Long.valueOf(instrumentDto.getInstrumentType()));
            }
            instrument.setInstrumentType(instType);
        }

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

    @Transactional
    public InstrumentType instrumentTypeisExist(InstrumentType instrumentType) {
        List<InstrumentType> instrumentTypes = instrumentClassTypeService.getAllInstrumentType();
        AtomicBoolean isEqual = new AtomicBoolean(false);
        InstrumentType finalInstrumentType = instrumentType;
        AtomicReference<InstrumentType> instrumentTypeExist = new AtomicReference<>();
        instrumentTypes.forEach(instType -> {
            if (finalInstrumentType.compareTo(instType) == 1) {
                instrumentTypeExist.set(instType);
                isEqual.set(true);
            }
        });

        if (isEqual.get() == true) {
            return instrumentTypeExist.get();
        } else {
            return instrumentType;
        }
    }

    @Override
    public InstrumentClassBasisInstrument deleteInstrument(Long id) {
        InstrumentClassBasisInstrument instrument = findById(id);
        if (bookingInstrumentBasisRepository.findBookingInstrumentBasesByInstrumentClassBasisInstrumentAndDeletedIsFalse(instrument).size() > 0) {
            return null;
        } else {
            instrument.setDeleted(true);
            return repository.save(instrument);
        }
    }

    @Override
    public List<InstrumentClassBasisInstrument> findInstrumentBasisByInstrumentType(InstrumentType instrumentType) {
        return repository.findInstrumentClassBasisInstrumentsByInstrumentTypeAndDeletedIsFalse(instrumentType);
    }


}
