package com.example.ordermanagmentservice.mapper;


import com.example.ordermanagmentservice.dto.ProductDto;
import com.example.ordermanagmentservice.model.Product;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

/*
    @Mapping(source = "id", target = "id")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "name", target = "name")
    ProductDto productToProductDto(Product product);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "name", target = "name")
    Product productDtoToProduct(ProductDto productDto);

    List<Product> productDtosToProducts(List<ProductDto> productDtoList);

    List<ProductDto> productToProductDtos(List<Product> productList);
*/

}
