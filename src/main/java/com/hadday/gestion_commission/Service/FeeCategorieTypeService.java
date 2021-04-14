package com.hadday.gestion_commission.Service;

import com.hadday.gestion_commission.entities.CategorieFees;
import com.hadday.gestion_commission.entities.FeeType;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface FeeCategorieTypeService {

    //Categorie Fees
    public Optional<CategorieFees> categorieFeesById(Long id);

    public List<CategorieFees> allCategorieFees();

    public CategorieFees createOrUpdateCategorieFees(CategorieFees categorieFees);

    public CategorieFees deleteCategorieFee(Long id);

    // Fees Type

    public Optional<FeeType> typeFeesById(Long id);

    public List<FeeType> allTypeFees();

    public FeeType createOrUpdateFeeType(FeeType feeType);

    public FeeType deleteFeeType(Long id);

    public List<FeeType> findFeeTypeByCategorieFee(CategorieFees categorieFees);

}
