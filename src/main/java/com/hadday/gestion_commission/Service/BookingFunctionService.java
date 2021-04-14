package com.hadday.gestion_commission.Service;

import com.hadday.gestion_commission.entities.BookingFunction;

import java.util.List;

public interface BookingFunctionService {

    public BookingFunction fingBookigFunctionById(Long id);

    public BookingFunction fingBookigFunctionByName(String name);

    public List<BookingFunction> findBookingFunctions();

    public BookingFunction addUpdateBookingFunction(BookingFunction bookingFunction);
    public BookingFunction deleteBookingFunction(Long id);
}
