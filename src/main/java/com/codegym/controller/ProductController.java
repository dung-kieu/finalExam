package com.codegym.controller;

import com.codegym.model.Product;
import com.codegym.model.Supplier;
import com.codegym.service.ProductService;
import com.codegym.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private SupplierService supplierService;

    @ModelAttribute("suppliers")
    public Iterable<Supplier> suppliers(){
        return supplierService.findAll();
    }

    @GetMapping("/product")
    public ModelAndView showListProduct(@RequestParam("s") Optional<String> s, @PageableDefault(size = 10) Pageable pageable) {
        Page<Product> products;
        if (s.isPresent()) {
            products = productService.findAllByNameContaining(s.get(), pageable);
        } else {
            products = productService.findAll(pageable);
        }
        ModelAndView modelAndView = new ModelAndView("product/list");
        modelAndView.addObject("products", products);
        return modelAndView;
    }

    @GetMapping("/create-product")
    public ModelAndView showCreateProduct() {
        ModelAndView modelAndView = new ModelAndView("product/create");
        modelAndView.addObject("product", new Product());
        return modelAndView;
    }

    @PostMapping("/create-product")
    public ModelAndView saveProduct(@ModelAttribute Product product) {
        productService.save(product);
        ModelAndView modelAndView = new ModelAndView("product/create");
        modelAndView.addObject("product", new Product());
        modelAndView.addObject("message", "Create product successfully");
        return modelAndView;
    }

    @GetMapping("/edit-product/{id}")
    public ModelAndView showEditProduct(@PathVariable Long id) {
        Product product = productService.findById(id);
        ModelAndView modelAndView = null;
        if (product != null) {
            modelAndView = new ModelAndView("product/edit");
            modelAndView.addObject("product", product);
        } else {
            modelAndView = new ModelAndView("error.404");
        }
        return modelAndView;
    }

    @PostMapping("/edit-product")
    public ModelAndView updateProduct(@ModelAttribute Product product) {
        productService.save(product);
        ModelAndView modelAndView = new ModelAndView("product/edit");
        modelAndView.addObject("product", product);
        modelAndView.addObject("message", "Edit product successfully");
        return modelAndView;
    }

    @GetMapping("/delete-product/{id}")
    public ModelAndView deleteProduct(@PathVariable Long id) {
        productService.remove(id);
        ModelAndView modelAndView = new ModelAndView("redirect:/product");
        return modelAndView;
    }


}
