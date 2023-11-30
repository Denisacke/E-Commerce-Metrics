package com.commerce.controller;

import com.commerce.constant.Constants;
import com.commerce.model.Product;
import com.commerce.model.Wishlist;
import com.commerce.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

import static com.commerce.constant.Constants.*;

@Controller
public class WishlistController {

    private final WishlistService wishlistService = new WishlistService();
    private final ProductService productService = new ProductService();
    private final CustomerService customerService = new CustomerService();

    @GetMapping("/frontoffice/shop/wishlist")
    public String getList(Model model, HttpServletRequest request) {
        if(request.isUserInRole(Constants.CUSTOMER_ROLE)){

            Long customerId = (customerService.findByUsername(request.getUserPrincipal().getName())).getId();
            List<Wishlist> productIds = wishlistService.findByCustomerId(customerId.intValue());

            List<Product> wishlistProducts = productIds.stream().map((x) -> productService.findById((long) x.getId_product())).collect(Collectors.toList());
            model.addAttribute("products", wishlistProducts);

            CategoryService categoryService = new CategoryService();
            model.addAttribute("categories", categoryService.findAll());

            return "wishlist";
        }
        else{
            return Constants.REDIRECT_LINK+ FRONTOFFICE_HOME_PAGE;
        }
    }

    @PostMapping("/frontoffice/shop/wishlist/add/{id}")
    public String addEntry(@PathVariable String id, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
        if(request.isUserInRole(Constants.CUSTOMER_ROLE)){
            Long customerId = (customerService.findByUsername(request.getUserPrincipal().getName())).getId();
            Wishlist form = new Wishlist(customerId.intValue(), Integer.parseInt(id));
            if(wishlistService.save(form) != null){
                redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, "Account with user: " + productService.findById((long) Integer.parseInt(id)) + " has been added");
            }else{
                redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, "Error when checking input");
            }
            return "redirect:/frontoffice/shop/wishlist";
        }
        else{
            return Constants.REDIRECT_LINK+ FRONTOFFICE_HOME_PAGE;
        }
    }

    @PostMapping("/frontoffice/shop/wishlist/delete/{id}")
    public String delEntry(Model model, @PathVariable String id, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        if(request.isUserInRole(Constants.CUSTOMER_ROLE)) {
            Long customerId = (customerService.findByUsername(request.getUserPrincipal().getName())).getId();
            Wishlist entity = wishlistService.findByCustomerId(customerId.intValue()).stream().filter((x) -> x.getId_product() == Integer.parseInt(id)).collect(Collectors.toList()).get(0);

            if (wishlistService.delete(entity)) {
                redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, "Ai sters cu succes produsul: " +
                        productService.findById((long) entity.getId_product()));
            } else {
                redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, "Atentie! Produsul pe care incerci sa il stergi nu exista");
            }
            return "redirect:/frontoffice/shop/wishlist";
        }else{
            redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, ERROR_ACCESS_MESSAGE);
            return Constants.REDIRECT_LINK+ FRONTOFFICE_HOME_PAGE;
        }
    }
}

