package com.hadday.gestion_commission.controller;

import com.hadday.gestion_commission.Service.*;
import com.hadday.gestion_commission.entities.*;
import com.hadday.gestion_commission.entities.DTO.BookingInstrumentBasisDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/gestion-commission/mouvement-rate")
public class BookingInstrumentBasisController {

    @Autowired
    private BookingInstrumentBasisService bookingInstrumentBasisService;
    @Autowired
    private InstrumentClassTypeService instrumentClassTypeService;
    @Autowired
    private BookingFunctionService bookingFunctionService;
    @Autowired
    private FeeCategorieTypeService feeCategorieTypeService;


    @GetMapping
    public String index(Model model) {
        model.addAttribute("instrumentClasses", instrumentClassTypeService.getAllInstrumentClass());
        model.addAttribute("instrumentTypes", instrumentClassTypeService.getAllInstrumentType());
        model.addAttribute("bookingInstruments", bookingInstrumentBasisService.getAll());
        model.addAttribute("bookingFunctions", bookingFunctionService.findBookingFunctions());
        model.addAttribute("feeCategories", feeCategorieTypeService.allCategorieFees());
        model.addAttribute("bookingInstrument", new BookingInstrumentBasisDto());
        return "gestion-commission/mouvement/bookingFunctionInstrumentRate";
    }

    @PostMapping
    public String createUpdateBookingInstrumentBasis(
            @ModelAttribute("bookingInstrument") BookingInstrumentBasisDto dto,
            Model model,
            BindingResult result) {
        System.out.println(dto);

        if (dto.getBookFunction()==null) {
            result.rejectValue("bookFunction", null, " Booking Function field is Empty");
        }
        if (dto.getInstrumentClassBasisInstrument()==null) {
            result.rejectValue("instrumentClassBasisInstrument", null, " Instrument Class Basis Instrument field is Empty");
        }
        if (dto.getFeeType()==null) {
            result.rejectValue("feeType", null, " Fee Type field is Empty");
        }
        if (dto.getFeeRate().isEmpty()) {
            result.rejectValue("feeRate", null, " Fee Rate field is Empty");
        }

        if (result.hasErrors()) {
            model.addAttribute("instrumentClasses", instrumentClassTypeService.getAllInstrumentClass());
            model.addAttribute("instrumentTypes", instrumentClassTypeService.getAllInstrumentType());
            model.addAttribute("bookingInstruments", bookingInstrumentBasisService.getAll());
            model.addAttribute("bookingFunctions", bookingFunctionService.findBookingFunctions());
            model.addAttribute("feeCategories", feeCategorieTypeService.allCategorieFees());
            return "gestion-commission/mouvement/bookingFunctionInstrumentRate";
        }
        bookingInstrumentBasisService.CreateUpdateBookingInstrumentBasis(dto);
        return "redirect:/gestion-commission/mouvement-rate";
    }

    @GetMapping("/{id}")
    public String deleteBookingInstrumentBasis(@PathVariable("id") Long id){
        bookingInstrumentBasisService.deleteBookingInstrumentBasis(id);
        return "redirect:/gestion-commission/mouvement-rate";
    }
}

@RestController
class BookingInstrumentBasisControllerRest {

    @Autowired
    private InstrumentClassTypeService instrumentClassTypeService;
    @Autowired
    private InstrumentClassBasisInstrumentService instrumentService;
    @Autowired
    private BookingInstrumentBasisService bookingInstrumentBasisService;
    @Autowired
    private FeeCategorieTypeService feeCategorieTypeService;


    @GetMapping("/instrumentTypeByClass/{id}")
    public List<InstrumentType> instrumentTypesParClass(@PathVariable("id") Long id) {
        InstrumentClass instrumentClass = instrumentClassTypeService.getInstrumentClassById(id);
        return instrumentClassTypeService.getInstrumentTypeByClass(instrumentClass);
    }

    @GetMapping("/instrumentBasisByInstrumentType/{id}")
    public List<InstrumentClassBasisInstrument> instrumentBasisByInstrumentType(@PathVariable("id") Long id) {
        InstrumentType instrumentType = instrumentClassTypeService.getInstrumentTypeById(id);
        return instrumentService.findInstrumentBasisByInstrumentType(instrumentType);
    }

    @GetMapping("/feeTypesByCategorieFees/{id}")
    public List<FeeType> feeTypesByCategorieFees(@PathVariable("id") Long id) {
        CategorieFees categorieFees = feeCategorieTypeService.categorieFeesById(id).get();
        return feeCategorieTypeService.findFeeTypeByCategorieFee(categorieFees);
    }

    @GetMapping("/getBookingInstrumentBasisById/{id}")
    public BookingInstrumentBasis getBookingInstrumentBasisById(@PathVariable("id")Long id){
       return bookingInstrumentBasisService.getById(id);
    }

}
