package com.commerce.controller;

import com.commerce.constant.Constants;
import com.commerce.model.Category;
import com.commerce.model.Product;
import com.commerce.dto.ProductDTO;
import com.commerce.mapper.ProductMapper;
import com.commerce.service.CategoryService;
import com.commerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
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

    private final ProductService productService;
    private final CategoryService categoryService;

    private static final String PRODUCTS_OBJECT_NAME = "products";
    private static final String SHOP_TEMPLATE = "shop";

    @Value("${spring.servlet.multipart.location}")
    private String fileLocation;
    private static final String FILE_NAME = "filename";

    private static final String RELATIVE_FILE_PATH = System.getProperty("user.dir");
    @Autowired
    public ProductController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping("/backoffice/product")
    public String getList(Model model, HttpServletRequest request, @RequestParam(value = "sort", required = false) String sortCriteria) {
        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
        
        model.addAttribute(PRODUCTS_OBJECT_NAME, productService.findAll(sortCriteria));
        addModelAttributes(inputFlashMap, model);

        return PRODUCTS_OBJECT_NAME;
    }

    private void addModelAttributes(Map<String, ?> inputFlashMap, Model model){
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

    }

    @GetMapping("/frontoffice/shop")
    public String getProducts(Model model, HttpServletRequest request) throws NullPointerException {
        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute(PRODUCTS_OBJECT_NAME, productService.findAll());

        addModelAttributes(inputFlashMap, model);

        return SHOP_TEMPLATE;
    }

    @GetMapping("/backoffice/product/add")
    public String getProductEntryForm(Model model) {
        ProductDTO productEntry = new ProductDTO();

        model.addAttribute("entry", productEntry);
        model.addAttribute("categories", categoryService.findAll());
        return "add_product";
    }

    @GetMapping("/frontoffice/products/bycateg/{id}")
    public String getProductsByCategory(Model model, @PathVariable String id, HttpServletRequest request){
        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
        model.addAttribute(PRODUCTS_OBJECT_NAME, productService.findByCategory(Integer.parseInt(id)));

        addModelAttributes(inputFlashMap, model);
        return SHOP_TEMPLATE;
    }

    @PostMapping("/backoffice/product/add/submit")
    public String addProductEntry(@ModelAttribute @Valid ProductDTO form,
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
    public String delProductEntry(@PathVariable String id, RedirectAttributes redirectAttributes) {
        Product product = productService.findById((long) Integer.parseInt(id));
        if (productService.delete(product)) {
            redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, "You have successfully deleted product with name: " + product.getName());
        } else {
            redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, "The product you are trying to delete does not exist");
        }

        return Constants.REDIRECT_LINK + Constants.PRODUCTS_LIST_PAGE;
    }

    @GetMapping("/backoffice/product/edit/{id}")
    public String editForm(Model model, @PathVariable String id){
        Product productEntry = productService.findById((long) Integer.parseInt(id));
        model.addAttribute("entry", productEntry);
        return "edit_product";
    }

    @PostMapping("/backoffice/product/edit/submit")
    public String editProductEntry(@ModelAttribute ProductDTO form, RedirectAttributes redirectAttributes) {
        Category selectedCategory = categoryService.findByName(form.getCategoryName());
        if (productService.update(ProductMapper.mapFromProductDTOToProduct(form, selectedCategory)) != null) {
            redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, "You have successfully updated product with name: " + form.getName());
        } else {
            redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, "The product you are trying to update does not exist");
        }

        return Constants.REDIRECT_LINK + Constants.PRODUCTS_LIST_PAGE;
    }
}