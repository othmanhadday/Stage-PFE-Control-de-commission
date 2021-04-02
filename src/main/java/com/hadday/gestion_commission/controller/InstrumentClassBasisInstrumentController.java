package com.hadday.gestion_commission.controller;

import com.hadday.gestion_commission.Service.BookingFunctionService;
import com.hadday.gestion_commission.Service.InstrumentClassBasisInstrumentService;
import com.hadday.gestion_commission.Service.InstrumentClassTypeService;
import com.hadday.gestion_commission.entities.BookingFunction;
import com.hadday.gestion_commission.entities.InstrumentClassBasisInstrument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/gestion-commission/instrumentBasis")
public class InstrumentClassBasisInstrumentController {

    @Autowired
    private InstrumentClassBasisInstrumentService icbIservice;
    @Autowired
    private InstrumentClassTypeService instrumentClassTypeService;
    @Autowired
    private BookingFunctionService bookingFunctionService;

    @PostMapping
    public String createUpdateBookingFunction(@Valid @ModelAttribute("instrumentBasis") InstrumentClassBasisInstrument instrument, Model model, BindingResult result) {
        if (instrument.getName().isEmpty()) {
            result.rejectValue("name", null, " Instrument Name field is Empty");
        }
        if (result.hasErrors()) {
            model.addAttribute("InstrumentsBasis", icbIservice.findAll());
            model.addAttribute("bookingFunctions", bookingFunctionService.findBookingFunctions());
            model.addAttribute("typeInstruments", instrumentClassTypeService.getAllInstrumentType());
            model.addAttribute("bookingFunction", new BookingFunction());
            return "/gestion-commission/mouvement/bookingFunction";
        }
        icbIservice.createUpdateInstrument(instrument);
        return "redirect:/gestion-commission/bookingFunction";
    }

    @GetMapping("/{id}")
    public String deleteBookingFunction(@PathVariable("id") Long id) {
        icbIservice.deleteInstrument(id);
        return "redirect:/gestion-commission/bookingFunction";
    }
}

@RestController
class InstrumentClassBasisInstrumentControllerRest {

    @Autowired
    private InstrumentClassBasisInstrumentService icbIservice;

    @GetMapping("/instrumentBasis/{id}")
    public InstrumentClassBasisInstrument instrumentBasis(@PathVariable("id") Long id) {
        return icbIservice.findById(id);
    }

}
