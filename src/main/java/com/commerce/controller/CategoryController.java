package com.commerce.controller;

import com.commerce.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class CategoryController {

   /* private CategoryService categoryService = new CategoryService();

    @GetMapping("/frontoffice/shop")
    public String getCategories(Model model, HttpServletRequest request){
        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
        model.addAttribute("categories", categoryService.findAll());
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
    }*/
}
