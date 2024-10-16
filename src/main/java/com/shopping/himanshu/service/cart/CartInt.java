package com.shopping.himanshu.service.cart;

import com.shopping.himanshu.exceptions.ResourceNotFound;
import com.shopping.himanshu.model.Cart;
import com.shopping.himanshu.model.CartItem;

import java.math.BigDecimal;

public interface CartInt {
    Cart getCart(Long id) throws ResourceNotFound;
    void clearCart(Long id) throws ResourceNotFound;
    BigDecimal getTotalPrice(Long id) throws ResourceNotFound;


    Long initializeNewCart();
}
