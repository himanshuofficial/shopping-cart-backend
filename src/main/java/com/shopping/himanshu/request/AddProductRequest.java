package com.shopping.himanshu.request;

import com.shopping.himanshu.model.Category;
import com.shopping.himanshu.model.Image;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
public class AddProductRequest {
    private Long id;

    private String name;

    private String brand;

    private BigDecimal price;

    private int inventory;

    private String  description;

    private Category category;
}
