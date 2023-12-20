package com.commerce.controller;

import com.commerce.constant.Constants;
import com.commerce.model.Cart;
import com.commerce.model.Product;
import com.commerce.service.CartService;
import com.commerce.service.CategoryService;
import com.commerce.service.CustomerService;
import com.commerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

import static com.commerce.constant.Constants.*;

@Controller
public class CartController {

    private final CartService cartService;
    private final CustomerService customerService;
    private final CategoryService categoryService;

    @Autowired
    public CartController(CartService cartService, CustomerService customerService, CategoryService categoryService) {
        this.cartService = cartService;
        this.customerService = customerService;
        this.categoryService = categoryService;
    }

    @GetMapping("/frontoffice/shop/cart")
    public String getList(Model model, HttpServletRequest request) {
        if(request.isUserInRole(Constants.CUSTOMER_ROLE)){

            Long customerId = (customerService.findByUsername(request.getUserPrincipal().getName())).getId();
            List<Cart> productIds = cartService.findByCustomerId(customerId.intValue());

            ProductService productService = new ProductService();
            List<Product> cartProducts = productIds.stream().map(x -> productService.findById((long) x.getProductId())).collect(Collectors.toList());
            model.addAttribute("products", cartProducts);

            model.addAttribute("categories", categoryService.findAll());

            return "my_cart";
        }
        else{
            return Constants.REDIRECT_LINK+ FRONTOFFICE_HOME_PAGE;
        }
    }

    @PostMapping("/frontoffice/shop/cart/add/{id}")
    public String addEntry(@PathVariable String id, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
        if(request.isUserInRole(Constants.CUSTOMER_ROLE)){
            Long customerId = (customerService.findByUsername(request.getUserPrincipal().getName())).getId();
            Cart form = new Cart(customerId.intValue(),Integer.parseInt((id)), 1);
            if(cartService.save(form) != null){
                ProductService productService = new ProductService();
                redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, "Account with user: " + productService.findById((long) Integer.parseInt(id)) + " has been added");
            }else{
                redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, "Error when checking input");
            }
            return "redirect:/frontoffice/shop/cart";
        }
        else{
            return Constants.REDIRECT_LINK+ FRONTOFFICE_HOME_PAGE;
        }
    }

    @PostMapping("/frontoffice/shop/cart/delete/{id}")
    public String delEntry(Model model, @PathVariable String id, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        if(request.isUserInRole(Constants.CUSTOMER_ROLE)) {
            ProductService productService = new ProductService();

            Long customerId = (customerService.findByUsername(request.getUserPrincipal().getName())).getId();
            Cart entity = cartService.findByCustomerId(customerId.intValue()).stream().filter(x -> x.getProductId() == Integer.parseInt(id)).collect(Collectors.toList()).get(0);

            if (cartService.delete(entity)) {
                redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, "Ai sters cu succes produsul: " + productService.findById((long) entity.getProductId()));
            } else {
                redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, "Atentie! Produsul pe care incerci sa il stergi nu exista");
            }
            return "redirect:/frontoffice/shop/cart";
        }else{
            redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, ERROR_ACCESS_MESSAGE);
            return Constants.REDIRECT_LINK+ FRONTOFFICE_HOME_PAGE;
        }
    }
}
