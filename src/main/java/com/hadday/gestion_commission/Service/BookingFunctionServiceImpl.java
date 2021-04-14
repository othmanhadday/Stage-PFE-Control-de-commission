package com.hadday.gestion_commission.Service;

import com.hadday.gestion_commission.entities.BookingFunction;
import com.hadday.gestion_commission.entities.InstrumentType;
import com.hadday.gestion_commission.repositories.BookingFunctionRepository;
import com.hadday.gestion_commission.repositories.BookingInstrumentBasisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingFunctionServiceImpl implements BookingFunctionService {

    @Autowired
    private BookingFunctionRepository bookingFunctionRepository;
    @Autowired
    private BookingInstrumentBasisRepository bookingInstrumentBasisRepository;

    @Override
    public BookingFunction fingBookigFunctionById(Long id) {
        return bookingFunctionRepository.findById(id).get();
    }

    @Override
    public BookingFunction fingBookigFunctionByName(String name) {
        return bookingFunctionRepository.findBookingFunctionByNameAndDeletedIsFalse(name);
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
                bookingFunction = bookingFunctionRepository.save(bookingFunction);
            } else {
                bookingFunction = null;
            }
        } else {
            Optional<BookingFunction> bookingFunctionOptional = bookingFunctionRepository.findById(bookingFunction.getId());
            if (bookingFunctionOptional.isPresent()) {
                BookingFunction newBookingFunction = bookingFunctionOptional.get();
                newBookingFunction.setId(bookingFunction.getId());
                if (bookingFunctionisNull == null) {
                    newBookingFunction.setName(bookingFunction.getName());
                    bookingFunction = bookingFunctionRepository.save(newBookingFunction);
                } else {
                    bookingFunction = null;
                }
            } else {
                bookingFunction = bookingFunctionRepository.save(bookingFunction);
            }
        }
        return bookingFunction;
    }

    @Override
    public BookingFunction deleteBookingFunction(Long id) {
        BookingFunction bookingFunction = fingBookigFunctionById(id);
        if (bookingInstrumentBasisRepository.findBookingInstrumentBasesByBookFunctionAndDeletedIsFalse(bookingFunction).size() > 0) {
            return null;
        } else {
            bookingFunction.setDeleted(true);
            return bookingFunctionRepository.save(bookingFunction);
        }
    }
}
