package com.hadday.gestion_commission.Service;

import com.hadday.gestion_commission.entities.InstrumentClass;
import com.hadday.gestion_commission.entities.InstrumentClassBasisInstrument;
import com.hadday.gestion_commission.entities.InstrumentType;
import com.hadday.gestion_commission.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class InstrumentClassTypeServiceImpl implements InstrumentClassTypeService {

    @Autowired
    private InstrumentClassRepository instrumentClassRepository;
    @Autowired
    private InstrumentTypeRepository instrumentTypeRepository;
    @Autowired
    private InstrumentCategorieRepository instrumentCategorieRepository;
    @Autowired
    private InstrumentClassBasisInstrumentRepository instrumentClassBasisInstrumentRepository;

    @Override
    public List<InstrumentClass> getAllInstrumentClass() {
        return instrumentClassRepository.findInstrumentClassesByDeletedIsFalse();
    }

    @Override
    public InstrumentClass createUpdateInstrumentClass(InstrumentClass instrumentClass) {
        InstrumentClass instrumentClassIsNul = instrumentClassRepository.findInstrumentClassByInstrementClassAndDeletedIsFalse(instrumentClass.getInstrementClass());
        if (instrumentClass.getId() == null) {
            if (instrumentClassIsNul == null) {
                instrumentClass = instrumentClassRepository.save(instrumentClass);
            } else {
                instrumentClass = null;
            }
        } else {
            Optional<InstrumentClass> instrumentClassOptional = instrumentClassRepository.findById(instrumentClass.getId());
            if (instrumentClassOptional.isPresent()) {
                InstrumentClass newInstrumentClass = instrumentClassOptional.get();
                if (instrumentClassIsNul == null) {
                    newInstrumentClass.setInstrementClass(instrumentClass.getInstrementClass());
                    newInstrumentClass.setId(instrumentClass.getId());
                    instrumentClass = instrumentClassRepository.save(newInstrumentClass);
                } else {
                    instrumentClass = null;
                }
            } else {
                instrumentClass = instrumentClassRepository.save(instrumentClass);
            }
        }
        return instrumentClass;
    }

    @Override
    public InstrumentClass getInstrumentClassById(Long id) {
        return instrumentClassRepository.findById(id).get();
    }

    @Override
    public InstrumentClass deleteInstrumentClass(Long id) {
        InstrumentClass instrumentClass = getInstrumentClassById(id);
        if (instrumentTypeRepository.findInstrumentTypesByInstrumentClassAndDeletedIsFalse(instrumentClass).size() > 0) {
            return null;
        } else {
            instrumentClass.setDeleted(true);
            return instrumentClassRepository.save(instrumentClass);
        }
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
        AtomicBoolean isEquals = new AtomicBoolean(false);
        List<InstrumentType> instrumentTypes = instrumentTypeRepository.findInstrumentTypesByDeletedIsFalse();

        InstrumentType it = instrumentType;
        instrumentTypes.forEach(its -> {
            if (its.compareTo(it) == 1) {
                isEquals.set(true);
                return;
            }
        });

        if (instrumentType.getId() == null) {
            if (isEquals.get() == false) {
                instrumentType = instrumentTypeRepository.save(instrumentType);
//                if instrument type = coupons o ajout un "-"
                if (!instrumentType.getInstrumentTypeName().toUpperCase().equals("COUPONS")){
                    instrumentClassBasisInstrumentRepository.save(new InstrumentClassBasisInstrument(null,"-",instrumentType,null,false));
                }
            } else {
                instrumentType = null;
            }
        } else {
            Optional<InstrumentType> instrumentTypeOptional = instrumentTypeRepository.findById(instrumentType.getId());
            if (instrumentTypeOptional.isPresent()) {
                InstrumentType newInstrumentType = instrumentTypeOptional.get();
                newInstrumentType.setId(instrumentType.getId());
                newInstrumentType.setInstrumentTypeName(instrumentType.getInstrumentTypeName());
                newInstrumentType.setInstrumentTypeCode(instrumentType.getInstrumentTypeCode());
                if (isEquals.get() == false) {
                    instrumentType = instrumentTypeRepository.save(newInstrumentType);

                } else {
                    instrumentType = null;
                }
            } else {
                instrumentType = instrumentTypeRepository.save(instrumentType);
            }
        }

        return instrumentType;
    }

    @Override
    public InstrumentType deleteInstrumentType(Long id) {
        InstrumentType instrumentType = getInstrumentTypeById(id);
        if (
            instrumentCategorieRepository.findInstrumentCategoriesByInstrumentTypeAndDeletedIsFalse(instrumentType).size() > 0 ||
            instrumentClassBasisInstrumentRepository.findInstrumentClassBasisInstrumentsByInstrumentTypeAndDeletedIsFalse(instrumentType).size() > 0
        ) {
            return null;
        } else {
            instrumentType.setDeleted(true);
            return instrumentTypeRepository.save(instrumentType);
        }
    }

    @Override
    public List<InstrumentType> getInstrumentTypeByClass(InstrumentClass instrumentClass) {
        return instrumentTypeRepository.findInstrumentTypesByInstrumentClassAndDeletedIsFalse(instrumentClass);
    }
}
