package com.hadday.gestion_commission.controller;

import com.hadday.gestion_commission.Service.BookingFunctionService;
import com.hadday.gestion_commission.Service.InstrumentClassBasisInstrumentService;
import com.hadday.gestion_commission.Service.InstrumentClassTypeService;
import com.hadday.gestion_commission.entities.BookingFunction;
import com.hadday.gestion_commission.entities.InstrumentClass;
import com.hadday.gestion_commission.entities.InstrumentClassBasisInstrument;
import com.hadday.gestion_commission.entities.InstrumentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/gestion-commission/bookingFunction")
public class BokingFunctionController {

    @Autowired
    private BookingFunctionService bookingFunctionService;
    @Autowired
    private InstrumentClassTypeService instrumentClassTypeService;
    @Autowired
    private InstrumentClassBasisInstrumentService icbIservice;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("bookingFunctions", bookingFunctionService.findBookingFunctions());
        model.addAttribute("typeInstruments", instrumentClassTypeService.getAllInstrumentType());
        model.addAttribute("InstrumentsBasis", icbIservice.findAll());
        model.addAttribute("bookingFunction", new BookingFunction());
        model.addAttribute("instrumentBasis", new InstrumentClassBasisInstrument());
        return "gestion-commission/mouvement/bookingFunction";
    }

    @PostMapping
    public String createUpdateBookingFunction(@Valid @ModelAttribute("bookingFunction") BookingFunction bookingFunction, Model model, BindingResult result) {
        if (bookingFunction.getName().isEmpty()) {
            result.rejectValue("name", null, " Booking Function Name field is Empty");
        }
        if (result.hasErrors()) {
            model.addAttribute("bookingFunctions", bookingFunctionService.findBookingFunctions());
            model.addAttribute("InstrumentsBasis", icbIservice.findAll());
            model.addAttribute("instrumentBasis", new InstrumentClassBasisInstrument());
            model.addAttribute("typeInstruments", instrumentClassTypeService.getAllInstrumentType());
            return "/gestion-commission/mouvement/bookingFunction";
        }

        bookingFunctionService.addUpdateBookingFunction(bookingFunction);
        return "redirect:/gestion-commission/bookingFunction";
    }

    @GetMapping("/{id}")
    public String deleteBookingFunction(@PathVariable("id") Long id) {
        bookingFunctionService.deleteBookingFunction(id);
        return "redirect:/gestion-commission/bookingFunction";
    }
}

@RestController
class BookingFunctionControllerRest {

    @Autowired
    private BookingFunctionService bookingFunctionService;

    @GetMapping("/bookingFunction/{id}")
    public BookingFunction bookingFunctionById(@PathVariable("id") Long id) {
        return bookingFunctionService.fingBookigFunctionById(id);
    }

}

