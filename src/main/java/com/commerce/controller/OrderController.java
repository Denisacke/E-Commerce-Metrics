package com.commerce.controller;

import com.commerce.constant.Constants;
import com.commerce.model.Cart;
import com.commerce.model.Order;
import com.commerce.model.Product;
import com.commerce.service.*;
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

    OrderService orderService = new OrderService();

    @PostMapping("/frontoffice/shop/order/submit")
    public String addEntry(@RequestParam String form, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
        if(request.isUserInRole(Constants.CUSTOMER_ROLE)){
            CustomerService customerService = new CustomerService();
            Long customer_id = (customerService.findByUsername(request.getUserPrincipal().getName())).getId();

            CartService cartService = new CartService();
            List<Cart> product_ids = cartService.findByCustomerId(customer_id.intValue());
            ProductService productService = new ProductService();
            List<Product> cart_products = product_ids.stream().map((x) -> productService.findById((long) x.getId_product())).collect(Collectors.toList());

            int sum = (cart_products.stream().map((x) -> x.getPrice()).reduce(0.0, Double::sum)).intValue();
            String allProducts = product_ids.stream().map((x) -> String.valueOf(x.getId_product())).collect(Collectors.joining(", "));

            Order order = new Order(customer_id.intValue(), sum, form, allProducts, "placed");
            product_ids.stream().map((x) -> cartService.delete(x));
            if(orderService.save(order) != null){
                redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, "Order for user: " + customer_id + " has been added");
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
