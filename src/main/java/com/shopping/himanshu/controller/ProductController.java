package com.shopping.himanshu.controller;


import com.shopping.himanshu.dto.ProductDto;
import com.shopping.himanshu.exceptions.ResourceNotFound;
import com.shopping.himanshu.model.Product;
import com.shopping.himanshu.request.AddProductRequest;
import com.shopping.himanshu.request.ProductUpdateRequest;
import com.shopping.himanshu.response.ApiResponse;
import com.shopping.himanshu.service.product.ProductServiceInt;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/products")
public class ProductController {

    private final ProductServiceInt productService;

    @GetMapping("")
    public ResponseEntity<ApiResponse> getAllProducts() {
        List<ProductDto> products = productService.getConvertedProducts(productService.listAllProducts());
        return ResponseEntity.ok(new ApiResponse("success", products));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable Long productId) {
        try {
            ProductDto product = productService.convertToDto(productService.getProductById(productId));
            return ResponseEntity.ok(new ApiResponse("Product found!", product));
        } catch (ResourceNotFound e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse> addProduct(@RequestBody AddProductRequest request) {
        try {
            Product product = productService.addProduct(request);
            return ResponseEntity.ok(new ApiResponse("Prooduct created", product));
        } catch(Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ApiResponse> updateProduct(@RequestBody ProductUpdateRequest request, @PathVariable Long productId) {
        try {
            Product product = productService.updateProduct(request, productId);
            return ResponseEntity.ok(new ApiResponse("Prooduct updated", product));
        } catch(ResourceNotFound e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }


    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long productId) {
//        TODO: check for exception by giving id that does not exists
        try {
            productService.deleteProductById(productId);
            return ResponseEntity.ok(new ApiResponse("Prooduct Deleted", null));
        } catch(Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
}
