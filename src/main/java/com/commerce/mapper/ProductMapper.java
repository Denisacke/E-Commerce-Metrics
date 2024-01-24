package com.commerce.mapper;

import com.commerce.model.Category;
import com.commerce.model.Product;
import com.commerce.dto.ProductDTO;

public class ProductMapper {

    private ProductMapper(){
    }

    public static Product mapFromProductDTOToProduct(ProductDTO productDTO, Category category){
        return new Product(productDTO.getName(), productDTO.getPrice(), productDTO.getDescription(), productDTO.getPicture(), category);
    }

    public static ProductDTO mapFromProductToProductDTO(Product product){
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId().intValue());
        productDTO.setName(product.getName());
        productDTO.setPicture(product.getPicture());
        productDTO.setCategoryName(product.getCategory() != null ? product.getCategory().getName() : "");
        productDTO.setPrice(product.getPrice());
        productDTO.setDescription(product.getDescription());
        productDTO.setStock(product.getStock());

        return productDTO;
    }
}
