package com.codegym.controller;

import com.codegym.model.Supplier;
import com.codegym.service.ProductService;
import com.codegym.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SupplierController {

    @Autowired
    private ProductService productService;

    @Autowired
    private SupplierService supplierService;

    @GetMapping("/supplier")
    public ModelAndView showListSupplier() {
        Iterable<Supplier> suppliers = supplierService.findAll();
        ModelAndView modelAndView = new ModelAndView("supplier/list");
        modelAndView.addObject("supplier", suppliers);
        return modelAndView;
    }


}
