package com.hadday.gestion_commission.Service;

import com.hadday.gestion_commission.entities.CategorieFees;
import com.hadday.gestion_commission.entities.FeeType;
import com.hadday.gestion_commission.repositories.CategorieFeesRepository;
import com.hadday.gestion_commission.repositories.FeeTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FeeCategorieTypeServiceImpl implements FeeCategorieTypeService {

    @Autowired
    private CategorieFeesRepository categorieFeesRepository;

    @Autowired
    private FeeTypeRepository feeTypeRepository;

    public Optional<CategorieFees> categorieFeesById(Long id) {
        return categorieFeesRepository.findById(id);
    }

    @Override
    public List<CategorieFees> allCategorieFees() {
        return categorieFeesRepository.findCategorieFeesByDeletedIsFalse();
    }

    @Override
    public CategorieFees createOrUpdateCategorieFees(CategorieFees categorieFees) {
        CategorieFees categorieFeesIsNotNull = categorieFeesRepository.findCategorieFeesByCategorieFeeName(categorieFees.getCategorieFeeName());
        if (categorieFees.getId() == null) {
            if (categorieFeesIsNotNull == null) {
                return categorieFeesRepository.save(categorieFees);
            } else {
                categorieFees.setId(categorieFeesIsNotNull.getId());
                categorieFees.setDeleted(false);
                return categorieFeesRepository.save(categorieFees);
            }
        } else {
            Optional<CategorieFees> catFee = categorieFeesRepository.findById(categorieFees.getId());
            if (catFee.isPresent()) {
                CategorieFees newCatFee = catFee.get();
                newCatFee.setId(categorieFees.getId());
                if (categorieFeesIsNotNull == null) {
                    newCatFee.setCategorieFeeName(categorieFees.getCategorieFeeName());
                } else {
                    newCatFee.setDeleted(false);
                }
                return categorieFeesRepository.save(newCatFee);
            } else {
                return categorieFeesRepository.save(categorieFees);
            }
        }
    }


    @Override
    public void deleteCategorieFee(Long id) {
        CategorieFees categorieFees = categorieFeesById(id).get();
        categorieFees.setDeleted(true);
        categorieFeesRepository.save(categorieFees);
    }

    @Override
    public Optional<FeeType> typeFeesById(Long id) {
        return feeTypeRepository.findById(id);
    }

    @Override
    public List<FeeType> allTypeFees() {
        return feeTypeRepository.findFeeTypesByDeletedIsFalse();
    }

    @Override
    public FeeType createOrUpdateFeeType(FeeType feeType) {
        FeeType feeTypeIsNotNull = feeTypeRepository.findFeeTypeByTypeName(feeType.getTypeName());
        if (feeType.getId() == null) {
            if (feeTypeIsNotNull == null) {
                return feeTypeRepository.save(feeType);
            } else {
                feeType.setId(feeTypeIsNotNull.getId());
                feeType.setDeleted(false);
                return feeTypeRepository.save(feeType);
            }
        } else {
            Optional<FeeType> feeTypeOptional = feeTypeRepository.findById(feeType.getId());
            if (feeTypeOptional.isPresent()) {
                FeeType newFeeType = feeTypeOptional.get();
                newFeeType.setId(feeType.getId());
                if (feeTypeIsNotNull == null) {
                    newFeeType.setTypeName(feeType.getTypeName());
                } else {
                    newFeeType.setDeleted(false);
                }
                newFeeType.setCategorieFees(feeType.getCategorieFees());
                return feeTypeRepository.save(newFeeType);
            } else {
                return feeTypeRepository.save(feeType);
            }
        }
    }

    @Override
    public void deleteFeeType(Long id) {
        FeeType feeType = typeFeesById(id).get();
        feeType.setDeleted(true);
        feeTypeRepository.save(feeType);
    }

    @Override
    public List<FeeType> findFeeTypeByCategorieFee(CategorieFees categorieFees) {
        return feeTypeRepository.findFeeTypesByCategorieFees(categorieFees);
    }
}
