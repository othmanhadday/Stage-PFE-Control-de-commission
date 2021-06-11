package com.hadday.gestion_commission.Service;

import com.hadday.gestion_commission.entities.AllFees;
import com.hadday.gestion_commission.repositories.AllFeesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AllFeesServiceImpl implements AllFeesService {

    @Autowired
    private AllFeesRepository allFeesRepository;

    @Override
    public List<AllFees> findAllFees() {
        return allFeesRepository.findAllFeesByDeletedIsFalse();
    }

    @Override
    public Page<AllFees> findAllFees(Pageable pageable) {

        Date date = new Date();
        System.out.println(date);
        return allFeesRepository.findAllFeesByDeletedIsFalse(pageable);
    }

    @Override
    public Page<AllFees> findAllFeesBetwwenFDate(Pageable pageable, Date date, Date date1) {
        return allFeesRepository.findAllFeesByDeletedIsFalseAndDATEBetween(pageable, date, date1);
    }
}
