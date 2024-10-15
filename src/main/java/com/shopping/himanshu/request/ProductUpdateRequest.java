package com.shopping.himanshu.request;

import com.shopping.himanshu.model.Category;
import lombok.*;
import java.math.BigDecimal;


@Data
public class ProductUpdateRequest {
    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private int inventory;
    private String  description;
    private Category category;
}