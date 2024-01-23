package com.commerce.controller;

import com.commerce.constant.Constants;
import com.commerce.model.Cart;
import com.commerce.model.Customer;
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
    private final ProductService productService;

    private static final String MY_CART_TEMPLATE = "my_cart";
    @Autowired
    public CartController(CartService cartService,
                          CustomerService customerService,
                          CategoryService categoryService,
                          ProductService productService) {
        this.cartService = cartService;
        this.customerService = customerService;
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @GetMapping("/frontoffice/shop/cart")
    public String getList(Model model, HttpServletRequest request) {
        if(request.isUserInRole(Constants.CUSTOMER_ROLE)){

            Long customerId = (customerService.findByUsername(request.getUserPrincipal().getName())).getId();
            List<Cart> cartEntries = cartService.findByCustomerId(customerId);

            List<Product> cartProducts = cartEntries.stream().map(x -> productService.findById(x.getProduct().getId())).collect(Collectors.toList());
            model.addAttribute("products", cartProducts);

            model.addAttribute("categories", categoryService.findAll());

            return MY_CART_TEMPLATE;
        }
        else{
            return Constants.REDIRECT_LINK+ FRONTOFFICE_HOME_PAGE;
        }
    }

    @PostMapping("/frontoffice/shop/cart/add/{id}")
    public String addEntry(@PathVariable String id, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
        if(request.isUserInRole(Constants.CUSTOMER_ROLE)){
            Customer customer = (customerService.findByUsername(request.getUserPrincipal().getName()));
            Product product = productService.findById(Long.parseLong(id));
            Cart form = new Cart(customer, product, 1);
            if(cartService.save(form) != null){
                redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, "Product added to cart");
            }else{
                redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, "Error when checking input");
            }
            return REDIRECT_LINK + CART_PAGE;
        }
        else{
            return Constants.REDIRECT_LINK + FRONTOFFICE_HOME_PAGE;
        }
    }

    @PostMapping("/frontoffice/shop/cart/delete/{id}")
    public String delEntry(Model model, @PathVariable String id, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        if(request.isUserInRole(Constants.CUSTOMER_ROLE)) {
            Long customerId = (customerService.findByUsername(request.getUserPrincipal().getName())).getId();
            Cart entity = cartService.findByCustomerId(customerId).stream().filter(x -> x.getProduct().getId() == Integer.parseInt(id)).collect(Collectors.toList()).get(0);

            if (cartService.delete(entity)) {
                redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, "You have successfully deleted product: " + productService.findById((long) entity.getProduct().getId()));
            } else {
                redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, "An issue occurred when trying to delete a cart entry");
            }
            return REDIRECT_LINK + CART_PAGE;
        }else{
            redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, ERROR_ACCESS_MESSAGE);
            return Constants.REDIRECT_LINK + FRONTOFFICE_HOME_PAGE;
        }
    }
}
