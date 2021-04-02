package com.hadday.gestion_commission.repositories;

import com.hadday.gestion_commission.entities.BookingInstrumentBasis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingInstrumentBasisRepository extends JpaRepository<BookingInstrumentBasis, Long> {
    public List<BookingInstrumentBasis> findBookingInstrumentBasesByDeletedIsFalse();

}
