package com.commerce.constant;

/**
 * Class containing most of the constants used throughout the project
 */
public class Constants {

    private Constants(){

    }

    public static final String BACKOFFICE_HOME_PAGE = "/backoffice/home";
    public static final String PRODUCTS_LIST_PAGE = "/backoffice/product";
    public static final String EMPLOYEES_LIST_PAGE = "/backoffice/employee";
    public static final String CUSTOMERS_LIST_PAGE = "/backoffice/customer";
    public static final String FRONTOFFICE_HOME_PAGE = "/frontoffice";
    public static final String SHOP_PAGE = "/frontoffice/shop";
    public static final String CART_PAGE = "/frontoffice/shop/cart";


    public static final String REDIRECT_LINK = "redirect:";
    public static final String PRODUCTS = "/products";
    public static final String CUSTOMER_ROLE = "ROLE_CUSTOMER";
    public static final String EMPLOYEE_ROLE = "ROLE_USER";
    public static final String ADMIN_ROLE = "ROLE_ADMIN";

    public static final String SUCCESS_MESSAGE = "success";
    public static final String ERROR_ACCESS_MESSAGE = "Error. You don't have the permission to access this!";
}
