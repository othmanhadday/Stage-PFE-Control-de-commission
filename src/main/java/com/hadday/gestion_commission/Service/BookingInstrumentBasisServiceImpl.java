package com.hadday.gestion_commission.Service;

import com.hadday.gestion_commission.entities.BookingInstrumentBasis;
import com.hadday.gestion_commission.entities.DTO.BookingInstrumentBasisDto;
import com.hadday.gestion_commission.repositories.BookingInstrumentBasisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class BookingInstrumentBasisServiceImpl implements BookingInstrumentBasisService {

    @Autowired
    private BookingInstrumentBasisRepository bookingInstrumentBasisRepository;

    @Override
    public BookingInstrumentBasis getById(Long id) {
        return bookingInstrumentBasisRepository.findById(id).get();
    }

    @Override
    public List<BookingInstrumentBasis> getAll() {
        return bookingInstrumentBasisRepository.findBookingInstrumentBasesByDeletedIsFalse();
    }

    @Override
    public void CreateUpdateBookingInstrumentBasis(BookingInstrumentBasisDto dto) {
        AtomicBoolean isEquals = new AtomicBoolean(false);
        List<BookingInstrumentBasis> bookingInstrumentBases = bookingInstrumentBasisRepository.findAll();
        BookingInstrumentBasis bookingInstrumentBasis = new BookingInstrumentBasis();
        bookingInstrumentBasis.setId(dto.getId());
        bookingInstrumentBasis.setBookFunction(dto.getBookFunction());
        bookingInstrumentBasis.setFeeRate(Double.valueOf(dto.getFeeRate()));
        bookingInstrumentBasis.setFeeType(dto.getFeeType());
        bookingInstrumentBasis.setInstrumentClassBasisInstrument(dto.getInstrumentClassBasisInstrument());
        bookingInstrumentBasis.setCreditDebit(dto.getCreditDebit());

        bookingInstrumentBases.forEach(bib -> {
            if (bib.compareTo(bookingInstrumentBasis)==1){
                isEquals.set(true);
                return;
            }
        });

        if (bookingInstrumentBasis.getId()==null){
            if (isEquals.get()==false){
                bookingInstrumentBasisRepository.save(bookingInstrumentBasis);
            }
        }else {
            Optional<BookingInstrumentBasis> bookingInstrumentBasisOptional = bookingInstrumentBasisRepository.findById(bookingInstrumentBasis.getId());
            if (bookingInstrumentBasisOptional.isPresent()){
                BookingInstrumentBasis newBooking = bookingInstrumentBasisOptional.get();
                newBooking.setId(bookingInstrumentBasis.getId());
                newBooking.setFeeRate(bookingInstrumentBasis.getFeeRate());
                if (bookingInstrumentBasis.getInstrumentClassBasisInstrument()!=null){
                    newBooking.setInstrumentClassBasisInstrument(bookingInstrumentBasis.getInstrumentClassBasisInstrument());
                }
                if(bookingInstrumentBasis.getFeeType()!=null){
                    newBooking.setFeeType(bookingInstrumentBasis.getFeeType());
                }
                if(bookingInstrumentBasis.getBookFunction()!=null){
                    newBooking.setBookFunction(bookingInstrumentBasis.getBookFunction());
                }
                if (isEquals.get()==false){
                    bookingInstrumentBasisRepository.save(newBooking);
                }
            }else {
                if (isEquals.get()==false){
                    bookingInstrumentBasisRepository.save(bookingInstrumentBasis);
                }
            }
        }
    }

    @Override
    public void deleteBookingInstrumentBasis(Long id) {
        BookingInstrumentBasis bookingInstrumentBasis = getById(id);
        bookingInstrumentBasis.setDeleted(true);
        bookingInstrumentBasisRepository.save(bookingInstrumentBasis);
    }
}
