package com.commerce.controller;

import com.commerce.Constants;
import com.commerce.model.Product;
import com.commerce.service.CategoryService;
import com.commerce.service.ProductService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.Map;

@Controller
public class ProductController {

    private ProductService productService = new ProductService();

    @Value("${spring.servlet.multipart.location}")
    private String fileLocation;

    private final String RELATIVE_FILE_PATH = System.getProperty("user.dir") + "/";

    @GetMapping("/backoffice/product")
    public String getList(Model model, HttpServletRequest request, @RequestParam(value = "sort", required = false) String sortCriteria) {
        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
        model.addAttribute("products", productService.findAll(sortCriteria));
        try {
            model.addAttribute("success", inputFlashMap.get("success"));
        } catch (Exception e) {
            model.addAttribute("success", "");
        }
        try {
            model.addAttribute("filename", inputFlashMap.get("filename"));
        } catch (Exception e) {
            model.addAttribute("filename", "");
        }


        return "products";
    }

    @GetMapping("/frontoffice/shop")
    public String getProducts(Model model, HttpServletRequest request) throws NullPointerException {
        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
        CategoryService categoryService = new CategoryService();
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("products", productService.findAll());
        try {
            model.addAttribute("success", inputFlashMap.get("success"));
        } catch (Exception e) {
            model.addAttribute("success", "");
        }
        try {
            model.addAttribute("filename", inputFlashMap.get("filename"));
        } catch (Exception e) {
            model.addAttribute("filename", "");
        }

        return "shop";
    }

    @GetMapping("/backoffice/product/add")
    public String getEntryForm(Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        if(request.isUserInRole(Constants.ADMIN_ROLE)){
            Product entry = new Product();
            model.addAttribute("entry", entry);
            return "add_product";
        }else{
            redirectAttributes.addFlashAttribute("success", "Error. You don't have the permission to access this!");
            return "redirect:" + Constants.PRODUCTS_LIST_PAGE;
        }

    }

    @GetMapping("/frontoffice/products/bycateg/{id}")
    public String getProductsByCategory(Model model, @PathVariable String id, HttpServletRequest request){
        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
        model.addAttribute("products", productService.findByCategory(Integer.parseInt(id)));
        try {
            model.addAttribute("success", inputFlashMap.get("success"));
        } catch (Exception e) {
            model.addAttribute("success", "");
        }
        try {
            model.addAttribute("filename", inputFlashMap.get("filename"));
        } catch (Exception e) {
            model.addAttribute("filename", "");
        }
        return "shop";
    }

    @PostMapping("/backoffice/product/add/submit")
    public String addEntry(@ModelAttribute @Valid Product form, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes, @RequestParam("picture") MultipartFile file) throws IOException {
//        if(bindingResult.hasErrors()){
//            System.out.println("errors!");
//            bindingResult.getAllErrors().forEach(System.out::print);
//            redirectAttributes.addFlashAttribute("success", "Error at input check");
//            return "redirect:/backoffice/product";
//        }

        if(!file.isEmpty()){
            System.out.println(System.getProperty("user.dir") + "/");
            System.out.println("file location" + fileLocation);
            String fileSaveLocation = RELATIVE_FILE_PATH + fileLocation + file.getOriginalFilename();
            form.setPicture(file.getOriginalFilename());
            // Save the file
            file.transferTo(new File(fileSaveLocation));
        }

        if (productService.save(form) != null) {
            redirectAttributes.addFlashAttribute("success", "Ai adaugat cu succes produsul: " + form.getName());
        } else {
            redirectAttributes.addFlashAttribute("success", "Eroare la verificarea datelor!");
        }

        return "redirect:" + Constants.PRODUCTS_LIST_PAGE;
    }

    @GetMapping("/backoffice/product/delete/{id}")
    public String delEntry(Model model, @PathVariable String id, RedirectAttributes redirectAttributes) {
        Product entity = productService.findById((long) Integer.parseInt(id));
        if (productService.delete(entity)) {
            redirectAttributes.addFlashAttribute("success", "Ai sters cu succes produsul: " + entity.getName());
        } else {
            redirectAttributes.addFlashAttribute("success", "Atentie! Produsul pe care incerci sa il stergi nu exista");
        }

        return "redirect:" + Constants.PRODUCTS_LIST_PAGE;
    }

    @GetMapping("/backoffice/product/edit/{id}")
    public String editForm(Model model, @PathVariable String id){
        Product entry = productService.findById((long) Integer.parseInt(id));
        model.addAttribute("entry", entry);
        return "edit_product";
    }

    @PostMapping("/backoffice/product/edit/submit")
    public String editEntry(Model model, @ModelAttribute Product form, RedirectAttributes redirectAttributes) {
        if (productService.update(form) != null) {
            redirectAttributes.addFlashAttribute("success", "Ai actualizat cu succes produsul: " + form.getName());
        } else {
            redirectAttributes.addFlashAttribute("success", "Atentie! Produsul pe care incerci sa il actualizezi nu exista");
        }

        return "redirect:" + Constants.PRODUCTS_LIST_PAGE;
    }
}