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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String createUpdateBookingFunction(@Valid @ModelAttribute("instrumentBasis") InstrumentClassBasisInstrument instrument,
                                              RedirectAttributes redirAttrs, Model model, BindingResult result) {
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
        instrument = icbIservice.createUpdateInstrument(instrument);
        if (instrument != null) {
            redirAttrs.addFlashAttribute("success", " Instrument Class a été inséré avec succès : " + instrument.getName());
        } else {
            redirAttrs.addFlashAttribute("exist", " Instrument Class déja existe ");
        }
        return "redirect:/gestion-commission/bookingFunction";
    }

    @GetMapping("/{id}")
    public String deleteBookingFunction(@PathVariable("id") Long id, RedirectAttributes redirAttrs) {
        InstrumentClassBasisInstrument instrument = icbIservice.deleteInstrument(id);
        if (instrument==null){
            redirAttrs.addFlashAttribute("error", "Vous n’avez pas le droit de supprimer cet Instrument Class. ");
        }else {
            redirAttrs.addFlashAttribute("delete", "Instrument Class a été supprimer  avec succès ");
        }
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
