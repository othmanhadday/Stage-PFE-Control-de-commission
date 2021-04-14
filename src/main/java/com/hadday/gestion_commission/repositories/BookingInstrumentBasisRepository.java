package com.hadday.gestion_commission.repositories;

import com.hadday.gestion_commission.entities.BookingFunction;
import com.hadday.gestion_commission.entities.BookingInstrumentBasis;
import com.hadday.gestion_commission.entities.FeeType;
import com.hadday.gestion_commission.entities.InstrumentClassBasisInstrument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingInstrumentBasisRepository extends JpaRepository<BookingInstrumentBasis, Long> {

    public List<BookingInstrumentBasis> findBookingInstrumentBasesByDeletedIsFalse();

    public List<BookingInstrumentBasis> findBookingInstrumentBasesByInstrumentClassBasisInstrumentAndDeletedIsFalse(InstrumentClassBasisInstrument instrument);

    public List<BookingInstrumentBasis> findBookingInstrumentBasesByBookFunctionAndDeletedIsFalse(BookingFunction bookingFunction);

    public List<BookingInstrumentBasis> findBookingInstrumentBasesByFeeTypeAndDeletedIsFalse(FeeType feeType);

}
