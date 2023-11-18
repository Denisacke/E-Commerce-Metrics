package com.commerce.model.mapper;

import com.commerce.model.Category;
import com.commerce.model.Product;
import com.commerce.model.dto.ProductDTO;

public class ProductMapper {

    private ProductMapper(){
    }

    public static Product mapFromProductDTOToProduct(ProductDTO productDTO, Category category){
        return new Product(productDTO.getName(), productDTO.getPrice(), productDTO.getDescription(), productDTO.getPicture(), category);
    }
}
