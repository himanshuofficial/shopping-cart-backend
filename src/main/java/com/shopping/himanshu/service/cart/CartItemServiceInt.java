package com.shopping.himanshu.service.cart;

import com.shopping.himanshu.exceptions.ResourceNotFound;
import com.shopping.himanshu.model.Cart;

import java.math.BigDecimal;

public interface CartItemServiceInt {
    void addItemToCart(Long cartId, Long ProductId, int quantity) throws ResourceNotFound;
    void removeItemFromCart(Long cartId, Long productId) throws ResourceNotFound;
    void updateItemQuantity(Long cartId, Long productId, int quantity) throws ResourceNotFound;

}
