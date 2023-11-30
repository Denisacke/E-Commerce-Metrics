package com.commerce.controller;

import com.commerce.constant.Constants;
import com.commerce.model.Category;
import com.commerce.model.Product;
import com.commerce.model.dto.ProductDTO;
import com.commerce.model.mapper.ProductMapper;
import com.commerce.service.CategoryService;
import com.commerce.service.ProductService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.Map;

import static com.commerce.constant.Constants.ERROR_ACCESS_MESSAGE;
import static com.commerce.constant.Constants.SUCCESS_MESSAGE;

@Controller
public class ProductController {

    private final ProductService productService = new ProductService();
    private final CategoryService categoryService = new CategoryService();
    
    private static final String PRODUCTS_OBJECT_NAME = "products";
    
    @Value("${spring.servlet.multipart.location}")
    private String fileLocation;
    private static final String FILE_NAME = "filename";
    
    private static final String RELATIVE_FILE_PATH = System.getProperty("user.dir");

    @GetMapping("/backoffice/product")
    public String getList(Model model, HttpServletRequest request, @RequestParam(value = "sort", required = false) String sortCriteria) {
        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
        
        model.addAttribute(PRODUCTS_OBJECT_NAME, productService.findAll(sortCriteria));
        try {
            if(inputFlashMap == null){
                throw new NullPointerException();
            }
            model.addAttribute(SUCCESS_MESSAGE, inputFlashMap.get(SUCCESS_MESSAGE));
            model.addAttribute(FILE_NAME, inputFlashMap.get(FILE_NAME));
        } catch (Exception e) {
            model.addAttribute(SUCCESS_MESSAGE, "");
            model.addAttribute(FILE_NAME, "");
        }

        return PRODUCTS_OBJECT_NAME;
    }

    @GetMapping("/frontoffice/shop")
    public String getProducts(Model model, HttpServletRequest request) throws NullPointerException {
        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute(PRODUCTS_OBJECT_NAME, productService.findAll());

        try {
            if(inputFlashMap == null){
                throw new NullPointerException();
            }
            model.addAttribute(SUCCESS_MESSAGE, inputFlashMap.get(SUCCESS_MESSAGE));
            model.addAttribute(FILE_NAME, inputFlashMap.get(FILE_NAME));
        } catch (Exception e) {
            model.addAttribute(SUCCESS_MESSAGE, "");
            model.addAttribute(FILE_NAME, "");
        }

        return "shop";
    }

    @GetMapping("/backoffice/product/add")
    public String getEntryForm(Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        if(request.isUserInRole(Constants.ADMIN_ROLE)){
            ProductDTO entry = new ProductDTO();
            model.addAttribute("entry", entry);
            model.addAttribute("categories", categoryService.findAll());
            return "add_product";
        }else{
            redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, ERROR_ACCESS_MESSAGE);
            return Constants.REDIRECT_LINK + Constants.PRODUCTS_LIST_PAGE;
        }

    }

    @GetMapping("/frontoffice/products/bycateg/{id}")
    public String getProductsByCategory(Model model, @PathVariable String id, HttpServletRequest request){
        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
        model.addAttribute(PRODUCTS_OBJECT_NAME, productService.findByCategory(Integer.parseInt(id)));

        try {
            if(inputFlashMap == null){
                throw new NullPointerException();
            }
            model.addAttribute(SUCCESS_MESSAGE, inputFlashMap.get(SUCCESS_MESSAGE));
            model.addAttribute(FILE_NAME, inputFlashMap.get(FILE_NAME));
        } catch (Exception e) {
            model.addAttribute(SUCCESS_MESSAGE, "");
            model.addAttribute(FILE_NAME, "");
        }
        return "shop";
    }

    @PostMapping("/backoffice/product/add/submit")
    public String addEntry(@ModelAttribute @Valid ProductDTO form,
                           RedirectAttributes redirectAttributes,
                           @RequestParam("picture") MultipartFile file) throws IOException {
        if(!file.isEmpty()){
            String originalFilename = file.getOriginalFilename();

            // Validate file name to prevent path traversal
            if (originalFilename != null && originalFilename.contains("..")) {
                throw new IOException("Invalid file name, path traversal detected");
            }
            String fileSaveLocation = RELATIVE_FILE_PATH + "/" + fileLocation + file.getOriginalFilename();
            form.setPicture(file.getOriginalFilename());
            // Save the file
            file.transferTo(new File(fileSaveLocation));
        }

        Category selectedCategory = categoryService.findByName(form.getCategoryName());
        redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, productService.save(ProductMapper.mapFromProductDTOToProduct(form, selectedCategory)) != null ? "You have successfully added product with name " + form.getName() : ERROR_ACCESS_MESSAGE);

        return Constants.REDIRECT_LINK + Constants.PRODUCTS_LIST_PAGE;
    }

    @GetMapping("/backoffice/product/delete/{id}")
    public String delEntry(Model model, @PathVariable String id, RedirectAttributes redirectAttributes) {
        Product entity = productService.findById((long) Integer.parseInt(id));
        if (productService.delete(entity)) {
            redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, "You have successfully deleted product with name: " + entity.getName());
        } else {
            redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, "The product you are trying to delete does not exist");
        }

        return Constants.REDIRECT_LINK + Constants.PRODUCTS_LIST_PAGE;
    }

    @GetMapping("/backoffice/product/edit/{id}")
    public String editForm(Model model, @PathVariable String id){
        Product entry = productService.findById((long) Integer.parseInt(id));
        model.addAttribute("entry", entry);
        return "edit_product";
    }

    @PostMapping("/backoffice/product/edit/submit")
    public String editEntry(Model model, @ModelAttribute ProductDTO form, RedirectAttributes redirectAttributes) {
        Category selectedCategory = categoryService.findByName(form.getCategoryName());
        if (productService.update(ProductMapper.mapFromProductDTOToProduct(form, selectedCategory)) != null) {
            redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, "You have successfully updated product with name: " + form.getName());
        } else {
            redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, "The product you are trying to update does not exist");
        }

        return Constants.REDIRECT_LINK + Constants.PRODUCTS_LIST_PAGE;
    }
}