package com.hadday.gestion_commission.Service;

import com.hadday.gestion_commission.entities.InstrumentClass;
import com.hadday.gestion_commission.entities.InstrumentType;
import com.hadday.gestion_commission.repositories.InstrumentClassRepository;
import com.hadday.gestion_commission.repositories.InstrumentTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InstrumentClassTypeServiceImpl implements InstrumentClassTypeService {

    @Autowired
    private InstrumentClassRepository instrumentClassRepository;
    @Autowired
    private InstrumentTypeRepository instrumentTypeRepository;

    @Override
    public List<InstrumentClass> getAllInstrumentClass() {
        return instrumentClassRepository.findInstrumentClassesByDeletedIsFalse();
    }

    @Override
    public InstrumentClass createUpdateInstrumentClass(InstrumentClass instrumentClass) {
        InstrumentClass instrumentClassIsNul = instrumentClassRepository.findInstrumentClassByInstrementClass(instrumentClass.getInstrementClass());
        if (instrumentClass.getId() == null) {
            if (instrumentClassIsNul == null) {
                return instrumentClassRepository.save(instrumentClass);
            } else {
                instrumentClass.setId(instrumentClassIsNul.getId());
                instrumentClass.setDeleted(false);
                return instrumentClassRepository.save(instrumentClass);
            }
        } else {
            Optional<InstrumentClass> instrumentClassOptional = instrumentClassRepository.findById(instrumentClass.getId());
            if (instrumentClassOptional.isPresent()) {
                InstrumentClass newInstrumentClass = instrumentClassOptional.get();
                if (instrumentClassIsNul == null) {
                    newInstrumentClass.setInstrementClass(instrumentClass.getInstrementClass());
                } else {
                    newInstrumentClass.setDeleted(false);
                }
                newInstrumentClass.setId(instrumentClass.getId());
                return instrumentClassRepository.save(newInstrumentClass);
            } else {
                return instrumentClassRepository.save(instrumentClass);
            }
        }
    }

    @Override
    public InstrumentClass getInstrumentClassById(Long id) {
        return instrumentClassRepository.findById(id).get();
    }

    @Override
    public void deleteInstrumentClass(Long id) {
        InstrumentClass instrumentClass = getInstrumentClassById(id);
        instrumentClass.setDeleted(true);
        instrumentClassRepository.save(instrumentClass);
    }

    @Override
    public List<InstrumentType> getAllInstrumentType() {
        return instrumentTypeRepository.findInstrumentTypesByDeletedIsFalse();
    }

    @Override
    public InstrumentType getInstrumentTypeById(Long id) {
        return instrumentTypeRepository.findById(id).get();
    }

    @Override
    public InstrumentType createUpdateInstrumentType(InstrumentType instrumentType) {
        InstrumentType instrumentTypeIsNull = instrumentTypeRepository.findInstrumentTypeByInstrumentTypeName(instrumentType.getInstrumentTypeName());
        if (instrumentType.getId() == null) {
            if (instrumentTypeIsNull == null) {
                return instrumentTypeRepository.save(instrumentType);
            } else {
                instrumentType.setId(instrumentTypeIsNull.getId());
                instrumentType.setDeleted(false);
                return instrumentTypeRepository.save(instrumentType);
            }
        } else {
            Optional<InstrumentType> instrumentTypeOptional = instrumentTypeRepository.findById(instrumentType.getId());
            if (instrumentTypeOptional.isPresent()) {
                InstrumentType newInstrumentType = instrumentTypeOptional.get();
                if (instrumentTypeIsNull == null) {
                    newInstrumentType.setInstrumentTypeName(instrumentType.getInstrumentTypeName());
                } else {
                    newInstrumentType.setDeleted(false);
                }
                newInstrumentType.setInstrumentTypeCode(instrumentType.getInstrumentTypeCode());
                newInstrumentType.setId(instrumentType.getId());
                return instrumentTypeRepository.save(newInstrumentType);
            } else {
                return instrumentType;
            }
        }
    }

    @Override
    public void deleteInstrumentType(Long id) {
        InstrumentType instrumentType = getInstrumentTypeById(id);
        instrumentType.setDeleted(true);
        instrumentTypeRepository.save(instrumentType);
    }

    @Override
    public List<InstrumentType> getInstrumentTypeByClass(InstrumentClass instrumentClass) {
        return instrumentTypeRepository.findInstrumentTypesByInstrumentClass(instrumentClass);
    }
}
