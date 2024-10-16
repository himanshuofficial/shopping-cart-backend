package com.shopping.himanshu.controller;


import com.shopping.himanshu.exceptions.ResourceNotFound;
import com.shopping.himanshu.repository.CartRepository;
import com.shopping.himanshu.response.ApiResponse;
import com.shopping.himanshu.service.cart.CartInt;
import com.shopping.himanshu.service.cart.CartItemService;
import com.shopping.himanshu.service.cart.CartItemServiceInt;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequestMapping("${api.prefix}/cartItems")
@RestController
@RequiredArgsConstructor
public class CartItemController {

        private final CartItemServiceInt cartItemService;
        private final CartInt cartService;

        @PostMapping("")
        public ResponseEntity<ApiResponse> addItemToCart(@RequestParam(required = false) Long cartId,
                                                         @RequestParam Long productId,
                                                         @RequestParam Integer quantity) {
                try {
                        if(cartId == null) {
                                cartId = cartService.initializeNewCart();
                        }
                        cartItemService.addItemToCart(cartId, productId, quantity);
                        return ResponseEntity.ok(new ApiResponse("Add item success", null));
                } catch (ResourceNotFound e) {
                        return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
                }
        }

        @DeleteMapping("/{cartId}/{itemId}")
        public ResponseEntity<ApiResponse> deleteItemToCart(@PathVariable Long cartId, @PathVariable Long itemId) {
                try {
                        cartItemService.removeItemFromCart(cartId, itemId);
                        return ResponseEntity.ok(new ApiResponse("Remove item success", null));
                } catch (ResourceNotFound e) {
                        return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
                }
        }

        @PutMapping("/{cartId}/{itemId}")
        public ResponseEntity<ApiResponse> updateCartQuantity(@PathVariable Long cartId,
                                                              @PathVariable Long itemId,
                                                              @RequestParam Integer quantity) {
                try {
                        cartItemService.updateItemQuantity(cartId, itemId, quantity);
                        return ResponseEntity.ok(new ApiResponse("Update item success", null));
                } catch (ResourceNotFound e) {
                        return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
                }
        }
}
