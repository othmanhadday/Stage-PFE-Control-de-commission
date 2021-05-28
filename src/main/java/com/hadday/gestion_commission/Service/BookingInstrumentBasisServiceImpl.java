package com.hadday.gestion_commission.Service;

import com.hadday.gestion_commission.entities.*;
import com.hadday.gestion_commission.entities.DTO.BookingInstrumentBasisDto;
import com.hadday.gestion_commission.repositories.BookingInstrumentBasisRepository;
import com.hadday.gestion_commission.repositories.InstrumentClassBasisInstrumentRepository;
import com.hadday.gestion_commission.repositories.InstrumentTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class BookingInstrumentBasisServiceImpl implements BookingInstrumentBasisService {

    @Autowired
    private BookingInstrumentBasisRepository bookingInstrumentBasisRepository;
    @Autowired
    private InstrumentTypeRepository instrumentTypeRepository;
    @Autowired
    private InstrumentClassBasisInstrumentRepository instrumentClassBasisInstrumentRepository;

    @Override
    public BookingInstrumentBasis getById(Long id) {
        return bookingInstrumentBasisRepository.findById(id).get();
    }

    @Override
    public List<BookingInstrumentBasis> getAll() {
        return bookingInstrumentBasisRepository.findBookingInstrumentBasesByDeletedIsFalse();
    }

    @Override
    public BookingInstrumentBasis CreateUpdateBookingInstrumentBasis(BookingInstrumentBasisDto dto) {
        System.out.println(dto);
        AtomicBoolean isEquals = new AtomicBoolean(false);
        List<BookingInstrumentBasis> bookingInstrumentBases = bookingInstrumentBasisRepository.findAll();
        BookingInstrumentBasis bookingInstrumentBasis = new BookingInstrumentBasis();
        bookingInstrumentBasis.setId(dto.getId());
        bookingInstrumentBasis.setBookFunction(dto.getBookFunction());
        bookingInstrumentBasis.setFeeRate(Double.valueOf(dto.getFeeRate()));
        bookingInstrumentBasis.setFeeType(dto.getFeeType());
        bookingInstrumentBasis.setCreditDebit(dto.getCreditDebit());
        if (dto.getInstrumentClassBasisInstrument() != null) {
            InstrumentType instType = new InstrumentType(null, "-", "-", false, dto.getInstrumentClass(), null,null);
            if (dto.getInstrumentType() != null) {
                if (dto.getInstrumentType().equals("-")) {
                    System.out.println("new type");
                    instType = instrumentTypeRepository.save(instrumentTypeisExist(instType));
                } else {
                    System.out.println("tpye = " + dto.getInstrumentType());
                    instType = instrumentTypeRepository.findById(Long.valueOf(dto.getInstrumentType())).get();
                }
            }
            InstrumentClassBasisInstrument instrumentClassBasisInstrument = new InstrumentClassBasisInstrument(null, "-", instType, null, false);
            if (dto.getInstrumentClassBasisInstrument().equals("-")) {
                System.out.println("new class basis");
                System.out.println(instrumentClassBasisInstrument);
                instrumentClassBasisInstrument = instrumentClassBasisInstrumentRepository.save(instrumentClassBasisExist(instrumentClassBasisInstrument));
            } else {
                System.out.println("class = " + dto.getInstrumentClassBasisInstrument());
                instrumentClassBasisInstrument = instrumentClassBasisInstrumentRepository.findById(Long.valueOf(dto.getInstrumentClassBasisInstrument())).get();
            }
            bookingInstrumentBasis.setInstrumentClassBasisInstrument(instrumentClassBasisInstrument);
        }


        BookingInstrumentBasis bb = bookingInstrumentBasis;
        bookingInstrumentBases.forEach(bib -> {
            if (bib.compareTo(bb) == 1) {
                isEquals.set(true);
                return;
            }
        });

        if (bookingInstrumentBasis.getId() == null) {
            if (isEquals.get() == false) {
                bookingInstrumentBasis = bookingInstrumentBasisRepository.save(bookingInstrumentBasis);
            } else {
                bookingInstrumentBasis = null;
            }
        } else {
            Optional<BookingInstrumentBasis> bookingInstrumentBasisOptional = bookingInstrumentBasisRepository.findById(bookingInstrumentBasis.getId());
            if (bookingInstrumentBasisOptional.isPresent()) {
                BookingInstrumentBasis newBooking = bookingInstrumentBasisOptional.get();
                newBooking.setId(bookingInstrumentBasis.getId());
                newBooking.setFeeRate(bookingInstrumentBasis.getFeeRate());
                if (bookingInstrumentBasis.getInstrumentClassBasisInstrument() != null) {
                    newBooking.setInstrumentClassBasisInstrument(bookingInstrumentBasis.getInstrumentClassBasisInstrument());
                }
                if (bookingInstrumentBasis.getFeeType() != null) {
                    newBooking.setFeeType(bookingInstrumentBasis.getFeeType());
                }
                if (bookingInstrumentBasis.getBookFunction() != null) {
                    newBooking.setBookFunction(bookingInstrumentBasis.getBookFunction());
                }
                if (isEquals.get() == false) {
                    bookingInstrumentBasis = bookingInstrumentBasisRepository.save(newBooking);
                } else {
                    bookingInstrumentBasis = null;
                }
            } else {
                if (isEquals.get() == false) {
                    bookingInstrumentBasis = bookingInstrumentBasisRepository.save(bookingInstrumentBasis);
                } else {
                    bookingInstrumentBasis = null;
                }
            }
        }
        return bookingInstrumentBasis;
    }

    @Transactional
    public InstrumentType instrumentTypeisExist(InstrumentType instrumentType) {
        List<InstrumentType> instrumentTypes = instrumentTypeRepository.findInstrumentTypesByDeletedIsFalse();
        AtomicBoolean isEqual = new AtomicBoolean(false);
        InstrumentType finalInstrumentType = instrumentType;
        AtomicReference<InstrumentType> instrumentTypeExist = new AtomicReference<>();
        instrumentTypes.forEach(instType -> {
            if (finalInstrumentType.compareTo(instType) == 1) {
                instrumentTypeExist.set(instType);
                isEqual.set(true);
            }
        });

        if (isEqual.get() == true) {
            return instrumentTypeExist.get();
        } else {
            return instrumentType;
        }
    }

    @Transactional
    public InstrumentClassBasisInstrument instrumentClassBasisExist(InstrumentClassBasisInstrument basisInstrument) {
        List<InstrumentClassBasisInstrument> basisInstruments = instrumentClassBasisInstrumentRepository.findInstrumentClassBasisInstrumentByDeletedIsFalse();
        AtomicBoolean isEqual = new AtomicBoolean(false);
        InstrumentClassBasisInstrument finalBasisInstrument = basisInstrument;
        AtomicReference<InstrumentClassBasisInstrument> instrumentTypeExist = new AtomicReference<>();
        basisInstruments.forEach(basis -> {
            if (finalBasisInstrument.compareTo(basis) == 1) {
                instrumentTypeExist.set(basis);
                isEqual.set(true);
            }
        });

        if (isEqual.get() == true) {
            return instrumentTypeExist.get();
        } else {
            return basisInstrument;
        }
    }


    @Override
    public void deleteBookingInstrumentBasis(Long id) {
        BookingInstrumentBasis bookingInstrumentBasis = getById(id);
        bookingInstrumentBasis.setDeleted(true);
        bookingInstrumentBasisRepository.save(bookingInstrumentBasis);
    }
}
