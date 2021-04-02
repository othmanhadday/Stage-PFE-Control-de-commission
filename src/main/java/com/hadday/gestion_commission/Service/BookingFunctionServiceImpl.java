package com.hadday.gestion_commission.Service;

import com.hadday.gestion_commission.entities.BookingFunction;
import com.hadday.gestion_commission.entities.InstrumentType;
import com.hadday.gestion_commission.repositories.BookingFunctionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingFunctionServiceImpl implements BookingFunctionService {

    @Autowired
    private BookingFunctionRepository bookingFunctionRepository;

    @Override
    public BookingFunction fingBookigFunctionById(Long id) {
        return bookingFunctionRepository.findById(id).get();
    }

    @Override
    public BookingFunction fingBookigFunctionByName(String name) {
        return bookingFunctionRepository.findBookingFunctionByName(name);
    }

    @Override
    public List<BookingFunction> findBookingFunctions() {
        return bookingFunctionRepository.findBookingFunctionsByDeletedIsFalse();
    }

    @Override
    public BookingFunction addUpdateBookingFunction(BookingFunction bookingFunction) {
        BookingFunction bookingFunctionisNull = fingBookigFunctionByName(bookingFunction.getName());
        if (bookingFunction.getId() == null) {
            if (bookingFunctionisNull == null) {
                return bookingFunctionRepository.save(bookingFunction);
            } else {
                bookingFunction.setId(bookingFunctionisNull.getId());
                bookingFunction.setDeleted(false);
                return bookingFunctionRepository.save(bookingFunction);
            }
        } else {
            Optional<BookingFunction> bookingFunctionOptional = bookingFunctionRepository.findById(bookingFunction.getId());
            if (bookingFunctionOptional.isPresent()) {
                BookingFunction newBookingFunction = bookingFunctionOptional.get();
                if (bookingFunctionisNull == null) {
                    newBookingFunction.setName(bookingFunction.getName());
                } else {
                    newBookingFunction.setDeleted(false);
                }
                newBookingFunction.setId(bookingFunction.getId());
                return bookingFunctionRepository.save(newBookingFunction);
            } else {
                return bookingFunction;
            }
        }
    }

    @Override
    public void deleteBookingFunction(Long id) {
        BookingFunction bookingFunction = fingBookigFunctionById(id);
        bookingFunction.setDeleted(true);
        bookingFunctionRepository.save(bookingFunction);
    }
}
