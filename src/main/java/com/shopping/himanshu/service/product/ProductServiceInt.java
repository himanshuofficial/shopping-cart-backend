package com.shopping.himanshu.service.product;

import com.shopping.himanshu.exceptions.ProductNotFound;
import com.shopping.himanshu.model.Product;
import com.shopping.himanshu.request.AddProductRequest;
import com.shopping.himanshu.request.ProductUpdateRequest;

import java.util.List;

public interface ProductServiceInt {
    Product addProduct(AddProductRequest product);
    Product getProductById(Long id) throws ProductNotFound;
    void deleteProductById(Long id);
    List<Product> listAllProducts();
    List<Product> getProductByCategory(String category);
    List<Product> getProductByBrand(String category, String brand);
    List<Product> getProductByName(String name);

    Product updateProduct(ProductUpdateRequest product, Long productId) throws ProductNotFound;
}
