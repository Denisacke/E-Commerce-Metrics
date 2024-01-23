package com.commerce.controller;

import com.commerce.constant.Constants;
import com.commerce.model.Cart;
import com.commerce.model.Order;
import com.commerce.model.Product;
import com.commerce.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

import static com.commerce.constant.Constants.FRONTOFFICE_HOME_PAGE;
import static com.commerce.constant.Constants.SUCCESS_MESSAGE;

@Controller
public class OrderController {

    private final OrderService orderService;
    private final CartService cartService;
    private final ProductService productService;

    @Autowired
    public OrderController(OrderService orderService,
                           CartService cartService,
                           ProductService productService) {
        this.orderService = orderService;
        this.cartService = cartService;
        this.productService = productService;
    }

    @PostMapping("/frontoffice/shop/order/submit")
    public String addEntry(@RequestParam String form, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
        if(request.isUserInRole(Constants.CUSTOMER_ROLE)){
            CustomerService customerService = new CustomerService();
            Long customerId = (customerService.findByUsername(request.getUserPrincipal().getName())).getId();
            
            List<Cart> productIds = cartService.findByCustomerId(customerId);
            List<Product> cartProducts = productIds.stream().map(x -> productService.findById((long) x.getProduct().getId())).collect(Collectors.toList());

            int sum = (cartProducts.stream().map(Product::getPrice).reduce(0.0, Double::sum)).intValue();
            String allProducts = productIds.stream().map(x -> String.valueOf(x.getProduct().getId())).collect(Collectors.joining(", "));

            Order order = new Order(customerId.intValue(), sum, form, allProducts, "placed");
            productIds.forEach(cartService::delete);
            if(orderService.save(order) != null){
                redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, "Order for user: " + customerId + " has been added");
            }else{
                redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, "Error when checking input");
            }
            return "redirect:/frontoffice/shop";
        }
        else{
            return Constants.REDIRECT_LINK+ FRONTOFFICE_HOME_PAGE;
        }
    }
}
