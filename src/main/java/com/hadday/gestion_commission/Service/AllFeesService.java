package com.hadday.gestion_commission.Service;

import com.hadday.gestion_commission.entities.AllFees;
import com.hadday.gestion_commission.entities.AllFeesGenerated;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface AllFeesService {


    public List<AllFees> findAllFees();

    public Page<AllFees> findAllFees(Pageable pageable);

    public Page<AllFees> findAllFeesBetwwenFDate(Pageable pageable, Date date, Date date1);

    public Page<AllFees> findAllFeesisProcessed(Pageable pageable);

    public void controllerEtat(AllFeesGenerated allFeesGenerated) ;


}
