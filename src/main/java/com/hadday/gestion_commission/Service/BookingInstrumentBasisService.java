package com.hadday.gestion_commission.Service;

import com.hadday.gestion_commission.entities.BookingInstrumentBasis;
import com.hadday.gestion_commission.entities.DTO.BookingInstrumentBasisDto;

import java.util.List;

public interface BookingInstrumentBasisService {

    public BookingInstrumentBasis getById(Long id);

    public List<BookingInstrumentBasis> getAll();

    public void CreateUpdateBookingInstrumentBasis(BookingInstrumentBasisDto bookingInstrumentBasisDto);

    public void deleteBookingInstrumentBasis(Long id);
}
